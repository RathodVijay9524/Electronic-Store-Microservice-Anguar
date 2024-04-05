package com.vijay.paymentservice.repository;

import com.vijay.paymentservice.entity.TransactionDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends MongoRepository<TransactionDetails, String> {

    TransactionDetails findByOrderId(String orderId);

    List<TransactionDetails> findByUserId(String userId);
}
