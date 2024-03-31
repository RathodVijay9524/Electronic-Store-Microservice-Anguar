package com.example.cartservice.enitity;

import com.vijay.commonservice.order.model.CartItemDto;
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
@Document("cart_tbl")
public class Cart {

    @Id
    private String cartId;
    private Date createdAt;
    private String userId;
    private List<CartItemDto> items = new ArrayList<>();
}
