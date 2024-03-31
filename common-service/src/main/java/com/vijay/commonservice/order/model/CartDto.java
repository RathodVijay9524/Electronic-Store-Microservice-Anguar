package com.vijay.commonservice.order.model;

import lombok.*;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date createdAt;
    private String userId;
    private List<CartItemDto> items = new ArrayList<>();
}
