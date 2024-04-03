package com.example.orderservice.client;


import com.vijay.commonservice.order.model.CartItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign client interface for interacting with the CART-ITEM-SERVICE.
 */
@FeignClient(name = "CART-ITEM-SERVICE")
public interface CartItemFeignClient {

    /**
     * Endpoint for updating a cart item by its ID.
     *
     * @param cartItemId The ID of the cart item to update.
     * @param request    The request body containing the updated cart item details.
     * @return CartItemDto representing the updated cart item.
     */
    @PutMapping("/api/cart-item/{cartItemId}")
    CartItemDto updateCartItem(@PathVariable String cartItemId, @RequestBody CartItemDto request);
    /**
     * Endpoint for retrieving a cart item by its ID.
     *
     * @param cartItemId The ID of the cart item to retrieve.
     * @return CartItemDto representing the retrieved cart item.
     */
    @GetMapping("/api/cart-item/{cartItemId}")
    CartItemDto getCartItemsByCartItemId(@PathVariable String cartItemId);

    /**
     * Endpoint for adding a new cart item to the cart.
     *
     * @param request The request body containing details of the cart item to add.
     * @return CartItemDto representing the added cart item.
     */
    @PostMapping("/api/cart-item")
    CartItemDto addCartItemToCart(@RequestBody CartItemDto request);

    /**
     * Endpoint for retrieving cart items by user ID.
     *
     * @param userId The ID of the user.
     * @return List of CartItemDto representing cart items associated with the user.
     */
    @GetMapping("/api/cart-item/user/{userId}")
    List<CartItemDto> getCartItemsByUserId(@PathVariable String userId);

    /**
     * Endpoint for retrieving cart items by cart ID.
     *
     * @param cartId The ID of the cart.
     * @return List of CartItemDto representing cart items associated with the cart.
     */
    @GetMapping("/api/cart-item/cart/{cartId}")
    List<CartItemDto> getCartItemsByCartId(@PathVariable String cartId);

    /**
     * Endpoint for removing a cart item by its ID.
     *
     * @param cartItemId The ID of the cart item to remove.
     */
    @DeleteMapping("/api/cart-item/{cartItemId}")
    void removeCartItem(@PathVariable String cartItemId);

    /**
     * Endpoint for deleting cart items by cart ID.
     *
     * @param cartId The ID of the cart.
     * @return List of CartItemDto representing the deleted cart items.
     */
    @DeleteMapping("/api/cart-item/{cartId}/items")
    List<CartItemDto> deleteCartItemsByCartId(@PathVariable String cartId);
}
