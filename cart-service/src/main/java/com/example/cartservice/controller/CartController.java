package com.example.cartservice.controller;

import com.example.cartservice.service.CartService;
import com.vijay.commonservice.order.model.AddItemToCartRequest;
import com.vijay.commonservice.order.model.CartDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling cart-related endpoints.
 */
@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Endpoint for adding an item to the cart.
     *
     * @param userId  The ID of the user.
     * @param request The request containing details of the item to be added.
     * @return ResponseEntity containing the cart DTO with the added item.
     */
    @PostMapping("/add/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.createCart(userId, request);
        return ResponseEntity.ok(cartDto);
    }

    /**
     * Endpoint for retrieving the cart by its ID.
     *
     * @param cartId The ID of the cart.
     * @return ResponseEntity containing the cart DTO.
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable String cartId) {
        CartDto cartDto = cartService.getCartById(cartId);
        return ResponseEntity.ok(cartDto);
    }

    /**
     * Endpoint for clearing the cart by its ID.
     *
     * @param cartId The ID of the cart to be cleared.
     * @return ResponseEntity indicating the success of the operation.
     */
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable String cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cart cleared successfully");
    }

    /**
     * Endpoint for removing an item from the cart by its ID.
     *
     * @param itemId The ID of the item to be removed.
     * @return ResponseEntity indicating the success of the operation.
     */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable String itemId) {
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok("Item removed from the cart successfully.");
    }
}