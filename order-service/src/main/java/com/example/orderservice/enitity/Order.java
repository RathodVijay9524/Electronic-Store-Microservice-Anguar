package com.example.orderservice.enitity;


import com.vijay.commonservice.order.model.OrderItemDto;
import com.vijay.commonservice.payment.model.PaymentMode;
import com.vijay.commonservice.payment.model.PaymentResponse;
import com.vijay.commonservice.user.response.UserDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document("orders")
public class Order {

    @Id
    private String orderId;
    private String userId;

    private String orderStatus;

    //NOT-PAID, PAID
    //enum
    //boolean- false=>NOTPAID  || true=>PAID
    private String paymentStatus;

    private int orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderedDate;

    private Date deliveredDate;

    private PaymentMode paymentMode;

    private List<OrderItemDto> orderItems = new ArrayList<>();
    private PaymentResponse payments;
    private UserDto user;


}
