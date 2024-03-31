package com.example.cartservice.service;

import com.example.cartservice.enitity.AddItemToCartRequest;
import com.example.cartservice.enitity.Cart;
import com.example.cartservice.repository.CartRepository;
import com.vijay.commonservice.order.model.CartDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final ModelMapper mapper;

    @Override
    public CartDto addCart(String userid,AddItemToCartRequest request) {

        return null;
    }
}
