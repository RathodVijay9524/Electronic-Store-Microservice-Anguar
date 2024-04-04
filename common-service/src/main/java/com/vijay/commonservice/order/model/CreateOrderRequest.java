package com.vijay.commonservice.order.model;

import com.vijay.commonservice.payment.model.PaymentMode;
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
    private String orderStatus = "CREATED";
    private String paymentStatus = "NOT PAID";
    private PaymentMode paymentMode;
}
