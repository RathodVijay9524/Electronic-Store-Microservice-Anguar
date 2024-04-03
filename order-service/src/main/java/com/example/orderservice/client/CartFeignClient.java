package com.example.orderservice.client;

import com.vijay.commonservice.order.model.AddItemToCartRequest;
import com.vijay.commonservice.order.model.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CART-SERVICE")
public interface CartFeignClient {

    @PostMapping("/api/carts/add/{userId}")
    CartDto addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request);

    @GetMapping("/api/carts/{cartId}")
    CartDto getCartById(@PathVariable String cartId);

    @DeleteMapping("api/carts/{cartId}/clear")
    String clearCart(@PathVariable String cartId);

    @DeleteMapping("api/carts/items/{itemId}")
    String removeItemFromCart(@PathVariable String itemId);


}
