package com.example.orderitemservice.controller;

import com.example.orderitemservice.service.OrderItemService;
import com.vijay.commonservice.order.model.OrderItemDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@AllArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping()
    public ResponseEntity<OrderItemDto> addOrderItem(@RequestBody OrderItemDto req) {
        OrderItemDto orderItemDto = orderItemService.addOrderItem(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemDto);
    }
    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItemDto> getOrderItemByOrderItemId(@PathVariable String orderItemId) {
        OrderItemDto orderItemDto = orderItemService.getOrderItemByOrderItemId(orderItemId);
        return ResponseEntity.ok(orderItemDto);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDto>> getOrderItemsByOrderId(@PathVariable String orderId) {
        List<OrderItemDto> orderItemDtoList = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(orderItemDtoList);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItemByOrderItemId(@PathVariable String orderItemId) {
        orderItemService.deleteOrderItemByOrderItemId(orderItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrderItemsByOrderId(@PathVariable String orderId) {
        orderItemService.deleteOrderItemsByOrderId(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<OrderItemDto> updateOrderItem(@PathVariable String orderItemId, @RequestBody OrderItemDto req) {
        OrderItemDto updatedOrderItem = orderItemService.updateOrderItem(orderItemId, req);
        return ResponseEntity.ok(updatedOrderItem);
    }
}
