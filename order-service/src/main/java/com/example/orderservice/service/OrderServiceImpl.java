package com.example.orderservice.service;

import com.example.orderservice.client.*;
import com.example.orderservice.enitity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.vijay.commonservice.exception.BadApiRequestException;
import com.vijay.commonservice.order.model.*;
import com.vijay.commonservice.response.PageableResponse;
import com.vijay.commonservice.user.response.UserDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
                    .user(user)
                    .build();
            OrderItemDto orderItemDto1=new OrderItemDto();
            BeanUtils.copyProperties(order,orderItemDto1);

            // Calculating order amount and creating order items
            AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
            List<OrderItemDto> orderItems = cartItems.stream()
                    .map(cartItem -> {
                        String cartItemId = cartItem.getCartItemId();

                        logger.info("Processing cart item: {}", cartItemId);

                        // Creating OrderItemDto
                        OrderItemDto orderItemDto = OrderItemDto.builder()
                                .quantity(cartItem.getQuantity())
                                .productId(cartItem.getProduct().getProductId())
                                .product(cartItem.getProduct())
                                .userId(cartItem.getUserId())
                                .order(orderItemDto1.getOrder())
                                .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                                .build();

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
            Order savedOrder = orderRepository.save(order);
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(savedOrder, orderDto);
            logger.info("Order created successfully for userId: {} and orderId: {}", userId, orderDto.getOrderId());
            return orderDto;
        } catch (Exception ex) {
            logger.error("Error creating order for userId: {}", req.getUserId(), ex);
            throw ex; // rethrow the exception after logging
        }
    }

    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        return List.of();
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public OrderDto updateOrder(String orderId, OrderUpdateRequest request) {
        return null;
    }
}
