package com.vijay.categoryservice.client;

import com.vijay.commonservice.product.model.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductFeignClient {


    @GetMapping("/api/products/category/{categoryId}")
    List<ProductResponse> getProductsByCategoryId(@PathVariable String categoryId);

}
