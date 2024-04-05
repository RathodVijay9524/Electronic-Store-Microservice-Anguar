package com.example.orderservice.client;

import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentFeignClientService {

    @PostMapping("/api/payment")
    PaymentResponse doPayment(@RequestBody PaymentRequest paymentRequest);

    @PutMapping("/api/payment/{paymentId}")
    PaymentResponse updatePaymentStatus(@PathVariable String paymentId, @RequestBody PaymentRequest paymentRequest);

    @GetMapping("/api/payment/order/{orderId}")
    PaymentResponse getPaymentDetailsByOrderId(@PathVariable String orderId);

}