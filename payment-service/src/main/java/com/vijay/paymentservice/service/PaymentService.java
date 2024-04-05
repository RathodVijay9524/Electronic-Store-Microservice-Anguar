package com.vijay.paymentservice.service;


import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse doPayment(PaymentRequest paymentRequest);

    List<PaymentResponse> getPaymentDetailsByUserId(String userId);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);

    PaymentResponse updatePayment(String paymentId, PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByPaymentId(String paymentId);
}
