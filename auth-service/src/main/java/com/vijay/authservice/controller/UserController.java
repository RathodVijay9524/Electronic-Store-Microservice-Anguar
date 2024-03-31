package com.vijay.authservice.controller;

import com.vijay.commonservice.user.exception.ApiResponseMessage;
import com.vijay.commonservice.user.response.UserDto;
import com.vijay.authservice.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
@AllArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Retrieves user categories by user ID.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing the UserDto.
     */
    @GetMapping("/categories/{userId}")
    public ResponseEntity<UserDto> getUsersCategoryByCategory(@PathVariable String userId) {
        // Retrieve user categories by user ID
        UserDto userDto = userService.getUserByCategory(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Retrieves user products by user ID.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing the UserDto.
     */
    @GetMapping("/products/{userId}")
    public ResponseEntity<UserDto> getUserProductsByUserId(@PathVariable String userId) {
        // Retrieve user products by user ID
        UserDto userDto = userService.getUserProductByUserId(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Retrieves user by email.
     *
     * @param email The email of the user.
     * @return ResponseEntity containing the UserDto.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Retrieving user by email: {}", email);
        // Retrieve user by email
        UserDto userDto = userService.getUserByEmail(email);
        logger.info("User retrieved successfully by email: {}", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Searches for users with keywords.
     *
     * @param keywords The keywords to search for.
     * @return ResponseEntity containing a list of UserDto.
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        logger.info("Searching for users with keywords: {}", keywords);
        // Search for users with keywords
        List<UserDto> users = userService.searchUser(keywords);
        logger.info("Users found: {}", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Updates or creates a user.
     *
     * @param userId   The ID of the user to update.
     * @param userDto  The DTO containing updated user information.
     * @return         ResponseEntity containing the updated user DTO.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        logger.info("Creating or updating user with ID: {}", userId);
        // Update or create user
        UserDto updatedUserDto = userService.updateUser(userId, userDto);
        logger.info("User created/updated successfully: {}", updatedUserDto);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId The ID of the user to delete.
     * @return       ResponseEntity containing a message.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        logger.info("Deleting user with ID: {}", userId);
        // Delete user by ID
        userService.deleteUserById(userId);
        logger.info("User deleted successfully with ID: {}", userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User is deleted Successfully !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return       ResponseEntity containing the UserDto.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        logger.info("Retrieving user by ID: {}", userId);
        // Retrieve user by ID
        UserDto userDto = userService.findUserById(userId);
        logger.info("User retrieved successfully by ID: {}", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of UserDto objects representing all users.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        // Retrieve all users from the repository
        List<UserDto> all = userService.getAllUsers();
        // Return the list of UserDto objects in the response entity
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
