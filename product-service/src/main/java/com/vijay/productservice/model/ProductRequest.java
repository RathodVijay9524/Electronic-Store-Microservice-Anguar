package com.vijay.productservice.model;

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
    }

