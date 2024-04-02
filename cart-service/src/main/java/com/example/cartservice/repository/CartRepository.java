package com.example.cartservice.repository;

import com.example.cartservice.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    // Define custom query methods if needed
    Optional<Cart> findByUserId(String userId);
}