package com.vijay.orderservice.client;

import com.vijay.commonservice.payment.model.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "ORDER-SERVICE")
public interface PaymentFeignClient {

    @GetMapping("/api/payment/order/{orderId}")
    PaymentResponse getPaymentDetailsByOrderId(@PathVariable String orderId);
}
