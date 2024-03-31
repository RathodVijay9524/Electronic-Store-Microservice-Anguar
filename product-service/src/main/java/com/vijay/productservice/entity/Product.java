package com.vijay.productservice.entity;

import com.vijay.commonservice.category.model.CategoryResponse;
import com.vijay.commonservice.user.request.UserRequest;
import com.vijay.commonservice.user.response.UserDto;
import com.vijay.commonservice.user.response.UserResponse;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Document("product_table")
public class Product {
    @Id
    private String productId;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;
    private String categoryId;
    private String userId;
    private UserDto user;
    private CategoryResponse category;


}

