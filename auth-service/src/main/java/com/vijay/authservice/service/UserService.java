package com.vijay.authservice.service;

import com.vijay.authservice.model.UserDto;

import java.util.List;

public interface UserService {

    UserDto getCurrentUser();
    UserDto findUserById(String userId);
    UserDto updateUser(String userId, UserDto userDto);
    void deleteUserById(String userId);
    UserDto getUserByEmail(String email);
    List<UserDto> searchUser(String keywords);
}
