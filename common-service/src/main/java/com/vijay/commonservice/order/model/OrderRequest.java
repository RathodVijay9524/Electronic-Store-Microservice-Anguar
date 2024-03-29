package com.vijay.commonservice.order.model;

import com.vijay.commonservice.model.OrderItemDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRequest {

    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate=new Date();
    private Date deliveredDate;
    //private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
