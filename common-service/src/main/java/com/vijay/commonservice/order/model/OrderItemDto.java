package com.vijay.commonservice.order.model;


import com.vijay.commonservice.product.model.ProductResponse;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {


    private String orderItemId;
    private String userId;
    private String orderId;
    private String productId;
    private int quantity;
    private int totalPrice;
    private ProductResponse product;
    private OrderDto order;


}
