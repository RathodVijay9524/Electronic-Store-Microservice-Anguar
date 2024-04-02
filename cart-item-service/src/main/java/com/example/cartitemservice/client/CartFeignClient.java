package com.example.cartitemservice.client;


import com.vijay.commonservice.order.model.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CART-SERVICE")
public interface CartFeignClient {

    @GetMapping("/api/carts/{cartId}")
    CartDto getCartByCartId(@PathVariable String cartId);
}
