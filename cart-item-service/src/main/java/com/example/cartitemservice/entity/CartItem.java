package com.example.cartitemservice.entity;

import com.vijay.commonservice.order.model.CartDto;
import com.vijay.commonservice.product.model.ProductResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("cart_Item")
public class CartItem {

    @Id
    private String cartItemId;
    private String cartId;
    private String userId;
    private  int quantity;
    private  int totalPrice;
    private CartDto cart;
    private ProductResponse product;


}
