package com.example.cartservice.controller;

import com.example.cartservice.service.CartService;
import com.vijay.commonservice.order.model.CartDto;
import jdk.jfr.Registered;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping
    public CartDto createCart(@RequestBody CartDto cartDto){
        //CartDto cartDto1= cartService.addCart(cartDto);
        return null;
    }
}
