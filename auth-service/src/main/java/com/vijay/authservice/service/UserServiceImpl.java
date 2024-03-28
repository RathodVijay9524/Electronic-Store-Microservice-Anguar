package com.vijay.authservice.service;

import com.vijay.authservice.entity.User;
import com.vijay.authservice.exception.AuthUserApiException;
import com.vijay.authservice.exception.ResourceNotFoundException;
import com.vijay.authservice.model.UserDto;
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

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDetailsService userDetailsService;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
    @Override
    public UserDto findUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return mapper.map(user, UserDto.class);
    }
    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        // user.setEmail(userDto.getEmail());
        if (!userDto.getPassword().equalsIgnoreCase(user.getPassword()))
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // save data
        User updatedUser = userRepository.save(user);
        return mapper.map(updatedUser, UserDto.class);
    }
    @Override
    public void deleteUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
    }
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
    @Override
    public List<UserDto> searchUser(String keywords) {
        List<User> users = userRepository.findByNameContaining(keywords);
        return users.stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}
