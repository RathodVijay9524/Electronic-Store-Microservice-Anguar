package com.vijay.paymentservice.service;


import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;

public interface PaymentService {
    String doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
