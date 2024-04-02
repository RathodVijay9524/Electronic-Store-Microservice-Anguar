package com.example.cartitemservice.controller;

import com.example.cartitemservice.service.CartItemService;
import com.vijay.commonservice.order.model.AddItemToCartRequest;
import com.vijay.commonservice.order.model.CartItemDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-item")
@AllArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;


    @PostMapping
    public ResponseEntity<CartItemDto> addCartItem(@RequestBody AddItemToCartRequest request) {
        CartItemDto cartItemDto= cartItemService.addCartItem(request);
        return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItemDto>> getCartItemsByUserId(@PathVariable String userId) {
        List<CartItemDto> cartItems = cartItemService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItemDto>> getCartItemsByCartId(@PathVariable String cartId) {
        List<CartItemDto> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> getCartItemsByCartItemId(@PathVariable String cartItemId) {
        CartItemDto cartItemDto = cartItemService.getCartItemsByCartItemId(cartItemId);
        if (cartItemDto != null) {
            return ResponseEntity.ok(cartItemDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable String cartItemId,
                                                      @RequestBody AddItemToCartRequest request) {
        try {
            CartItemDto updatedCartItem = cartItemService.updateCartItem(cartItemId, request);
            return ResponseEntity.ok(updatedCartItem);
        } catch (Exception e) {
            // Handle the case where the cart item with the specified ID is not found
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable String cartItemId) {
        cartItemService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemDto>> deleteCartItemsByCartId(@PathVariable String cartId) {
        List<CartItemDto> deletedItems = cartItemService.deleteCartItemsByCartId(cartId);
        return ResponseEntity.ok(deletedItems);
    }

}
