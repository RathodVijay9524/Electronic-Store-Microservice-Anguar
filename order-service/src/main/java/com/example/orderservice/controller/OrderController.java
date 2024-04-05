package com.example.orderservice.controller;

import com.example.orderservice.service.OrderService;
import com.vijay.commonservice.order.model.CreateOrderRequest;
import com.vijay.commonservice.order.model.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderDto createdOrder = orderService.createOrder(createOrderRequest);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable String orderId) {
        try {
            OrderDto orderDto = orderService.getOrderByOrderId(orderId);
            return new ResponseEntity<>(orderDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> orders = orderService.getOrdersOfUser(userId);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrderWithOrderItemsByOrderId(@PathVariable String orderId) {
        try {
            String message = orderService.deleteOrderWithOrderItemsByOrderId(orderId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
