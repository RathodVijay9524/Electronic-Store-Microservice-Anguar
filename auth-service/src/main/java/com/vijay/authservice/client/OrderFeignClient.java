package com.vijay.authservice.client;


import com.vijay.commonservice.order.model.OrderDto;
import com.vijay.commonservice.order.model.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderFeignClient {


    @GetMapping("/user/{userId}")
    List<OrderDto> getOrdersOfUser(@PathVariable String userId);
}
