package com.example.cartitemservice.service;

import com.vijay.commonservice.order.model.AddItemToCartRequest;
import com.vijay.commonservice.order.model.CartItemDto;

import java.util.List;

public interface CartItemService {

    CartItemDto addCartItem(AddItemToCartRequest request);

    void removeCartItem(String cartItemId);

    List<CartItemDto> getCartItemsByUserId(String userId);

    List<CartItemDto> deleteCartItemsByCartId(String cartId);

    List<CartItemDto> getCartItemsByCartId(String cartId);

    CartItemDto getCartItemsByCartItemId(String cartItemId);

    CartItemDto updateCartItem(String cartItemId, AddItemToCartRequest request);
}
