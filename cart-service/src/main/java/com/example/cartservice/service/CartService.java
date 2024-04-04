package com.example.cartservice.service;

import com.vijay.commonservice.order.model.AddItemToCartRequest;
import com.vijay.commonservice.order.model.CartDto;

/**
 * Interface defining operations related to shopping carts.
 */
public interface CartService {

    /**
     * Creates a new cart for the specified user with the given request.
     *
     * @param userId  The ID of the user for whom the cart is created.
     * @param request The request containing details of the item to be added to the cart.
     * @return The created cart DTO.
     */
    String createCart(String userId, AddItemToCartRequest request);

    /**
     * Removes an item from the cart using its ID.
     *
     * @param itemId The ID of the item to be removed from the cart.
     */
    void removeItemFromCart(String itemId);

    /**
     * Clears the cart with the specified ID, removing all items from it.
     *
     * @param cartId The ID of the cart to be cleared.
     */
    void clearCart(String cartId);

    /**
     * Retrieves the cart with the specified ID.
     *
     * @param cartId The ID of the cart to be retrieved.
     * @return The cart DTO corresponding to the specified ID.
     */
    CartDto getCartById(String cartId);
}

