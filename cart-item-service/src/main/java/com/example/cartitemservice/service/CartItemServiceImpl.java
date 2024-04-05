package com.example.cartitemservice.service;


import com.example.cartitemservice.entity.CartItem;
import com.example.cartitemservice.repository.CartItemRepository;
import com.vijay.commonservice.order.model.AddItemToCartRequest;
;
import com.vijay.commonservice.order.model.CartItemDto;
import com.vijay.commonservice.user.exception.ResourceNotFoundException;
import com.vijay.commonservice.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItemDto addCartItem(AddItemToCartRequest request) {
        log.info("Adding cart item for user {}", request.getUserId());
        CartItem cartItem = new CartItem();
        cartItem.setTotalPrice(request.getTotalPrice());
        cartItem.setCartItemId(IdUtils.generateCartItemId());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setCartId(request.getCartId());
        cartItem.setProduct(request.getProduct());
        cartItem.setUserId(request.getUserId());
        cartItem.setTotalPrice(request.getTotalPrice());
        log.debug("Saving cart item: {}", cartItem);
        cartItem = cartItemRepository.save(cartItem);
        CartItemDto cartItemDto = new CartItemDto();
        BeanUtils.copyProperties(cartItem, cartItemDto);
        log.info("Cart item added successfully");
        return cartItemDto;
    }

    @Override
    public void removeCartItem(String cartItemId) {
        log.info("Removing cart item with ID {}", cartItemId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "Id", cartItemId));
        log.debug("Deleting cart item: {}", cartItem);
        cartItemRepository.delete(cartItem);
        log.info("Cart item removed successfully");
    }

    @Override
    public List<CartItemDto> getCartItemsByUserId(String userId) {
        log.info("Retrieving cart items for user {}", userId);
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        log.debug("Retrieved {} cart items", cartItems.size());
        List<CartItemDto> cartItemDtos = cartItems.stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = new CartItemDto();
                    BeanUtils.copyProperties(cartItem, cartItemDto);
                    return cartItemDto;
                })
                .collect(Collectors.toList());
        log.info("Cart items retrieved successfully");
        return cartItemDtos;
    }

    @Override
    public List<CartItemDto> deleteCartItemsByCartId(String cartId) {
        log.info("Deleting cart items for cart ID {}", cartId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        log.debug("Retrieved {} cart items for deletion", cartItems.size());
        cartItemRepository.deleteAll(cartItems);
        log.info("Cart items deleted successfully");
        List<CartItemDto> deletedItems = cartItems.stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = new CartItemDto();
                    BeanUtils.copyProperties(cartItem, cartItemDto);
                    return cartItemDto;
                })
                .collect(Collectors.toList());
        return deletedItems;
    }

    @Override
    public List<CartItemDto> getCartItemsByCartId(String cartId) {
        // Retrieve cart items by cartId from the repository
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        // Map cart items to CartItemDto objects using stream and map
        List<CartItemDto> cartItemDtos = cartItems.stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = new CartItemDto();
                    // Copy properties from CartItem to CartItemDto using BeanUtils
                    BeanUtils.copyProperties(cartItem, cartItemDto);
                    return cartItemDto;
                })
                .collect(Collectors.toList());
        return cartItemDtos;
    }

    @Override
    public CartItemDto getCartItemsByCartItemId(String cartItemId) {
        log.info("Retrieving cart item with ID {}", cartItemId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "Id", cartItemId));
        CartItemDto cartItemDto = new CartItemDto();
        BeanUtils.copyProperties(cartItem, cartItemDto);
        log.info("Cart item retrieved successfully");
        return cartItemDto;
    }

    @Override
    public CartItemDto updateCartItem(String cartItemId, AddItemToCartRequest request) {
        log.info("Updating cart item with ID {}", cartItemId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "Id", cartItemId));
        cartItem.setQuantity(request.getQuantity());
        cartItem.setTotalPrice(request.getTotalPrice());
        cartItem.setProduct(request.getProduct());
        cartItem.setUserId(request.getUserId());
        cartItem.setCartId(request.getCartId());
        log.debug("Updated cart item: {}", cartItem);
        cartItem = cartItemRepository.save(cartItem);
        CartItemDto updatedCartItemDto = new CartItemDto();
        BeanUtils.copyProperties(cartItem, updatedCartItemDto);
        log.info("Cart item updated successfully");
        return updatedCartItemDto;
    }

}



