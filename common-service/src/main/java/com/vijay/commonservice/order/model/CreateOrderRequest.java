package com.vijay.commonservice.order.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {


    private String cartId;
    private String userId;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private String orderStatus = "PENDING";
    private String paymentStatus = "NOTPAID";


}
