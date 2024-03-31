package com.vijay.authservice.service;

import com.vijay.authservice.client.CategoryServiceFeignClient;
import com.vijay.authservice.client.ProductFeignClient;
import com.vijay.authservice.entity.User;
import com.vijay.commonservice.category.model.CategoryResponse;
import com.vijay.commonservice.product.model.ProductResponse;
import com.vijay.commonservice.user.exception.AuthUserApiException;
import com.vijay.commonservice.user.exception.ResourceNotFoundException;
import com.vijay.commonservice.user.response.UserDto;
import com.vijay.authservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for handling user-related operations.
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDetailsService userDetailsService;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryServiceFeignClient categoryServiceFeignClient;
    private final ProductFeignClient productFeignClient;

    /**
     * Retrieves user categories by user ID.
     *
     * @param userId The ID of the user.
     * @return UserDto representing the user with categories.
     * @throws ResourceNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public UserDto getUserByCategory(String userId) {
        // Retrieve user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        // Retrieve categories by user ID from Feign client
        List<CategoryResponse> categoryByUserId = categoryServiceFeignClient.getCategoryByUserId(user.getUserId());
        // Set categories to user
        user.setCategories(categoryByUserId);
        // Map user to UserDto and return
        return mapper.map(user, UserDto.class);
    }

    /**
     * Retrieves user products by user ID.
     *
     * @param userId The ID of the user.
     * @return UserDto representing the user with products.
     * @throws ResourceNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public UserDto getUserProductByUserId(String userId) {
        // Retrieve user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        // Retrieve products by user ID from Feign client
        List<ProductResponse> productsByUserId = productFeignClient.getProductsByUserId(user.getUserId());
        // Set products to user
        user.setProducts(productsByUserId);
        // Map user to UserDto and return
        return mapper.map(user, UserDto.class);
    }

    /**
     * Retrieves the details of the currently authenticated user.
     *
     * @return UserDto representing the current authenticated user.
     * @throws IllegalStateException if the user is not authenticated or user details are not found.
     */
    @Override
    public UserDto getCurrentUser() {
        // Retrieve the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication is null or if the user is anonymous
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalStateException("User is not authenticated.");
        }
        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Check if userDetails is null (user not found)
        if (userDetails == null) {
            throw new IllegalStateException("User details not found.");
        }
        return mapper.map(userDetails, UserDto.class);
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param userId ID of the user to retrieve.
     * @return UserDto representing the user with the specified ID.
     * @throws ResourceNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public UserDto findUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return mapper.map(user, UserDto.class);
    }

    /**
     * Updates the details of a user.
     *
     * @param userId   ID of the user to update.
     * @param userDto  UserDto containing the updated details.
     * @return UserDto representing the updated user.
     * @throws ResourceNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        // Retrieve the existing user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        // Map fields from UserDto to the existing User entity
        mapper.map(userDto, user);

        // Check if the password in the UserDto is different from the existing password
        if (!userDto.getPassword().equalsIgnoreCase(user.getPassword())) {
            // Encode and set the new password
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Ensure consistency by setting the userId and email from the existing user
        user.setUserId(userId);
        user.setEmail(user.getEmail());

        // Save the updated user to the repository
        User updatedSavedUser = userRepository.save(user);

        // Map the updated user to a UserDto and return it
        return mapper.map(updatedSavedUser, UserDto.class);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId ID of the user to delete.
     * @throws ResourceNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public void deleteUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email Email address of the user to retrieve.
     * @return UserDto representing the user with the specified email address.
     * @throws AuthUserApiException if the user with the specified email address is not found.
     */
    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> findByEmail = userRepository.findByEmail(email);
        try {
            User user = findByEmail.orElseThrow();
            return mapper.map(user, UserDto.class);
        } catch (NoSuchElementException ex) {
            throw new AuthUserApiException(HttpStatus.BAD_REQUEST, "User Email Is NotFound");
        }
    }

    /**
     * Searches for users based on the provided keywords.
     *
     * @param keywords Keywords to search for in usernames.
     * @return List of UserDto representing the users matching the search criteria.
     */
    @Override
    public List<UserDto> searchUser(String keywords) {
        List<User> users = userRepository.findByNameContaining(keywords);
        return users.stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> all = userRepository.findAll();
        return all.stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}