package com.vijay.orderservice.controller;

import com.vijay.commonservice.order.model.OrderRequest;
import com.vijay.commonservice.order.model.OrderResponse;
import com.vijay.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param orderRequest The request containing details of the order to be created.
     * @return A ResponseEntity containing details of the created order.
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        logger.info("Creating order: {}", orderRequest);
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        logger.info("Order created: {}", orderResponse);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return A ResponseEntity containing details of the retrieved order.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
        logger.info("Fetching order with ID: {}", orderId);
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        logger.info("Fetched order: {}", orderResponse);
        return ResponseEntity.ok(orderResponse);
    }

    /**
     * Retrieves all orders.
     *
     * @return A ResponseEntity containing a list of all orders.
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        logger.info("Fetching all orders");
        List<OrderResponse> orders = orderService.getAllOrders();
        logger.info("Fetched {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    // Other endpoints for getting orders by user ID, product ID, etc.

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return A ResponseEntity containing a message indicating the deletion status.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        logger.info("Deleting order with ID: {}", orderId);
        String message = orderService.deleteOrder(orderId);
        logger.info("Deleted order: {}", orderId);
        return ResponseEntity.ok(message);
    }

    /**
     * Updates an order.
     *
     * @param orderId      The ID of the order to update.
     * @param updatedOrder The request containing updated details of the order.
     * @return A ResponseEntity containing details of the updated order.
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable String orderId,
                                                     @RequestBody OrderRequest updatedOrder) {
        logger.info("Updating order with ID {}: {}", orderId, updatedOrder);
        OrderResponse orderResponse = orderService.updateOrder(orderId, updatedOrder);
        logger.info("Updated order: {}", orderResponse);
        return ResponseEntity.ok(orderResponse);
    }

    /**
     * Retrieves a list of orders associated with the given user ID.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A ResponseEntity containing a list of OrderResponse objects.
     */
    @GetMapping("/user/{userId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrderByUserId(@PathVariable String userId) {
        logger.info("Fetching orders by user ID: {}", userId);
        List<OrderResponse> orders = orderService.getOrderByUserId(userId);
        logger.info("Fetched {} orders for user ID: {}", orders.size(), userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Retrieves a list of orders associated with the given product ID.
     *
     * @param productId The ID of the product whose orders are to be retrieved.
     * @return A ResponseEntity containing a list of OrderResponse objects.
     */
    @GetMapping("/product/{productId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrderByProductId(@PathVariable String productId) {
        logger.info("Fetching orders by product ID: {}", productId);
        List<OrderResponse> orders = orderService.getOrderByProductId(productId);
        logger.info("Fetched {} orders for product ID: {}", orders.size(), productId);
        return ResponseEntity.ok(orders);
    }
}
