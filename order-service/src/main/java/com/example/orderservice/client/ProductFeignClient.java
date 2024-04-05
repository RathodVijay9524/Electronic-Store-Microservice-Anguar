package com.example.orderservice.client;

import com.vijay.commonservice.product.model.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductFeignClient {

    @GetMapping("/api/products/user/{userId}")
    List<ProductResponse> getProductsByUserId(@PathVariable String userId);

    @GetMapping("/api/products/{productId}")
    ProductResponse getProductByProductId(@PathVariable String productId);

    @PutMapping("/api/products/reduceQuantity/{id}")
    public ResponseEntity<String> reduceProductQuantity(
            @RequestParam long quantity,
            @PathVariable("id") String productId);

    @PutMapping("/api/products/increaseQuantity/{productId}")
    public ResponseEntity<String> increaseProductQuantity(
            @RequestParam long quantity,
            @PathVariable("productId") String productId);

}
