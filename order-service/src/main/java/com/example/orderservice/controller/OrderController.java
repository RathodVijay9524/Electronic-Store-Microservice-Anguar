package com.example.orderservice.controller;

import com.example.orderservice.service.OrderService;
import com.vijay.commonservice.order.model.CreateOrderRequest;
import com.vijay.commonservice.order.model.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
