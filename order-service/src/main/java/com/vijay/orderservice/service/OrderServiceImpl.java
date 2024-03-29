package com.vijay.orderservice.service;

import com.vijay.commonservice.order.model.OrderRequest;
import com.vijay.commonservice.order.model.OrderResponse;
import com.vijay.commonservice.user.exception.ResourceNotFoundException;
import com.vijay.orderservice.entity.Order;
import com.vijay.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new order.
     *
     * @param order The request containing details of the order to be created.
     * @return The response containing details of the created order.
     */
    @Override
    public OrderResponse createOrder(OrderRequest order) {
        // Map the OrderRequest to an Order entity
        Order entity = modelMapper.map(order, Order.class);
        // Generate a unique order ID
        entity.setOrderId(UUID.randomUUID().toString());
        // Save the order to the repository
        Order savedOrder = orderRepository.save(entity);
        // Map the saved Order entity back to OrderResponse and return it
        return modelMapper.map(savedOrder, OrderResponse.class);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The response containing details of the retrieved order.
     */
    @Override
    public OrderResponse getOrderById(String orderId) {
        // Retrieve the order from the repository by ID
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        // Map the retrieved Order entity to OrderResponse if present, otherwise return null
        return optionalOrder.map(order -> modelMapper.map(order, OrderResponse.class)).orElse(null);
    }

    /**
     * Retrieves all orders.
     *
     * @return A list of responses containing details of all orders.
     */
    @Override
    public List<OrderResponse> getAllOrders() {
        // Retrieve all orders from the repository
        List<Order> orders = orderRepository.findAll();
        // Map each Order entity to OrderResponse and collect them into a list
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves orders by user ID.
     *
     * @param userId The ID of the user.
     * @return A list of responses containing details of orders belonging to the user.
     */
    @Override
    public List<OrderResponse> getOrderByUserId(String userId) {
        // Retrieve orders by user ID from the repository
        List<Order> orders = orderRepository.findByUserId(userId);
        // Map each Order entity to OrderResponse and collect them into a list
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves orders by product ID.
     *
     * @param productId The ID of the product.
     * @return A list of responses containing details of orders for the product.
     */
    @Override
    public List<OrderResponse> getOrderByProductId(String productId) {
        // Retrieve orders by product ID from the repository
        List<Order> orders = orderRepository.findByProductId(productId);
        // Map each Order entity to OrderResponse and collect them into a list
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return A message indicating the result of the deletion.
     * @throws ResourceNotFoundException If the order with the given ID does not exist.
     */
    @Override
    public String deleteOrder(String orderId) {
        // Check if the order exists in the repository
        if (orderRepository.existsById(orderId)) {
            // Delete the order by ID
            orderRepository.deleteById(orderId);
            // Return a success message
            return "Order deleted successfully.";
        } else {
            // Throw an exception if the order is not found
            throw new ResourceNotFoundException("Order", "orderId", orderId);
        }
    }

    /**
     * Updates an existing order with the provided details.
     *
     * @param orderId      The ID of the order to update.
     * @param updatedOrder The request containing updated details of the order.
     * @return The response containing details of the updated order.
     * @throws ResourceNotFoundException If the order with the given ID does not exist.
     */
    @Override
    public OrderResponse updateOrder(String orderId, OrderRequest updatedOrder) {
        // Retrieve the existing order from the repository
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));

        // Map the updated details from OrderRequest to the existing Order entity
        modelMapper.map(updatedOrder, existingOrder);

        // Save the updated order to the repository
        Order savedOrder = orderRepository.save(existingOrder);

        // Map the saved Order entity back to OrderResponse and return it
        return modelMapper.map(savedOrder, OrderResponse.class);
    }
}
