package com.vijay.commonservice.category.model;

import com.vijay.commonservice.product.model.ProductResponse;
import com.vijay.commonservice.user.response.UserDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String categoryId;
    private String title;
    private String description;
    private String coverImage;
    private String userId;
    private UserDto user;
    private List<ProductResponse> products;
}
