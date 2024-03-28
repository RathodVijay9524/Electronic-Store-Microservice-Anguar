package com.vijay.authservice.controller;

import com.vijay.authservice.exception.ApiResponseMessage;
import com.vijay.authservice.model.UserDto;
import com.vijay.authservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.List;

@RestController
@RequestMapping("api/auth/users")
@AllArgsConstructor                       // Constructor injection for dependency
public class UserController {

    // Autowired dependency
    private final UserService userService;

    // Get user by email endpoint
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }
    // Search user endpoint
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }
    // Update user endpoint
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }
    // Delete user endpoint
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        userService.deleteUserById(userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User is deleted Successfully !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    // Get single user endpoint
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }
}

