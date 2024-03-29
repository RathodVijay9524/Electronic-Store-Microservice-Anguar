package com.vijay.authservice.client;


import com.vijay.commonservice.product.model.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface CategoryServiceFeignClient {

    @GetMapping("/api/categories/user/{userId}")
    List<CategoryResponse> getCategoryByUserId(@PathVariable String userId);

}
