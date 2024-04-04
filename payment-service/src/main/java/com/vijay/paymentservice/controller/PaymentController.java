package com.vijay.paymentservice.controller;


import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;
import com.vijay.paymentservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId) {
        return new ResponseEntity<>(
                paymentService.getPaymentDetailsByOrderId(orderId),
                HttpStatus.OK
        );
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(@PathVariable String orderId, @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.updatePayment(orderId, paymentRequest);
        return ResponseEntity.ok(response);
    }


}
