package com.vijay.commonservice.order.model;

import com.vijay.commonservice.user.response.UserDto;
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
    private String userId;
    private Date createdAt;
    //mapping cart-items
    private List<CartItemDto> items = new ArrayList<>();
    private UserDto user;

}
