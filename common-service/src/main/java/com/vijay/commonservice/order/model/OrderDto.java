package com.vijay.commonservice.order.model;

import com.vijay.commonservice.payment.model.PaymentMode;
import com.vijay.commonservice.payment.model.PaymentResponse;
import com.vijay.commonservice.user.response.UserDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String orderId;
    private String userId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate=new Date();
    private Date deliveredDate;
    private PaymentMode paymentMode;
    //private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private PaymentResponse payments;

    //add this to get user information with order
    private UserDto user;
}
