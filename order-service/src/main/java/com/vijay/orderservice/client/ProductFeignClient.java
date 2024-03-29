package com.vijay.orderservice.client;

import com.vijay.commonservice.product.model.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductFeignClient {

    @GetMapping("/api/products/{productId}")
    ProductResponse getProductByProductId(@PathVariable String productId);

    @GetMapping("/api/products/user/{userId}")
    List<ProductResponse> getProductsByUserId(@PathVariable String userId);

    @GetMapping("/api/products/category/{categoryId}")
    List<ProductResponse> getProductsByCategoryId(@PathVariable String categoryId);

}
