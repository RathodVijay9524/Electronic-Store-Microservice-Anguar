package com.vijay.commonservice.product.model;

import com.vijay.commonservice.category.model.CategoryResponse;
import com.vijay.commonservice.user.request.UserRequest;
import com.vijay.commonservice.user.response.UserDto;
import com.vijay.commonservice.user.response.UserResponse;
import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private String productId;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String categoryId;
    private String userId;
    private UserDto user;
    private String productImageName;
    private CategoryResponse category;



}
