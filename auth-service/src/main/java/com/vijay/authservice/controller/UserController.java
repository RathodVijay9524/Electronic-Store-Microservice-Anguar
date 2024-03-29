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

@RestController
@RequestMapping("api/auth/users")
@AllArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Retrieving user by email: {}", email);
        UserDto userDto = userService.getUserByEmail(email);
        logger.info("User retrieved successfully by email: {}", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        logger.info("Searching for users with keywords: {}", keywords);
        List<UserDto> users = userService.searchUser(keywords);
        logger.info("Users found: {}", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        logger.info("Updating user with ID: {}", userId);
        UserDto updatedUserDto = userService.updateUser(userId, userDto);
        logger.info("User updated successfully: {}", updatedUserDto);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        logger.info("Deleting user with ID: {}", userId);
        userService.deleteUserById(userId);
        logger.info("User deleted successfully with ID: {}", userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User is deleted Successfully !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        logger.info("Retrieving user by ID: {}", userId);
        UserDto userDto = userService.findUserById(userId);
        logger.info("User retrieved successfully by ID: {}", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
