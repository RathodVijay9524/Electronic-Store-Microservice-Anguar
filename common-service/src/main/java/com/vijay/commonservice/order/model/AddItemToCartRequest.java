package com.vijay.commonservice.order.model;


import com.vijay.commonservice.product.model.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddItemToCartRequest {

    private String cartId;
    private String userId;
    private String productId;
    private ProductResponse product;
    private  int quantity;
    private  int totalPrice;
    private CartDto cart;


}