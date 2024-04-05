package com.vijay.paymentservice.service;

import com.vijay.commonservice.payment.model.PaymentMode;
import com.vijay.commonservice.payment.model.PaymentRequest;
import com.vijay.commonservice.payment.model.PaymentResponse;
import com.vijay.commonservice.util.IdUtils;
import com.vijay.paymentservice.entity.TransactionDetails;
import com.vijay.paymentservice.repository.TransactionDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .id(IdUtils.generatePaymentId())
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .userId(paymentRequest.getUserId())
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();
        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction Completed with Id: {}", transactionDetails.getId());
        PaymentResponse paymentResponse=new PaymentResponse();
        BeanUtils.copyProperties(transactionDetails,paymentResponse);
        //int result = 10 / 0;
        return paymentResponse;
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment detail for the Order Id: {}", orderId);

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
    public List<PaymentResponse> getPaymentDetailsByUserId(String userId) {
        log.info("Getting payment details for the Order Id: {}", userId);

        List<TransactionDetails> transactionDetails= transactionDetailsRepository.findByUserId(userId);

        List<PaymentResponse> paymentResponse= transactionDetails.stream().map(payment -> {

            PaymentResponse response = PaymentResponse.builder()
                    .paymentId(payment.getId())
                    .paymentMode(PaymentMode.valueOf(payment.getPaymentMode()))
                    .paymentDate(payment.getPaymentDate())
                    .orderId(payment.getOrderId())
                    .paymentStatus(payment.getPaymentStatus())
                    .amount(payment.getAmount())
                    .build();
            return response;

        }).collect(Collectors.toList());

        return paymentResponse;
    }

    @Override
    public PaymentResponse updatePayment(String paymentId, PaymentRequest paymentRequest) {
        TransactionDetails transactionDetails= transactionDetailsRepository.findById(paymentId).get();
        transactionDetails.setPaymentStatus(paymentRequest.getPaymentStatus());
        transactionDetailsRepository.save(transactionDetails);
        PaymentResponse paymentResponse=new PaymentResponse();
        BeanUtils.copyProperties(transactionDetails,paymentResponse);
        return paymentResponse;
    }

    @Override
    public PaymentResponse getPaymentDetailsByPaymentId(String paymentId) {
        return null;
    }
}
