package com.vijay.commonservice.order.model;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;
    private String productId;
    private int quantity;
    private int totalPrice;
}
