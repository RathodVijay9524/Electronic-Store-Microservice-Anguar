package com.example.orderservice.client;

import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentFeignClientService {

    @PostMapping("/api/payment")
    PaymentResponse doPayment(@RequestBody PaymentRequest paymentRequest);

   /* @PostMapping("/api/payment")
    public ResponseEntity<Void> doPayments(@RequestBody PaymentRequest paymentRequest);
*/


}