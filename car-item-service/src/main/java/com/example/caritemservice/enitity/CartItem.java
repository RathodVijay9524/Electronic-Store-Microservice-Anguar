package com.example.caritemservice.enitity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("cart_item_tbl")
public class CartItem {

    @Id
    private int cartItemId;
    private String cartId;
    private String productId;
    private int quantity;
    private int totalPrice;
}