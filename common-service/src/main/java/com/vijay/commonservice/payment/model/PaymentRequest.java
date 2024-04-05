package com.vijay.commonservice.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private String orderId;
    private String userId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
    private String paymentStatus;
}
