package com.vijay.authservice.service;

import com.vijay.commonservice.user.response.UserDto;

import java.util.List;
/**
 * Service interface for user-related operations.
 */
public interface UserService {

    /**
     * Retrieves details of the current authenticated user.
     *
     * @return UserDto representing the current user.
     */
    UserDto getCurrentUser();

    /**
     * Retrieves details of a user by their ID.
     *
     * @param userId ID of the user to retrieve.
     * @return UserDto representing the user with the specified ID.
     */
    UserDto findUserById(String userId);

    /**
     * Updates details of a user with the provided user ID and user DTO.
     *
     * @param userId  ID of the user to update.
     * @param userDto UserDto containing updated user details.
     * @return UserDto representing the updated user.
     */
    UserDto updateUser(String userId, UserDto userDto);

    /**
     * Deletes a user with the specified ID.
     *
     * @param userId ID of the user to delete.
     */
    void deleteUserById(String userId);

    /**
     * Retrieves details of a user by their email address.
     *
     * @param email Email address of the user to retrieve.
     * @return UserDto representing the user with the specified email address.
     */
    UserDto getUserByEmail(String email);

    /**
     * Searches for users based on provided keywords.
     *
     * @param keywords Keywords to search for users.
     * @return List of UserDto objects matching the search criteria.
     */
    List<UserDto> searchUser(String keywords);
}
