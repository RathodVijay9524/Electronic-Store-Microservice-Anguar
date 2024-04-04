package com.vijay.commonservice.order.model;


import com.vijay.commonservice.payment.model.PaymentMode;
import com.vijay.commonservice.product.model.ProductResponse;
import com.vijay.commonservice.user.response.UserDto;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {


    private String cartItemId;
    private String cartId;
    private String userId;
    private  int quantity;
    private  int totalPrice;
    private PaymentMode paymentMode;
    private ProductResponse product;


}
