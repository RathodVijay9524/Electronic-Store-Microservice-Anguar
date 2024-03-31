package com.vijay.commonservice.product.model;

import com.vijay.commonservice.category.model.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;



    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class ProductRequest {

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
        private String productImageName;
        private CategoryResponse category;


    }

