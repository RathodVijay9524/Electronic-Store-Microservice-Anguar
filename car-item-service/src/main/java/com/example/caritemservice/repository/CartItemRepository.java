package com.example.caritemservice.repository;

import com.example.caritemservice.enitity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartItemRepository extends MongoRepository<CartItem, Integer> {
    List<CartItem> findByCartId(String cartId);
}
