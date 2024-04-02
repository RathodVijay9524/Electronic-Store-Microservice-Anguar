package com.example.cartservice.entity;

import com.vijay.commonservice.order.model.CartItemDto;
import com.vijay.commonservice.user.response.UserDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("cart")
public class Cart {

    @Id
    private String cartId;
    private String userId;
    private String productId;

    private Date createdAt;

    private UserDto user;
    //mapping cart-items

    private List<CartItemDto> items = new ArrayList<>();
}
