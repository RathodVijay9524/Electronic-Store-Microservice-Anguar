package com.example.orderservice.service;

import com.example.orderservice.client.*;
import com.example.orderservice.enitity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.vijay.commonservice.exception.BadApiRequestException;
import com.vijay.commonservice.order.model.*;
import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;
import com.vijay.commonservice.response.PageableResponse;
import com.vijay.commonservice.user.response.UserDto;
import com.vijay.commonservice.util.IdUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserFeignClient userFeignClient;
    private final ProductFeignClient productFeignClient;
    private final CartItemFeignClient cartItemFeignClient;
    private final CartFeignClient cartFeignClient;
    private final OrderItemFeignClientService orderItemFeignClientService;
    private final OrderRepository orderRepository;
    private final PaymentFeignClientService paymentFeignClientService;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderDto createOrder(CreateOrderRequest req) {
        try {
            logger.info("Creating order for userId: {} and cartId: {}", req.getUserId(), req.getCartId());
            // Extracting user and cart details
            String userId = req.getUserId();
            String cartId = req.getCartId();
            UserDto user = userFeignClient.getUser(userId);
            CartDto cart = cartFeignClient.getCartById(cartId);

            // Retrieving cart items
            List<CartItemDto> cartItems = cart.getItems();

            // Checking if cart is empty
            if (cartItems.isEmpty()) {
                logger.warn("Cart is empty for userId: {}", userId);
                throw new BadApiRequestException("The cart is empty!");
            }

            // Creating new order
            Order order = Order.builder()
                    .billingName(req.getBillingName())
                    .billingPhone(req.getBillingPhone())
                    .billingAddress(req.getBillingAddress())
                    .orderedDate(new Date())
                    .deliveredDate(null)
                    .paymentStatus(req.getPaymentStatus())
                    .orderStatus(req.getOrderStatus())
                    .userId(req.getUserId())
                    .orderId(IdUtils.generateId())
                    .paymentMode(req.getPaymentMode())
                    .user(user)
                    .build();



            // Calculating order amount and creating order items
            AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
            List<OrderItemDto> orderItems = cartItems.stream()
                    .map(cartItem -> {
                        String cartItemId = cartItem.getCartItemId();
                        System.out.println(order.getOrderId());
                        logger.info("Processing cart item: {}", cartItemId);

                        // Creating OrderItemDto
                        OrderItemDto orderItemDto = OrderItemDto.builder()
                                .quantity(cartItem.getQuantity())
                                .productId(cartItem.getProduct().getProductId())
                                .product(cartItem.getProduct())
                                .orderId(order.getOrderId())
                                .userId(cartItem.getUserId())
                                .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                                .build();
                        productFeignClient.reduceProductQuantity(cartItem.getQuantity(), cartItem.getProduct().getProductId());

                        // Updating cart item via Feign Client
                        cartItemFeignClient.updateCartItem(cartItemId, cartItem);
                        orderItemFeignClientService.addOrderItem(orderItemDto);


                        // Updating order amount
                        orderAmount.updateAndGet(amount -> amount + orderItemDto.getTotalPrice());
                        System.out.println(orderAmount.get());
                        return orderItemDto;
                    })
                    .collect(Collectors.toList());

            // Setting order items and order amount
            order.setOrderItems(orderItems);
            order.setOrderAmount(orderAmount.get());
            logger.info("Number of order items created: {}", order.getOrderItems().size());

            // Clearing cart items
            cartFeignClient.clearCart(cartId);
            // Saving order and converting to OrderDto

             orderRepository.save(order);
            logger.info("Calling Payment Service to complete the payment");
            PaymentRequest paymentRequest= PaymentRequest.builder()
                    .orderId(order.getOrderId())
                    .userId(user.getUserId())
                    .amount(order.getOrderAmount())
                    .paymentMode(order.getPaymentMode())
                    .build();

            String orderStatus = null;

            try {
                PaymentResponse response= paymentFeignClientService.doPayment(paymentRequest);
                order.setPaymentStatus(response.getPaymentStatus());
                logger.info("Payment done Successfully. Changing the Order status to PLACED");
                orderStatus = "PLACED";
                System.out.println("Payment successful");
            } catch (Exception e) {
                logger.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
                orderStatus = "PAYMENT_FAILED";
                cartItems.stream().map(cartItem ->{
                    OrderItemDto orderItemDto = OrderItemDto.builder().build();
                    productFeignClient.increaseProductQuantity(cartItem.getQuantity(),cartItem.getProduct().getProductId());
                    return orderItemDto;
                }).toList();
                PaymentResponse paymentDetails= paymentFeignClientService.getPaymentDetailsByOrderId(order.getOrderId());
                PaymentRequest paymentResponse=new PaymentRequest();
                paymentResponse.setPaymentStatus(orderStatus);
                paymentFeignClientService.updatePaymentStatus(paymentDetails.getPaymentId(),paymentResponse);
               System.out.println("Payment failed: " + e.getMessage());
            }

            PaymentResponse paymentDetails= paymentFeignClientService.getPaymentDetailsByOrderId(order.getOrderId());
            order.setPayments(paymentDetails);
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            OrderDto orderDto = new OrderDto();

            BeanUtils.copyProperties(order, orderDto);
            logger.info("Order created successfully for userId: {} and orderId: {}", userId, orderDto.getOrderId());
            return orderDto;
        } catch (Exception ex) {
            logger.error("Error creating order for userId: {}", req.getUserId(), ex);
            throw ex; // rethrow the exception after logging
        }
    }


    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        List<Order> orders= orderRepository.findByUserId(userId);
        List<OrderDto> OrderResponse= orders.stream().map(order -> {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            return orderDto;
        }).collect(Collectors.toList());
        return OrderResponse;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Order order= orderRepository.findById(orderId).get();
        PaymentResponse paymentDetails= paymentFeignClientService.getPaymentDetailsByOrderId(orderId);
        OrderDto orderDto=new OrderDto();
        orderDto.setPayments(paymentDetails);
        BeanUtils.copyProperties(order, orderDto);
        return orderDto;
    }

    @Override
    public String deleteOrderWithOrderItemsByOrderId(String orderId) {
        orderItemFeignClientService.deleteOrderItemsByOrderId(orderId);
        orderRepository.deleteById(orderId);
        return "Order Deleted Successfully With Order Items";
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public OrderDto updateOrder(String orderId, OrderUpdateRequest request) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders= orderRepository.findAll();
        List<OrderDto> ordersList= orders.stream().map(order -> {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            return orderDto;
        }).collect(Collectors.toList());
        return ordersList;
    }
}
