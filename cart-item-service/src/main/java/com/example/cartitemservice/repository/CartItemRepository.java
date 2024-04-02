package com.example.cartitemservice.repository;


import com.example.cartitemservice.entity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends MongoRepository<CartItem, String> {

    List<CartItem> findByUserId(String userId);

    List<CartItem> findByCartId(String cartId);
}