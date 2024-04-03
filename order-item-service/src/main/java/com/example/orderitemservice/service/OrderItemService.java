package com.example.orderitemservice.service;

import com.vijay.commonservice.order.model.OrderItemDto;

import java.util.List;

public interface OrderItemService {

    OrderItemDto addOrderItem(OrderItemDto req);
    OrderItemDto getOrderItemByOrderItemId(String orderItemId);
    List<OrderItemDto> getOrderItemsByOrderId(String orderId);
    void deleteOrderItemByOrderItemId(String orderItemId);
    void deleteOrderItemsByOrderId(String orderId);
    OrderItemDto updateOrderItem(String orderItemId,OrderItemDto req);
}
