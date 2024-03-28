package com.vijay.authservice.repository;

import com.vijay.authservice.entity.User;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Find user by email
    Optional<User> findByEmail(String email);

    // Find user by username or email
    Optional<User> findByUsernameOrEmail(String username, String email);

    // Find user by username
    Optional<User> findByUsername(String username);

    // Check if username exists
    Boolean existsByUsername(String username);

    // Check if email exists
    Boolean existsByEmail(String email);

    // Find user by email and password (for login)
    Optional<User> findByEmailAndPassword(String email, String password);

    // Find users by name containing certain keywords
    List<User> findByNameContaining(String keywords);

    // Find user by name
    Optional<User> findByName(String username);
}
