package com.example.cartservice.service;

import com.example.cartservice.client.CartItemFeignClient;
import com.example.cartservice.client.ProductFeignClient;
import com.example.cartservice.client.UserFeignClient;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.repository.CartRepository;
import com.vijay.commonservice.exception.BadApiRequestException;

import com.vijay.commonservice.order.model.AddItemToCartRequest;
import com.vijay.commonservice.order.model.CartDto;
import com.vijay.commonservice.order.model.CartItemDto;
import com.vijay.commonservice.product.model.ProductResponse;
import com.vijay.commonservice.user.response.UserDto;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class CartServiceImpl implements CartService{

    private final UserFeignClient userFeignClient;
    private final CartRepository cartRepository;
    private final ProductFeignClient productFeignClient;
    private final CartItemFeignClient cartItemFeignClient;

    @Override
    public String createCart(String userId, AddItemToCartRequest request) {
        log.info("Creating cart for user {}", userId);
        // Extract necessary information from the request
        String productId = request.getProductId();
        int quantity = request.getQuantity();
        log.debug("Received request to add product {} with quantity {} to cart for user {}", productId, quantity, userId);
        // Validate quantity
        if (quantity <= 0) {
            log.error("Invalid quantity {} received in request for user {}", quantity, userId);
            throw new BadApiRequestException("Requested quantity is not valid !!");
        }
        // Retrieve product and user information using Feign clients
        ProductResponse product = productFeignClient.getProductByProductId(productId);
        UserDto user = userFeignClient.getUser(userId);
        String userIds = user.getUserId();
        log.debug("Retrieved user information for user {} with ID {}", user.getUsername(), userIds);

        // Retrieve or create cart based on the user
        Cart cart = cartRepository.findByUserId(userIds)
                .orElseGet(() -> {
                    log.debug("No existing cart found for user {}, creating a new cart", userId);
                    Cart newCart = new Cart();
                    newCart.setCartId(UUID.randomUUID().toString());
                    newCart.setCreatedAt(new Date());
                    newCart.setUserId(userIds);
                    return newCart;
                });

        // Retrieve cart items for the user from the cart service
        List<CartItemDto> cartItemsByCartId = cartItemFeignClient.getCartItemsByCartId(cart.getCartId());
        log.debug("Retrieved {} cart items for cart {}", cartItemsByCartId.size(), cart.getCartId());

        // Store the old CartItemId outside the lambda expression scope
        AtomicReference<String> oldCartItemIdRef = new AtomicReference<>(null);

// Check if the cart item with the provided product ID already exists
        boolean itemExists = cartItemsByCartId.stream()
                .anyMatch(item -> {
                    if (item.getProduct().getProductId().equals(productId)) {
                        oldCartItemIdRef.set(item.getCartItemId()); // Store the old CartItemId
                        return true;
                    }
                    return false;
                });
        log.debug("Item with product ID {} already exists in cart: {}", productId, itemExists);

        if (itemExists) {
            // Item already present in cart, handle accordingly
            // Update its quantity, total price, and cart ID
            cart.getItems().stream()
                    .filter(item -> item.getProduct().getProductId().equals(productId))
                    .findFirst()
                    .ifPresent(item -> {
                        item.setQuantity(quantity);
                        item.setTotalPrice(quantity * product.getDiscountedPrice());
                        item.setCartId(cart.getCartId());

                        // Use the old CartItemId
                        System.out.println("Old CartItemId: " + oldCartItemIdRef.get());
                        cartItemFeignClient.updateCartItem(oldCartItemIdRef.get(),item);
                    });
        } else {
            // Item not present in cart, add it
            CartItemDto cartItemDto = new CartItemDto();
            BeanUtils.copyProperties(request, cartItemDto); // Copy properties from request to cartItemDto
            cartItemDto.setTotalPrice(quantity * product.getDiscountedPrice());
            cartItemDto.setUserId(userIds);
            cartItemDto.setProduct(product);
            cartItemDto.setCartId(cart.getCartId());
           // cart.getItems().add(cartItemDto);

            // Call the FeignClient to add the cart item
            cartItemFeignClient.addCartItemToCart(cartItemDto);
        }
        // Save or update the cart in the repository
        Cart savedCart = cartRepository.save(cart);
        // Map and return the saved cart as CartDto
        CartDto cartDto = new CartDto();
        BeanUtils.copyProperties(savedCart, cartDto);
         // Set the user details
        return "Cart Created Successfully With Items, Check DB";

    }


    @Override
    public void removeItemFromCart(String itemId) {
        log.info("Removing item with ID {} from the cart", itemId);
        cartItemFeignClient.removeCartItem(itemId);
        log.debug("Item with ID {} removed successfully from the cart", itemId);
    }

    @Override
    public void clearCart(String cartId) {
        log.info("Clearing cart with ID {}", cartId);
        cartItemFeignClient.deleteCartItemsByCartId(cartId);
        log.debug("Cart with ID {} cleared successfully", cartId);
    }

    @Override
    public CartDto getCartById(String cartId) {
        log.info("Retrieving cart with ID {}", cartId);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart with ID " + cartId + " not found"));
        CartDto cartDto = new CartDto();
        BeanUtils.copyProperties(cart, cartDto);
        UserDto user = userFeignClient.getUser(cart.getUserId());
        cartDto.setUser(user);
        List<CartItemDto> cartItems = cartItemFeignClient.getCartItemsByCartId(cartId);
        cartDto.setItems(cartItems);
        log.debug("Cart with ID {} retrieved successfully", cartId);
        return cartDto;
    }


}
