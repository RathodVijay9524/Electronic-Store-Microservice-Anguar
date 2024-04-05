package com.example.orderservice.client;

import com.vijay.commonservice.order.model.OrderItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ORDER-ITEM-SERVICE")
public interface OrderItemFeignClientService {

    @PostMapping("/api/order-items")
    OrderItemDto addOrderItem(@RequestBody OrderItemDto req);

    @PutMapping("/api/order-items/{orderItemId}")
    OrderItemDto updateOrderItem(@PathVariable String orderItemId, @RequestBody OrderItemDto req);


    @GetMapping("/api/order-items/order/{orderId}")
    List<OrderItemDto> getOrderItemsByOrderId(@PathVariable String orderId);

    @DeleteMapping("/api/order-items/order/{orderId}")
    Void deleteOrderItemsByOrderId(@PathVariable String orderId);

    @GetMapping("/api/order-items/{orderItemId}")
    OrderItemDto getOrderItemByOrderItemId(@PathVariable String orderItemId);

    @DeleteMapping("/api/order-items/{orderItemId}")
    Void deleteOrderItemByOrderItemId(@PathVariable String orderItemId);

}
