package com.example.orderservice.service;

import com.vijay.commonservice.order.model.CreateOrderRequest;
import com.vijay.commonservice.order.model.OrderDto;
import com.vijay.commonservice.order.model.OrderUpdateRequest;
import com.vijay.commonservice.response.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest req);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    OrderDto getOrderByOrderId(String orderId);

    String deleteOrderWithOrderItemsByOrderId(String orderId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    OrderDto updateOrder(String orderId, OrderUpdateRequest request);

    List<OrderDto> getAllOrders();
    //order methods(logic) related to order
}
