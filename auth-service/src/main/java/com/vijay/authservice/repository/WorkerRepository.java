package com.vijay.authservice.repository;

import com.vijay.authservice.entity.Worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;




@Repository
public interface WorkerRepository extends JpaRepository<Worker, String>{


    // Find worker by email
    Optional<Worker> findByEmail(String email);

    // Find worker by username or email
    Optional<Worker> findByUsernameOrEmail(String username, String email);

    // Find worker by username
    Optional<Worker> findByUsername(String username);

    // Check if username exists
    Boolean existsByUsername(String username);

    // Check if email exists
    Boolean existsByEmail(String email);
}
