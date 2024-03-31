package com.example.orderitemservice.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class OrderItem {


    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private String productId;
}