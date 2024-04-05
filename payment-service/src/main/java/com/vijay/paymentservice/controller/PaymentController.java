package com.vijay.paymentservice.controller;


import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;
import com.vijay.paymentservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<PaymentResponse> doPayment(@RequestBody PaymentRequest paymentRequest){
        PaymentResponse paymentResponse= paymentService.doPayment(paymentRequest);
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public List<PaymentResponse> getPaymentDetailsByUserId(@PathVariable String userId) {
        return paymentService.getPaymentDetailsByUserId(userId);
    }

    @GetMapping("/order/{orderId}")
    public PaymentResponse getPaymentDetailsByOrderId(@PathVariable String orderId) {
        return paymentService.getPaymentDetailsByOrderId(orderId);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(@PathVariable String paymentId, @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.updatePayment(paymentId, paymentRequest);
        return ResponseEntity.ok(response);
    }


}
