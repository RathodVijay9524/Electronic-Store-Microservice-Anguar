package com.example.cartservice.service;

import com.example.cartservice.enitity.AddItemToCartRequest;
import com.vijay.commonservice.order.model.CartDto;

public interface CartService {

    CartDto addCart(String userid,AddItemToCartRequest request);
}
