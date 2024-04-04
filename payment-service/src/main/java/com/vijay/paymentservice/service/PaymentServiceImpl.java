package com.vijay.paymentservice.service;

import com.vijay.commonservice.payment.model.PaymentMode;
import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;
import com.vijay.paymentservice.entity.TransactionDetails;
import com.vijay.paymentservice.repository.TransactionDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public PaymentResponse doPayment(PaymentRequest paymentRequest) {

        log.info("Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails
                =TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();
        transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction Completed with Id: {}", transactionDetails.getId());
        PaymentResponse paymentResponse=new PaymentResponse();
        BeanUtils.copyProperties(transactionDetails,paymentResponse);
        return paymentResponse;
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment details for the Order Id: {}", orderId);

        TransactionDetails transactionDetails
                = transactionDetailsRepository.findByOrderId(orderId);

        PaymentResponse paymentResponse
                = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId())
                .paymentStatus(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .build();

        return paymentResponse;
    }

    @Override
    public PaymentResponse updatePayment(String orderId, PaymentRequest paymentRequest) {
        TransactionDetails paymentDetails= transactionDetailsRepository.findByOrderId(orderId);
        paymentDetails.setPaymentStatus(paymentRequest.getPaymentStatus());
        PaymentResponse paymentResponse=new PaymentResponse();
        transactionDetailsRepository.save(paymentDetails);
        BeanUtils.copyProperties(paymentDetails,paymentResponse);
        return paymentResponse;
    }
}
