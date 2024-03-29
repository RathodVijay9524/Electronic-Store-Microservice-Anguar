package com.vijay.paymentservice.controller;


import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;
import com.vijay.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> doPayment(@RequestBody PaymentRequest paymentRequest){
        String paymentId= paymentService.doPayment(paymentRequest);
        return new ResponseEntity<>(paymentId, HttpStatus.CREATED);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId) {
        return new ResponseEntity<>(
                paymentService.getPaymentDetailsByOrderId(orderId),
                HttpStatus.OK
        );
    }


}
