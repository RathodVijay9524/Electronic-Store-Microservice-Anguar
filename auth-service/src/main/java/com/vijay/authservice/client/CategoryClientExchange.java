package com.vijay.authservice.client;

import com.vijay.commonservice.category.model.CategoryResponse;;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
@HttpExchange
public interface CategoryClientExchange {

    @GetExchange("/api/categories/user/{userId}")
    List<CategoryResponse> getCategoryByUserId(@PathVariable String userId);
}
