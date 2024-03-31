package com.vijay.productservice.client;


import com.vijay.commonservice.category.model.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CATEGORY-SERVICE")
public interface CategoryServiceFeignClient {

    @GetMapping("/api/categories/{categoryId}")
    CategoryResponse getCategoryByCategoryId(@PathVariable String categoryId);

}
