package com.vijay.orderservice.service;

import com.vijay.commonservice.order.model.OrderRequest;
import com.vijay.commonservice.order.model.OrderResponse;

import java.util.List;

public interface OrderService {
    /**
     * Creates a new order.
     *
     * @param order The request containing details of the order to be created.
     * @return The response containing details of the created order.
     */
    OrderResponse createOrder(OrderRequest order);

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The response containing details of the retrieved order.
     */
    OrderResponse getOrderById(String orderId);

    /**
     * Retrieves all orders.
     *
     * @return A list of responses containing details of all orders.
     */
    List<OrderResponse> getAllOrders();

    /**
     * Retrieves orders by user ID.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A list of responses containing details of orders placed by the user.
     */
    List<OrderResponse> getOrderByUserId(String userId);

    /**
     * Retrieves orders by product ID.
     *
     * @param productId The ID of the product associated with the orders to retrieve.
     * @return A list of responses containing details of orders containing the product.
     */
    List<OrderResponse> getOrderByProductId(String productId);

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return A message indicating the success or failure of the deletion operation.
     */
    String deleteOrder(String orderId);

    /**
     * Updates an existing order.
     *
     * @param orderId      The ID of the order to update.
     * @param updatedOrder The updated details of the order.
     * @return The response containing details of the updated order.
     */
    OrderResponse updateOrder(String orderId, OrderRequest updatedOrder);
}


