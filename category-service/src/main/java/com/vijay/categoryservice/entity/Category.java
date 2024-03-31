package com.vijay.categoryservice.entity;

import com.vijay.commonservice.product.model.ProductResponse;
import com.vijay.commonservice.user.response.UserDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("category_table")
public class Category {

    @Id
    private String categoryId;
    private String title;
    private String description;
    private String coverImage;
    private String userId;
    private UserDto user;
    private List<ProductResponse> products;

}