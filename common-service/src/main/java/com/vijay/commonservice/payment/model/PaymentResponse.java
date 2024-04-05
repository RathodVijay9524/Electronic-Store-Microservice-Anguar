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
public class PaymentResponse {
    private String paymentId;
    private String userId;
    private String paymentStatus;
    private PaymentMode paymentMode;
    private long amount;
    private Instant paymentDate;
    private String orderId;
}
