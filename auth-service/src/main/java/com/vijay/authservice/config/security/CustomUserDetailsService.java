package com.vijay.authservice.config.security;

import com.vijay.authservice.entity.User;
import com.vijay.authservice.entity.Worker;
import com.vijay.authservice.repository.UserRepository;
import com.vijay.authservice.repository.WorkerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.Optional;


@Service
@Primary
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;

    // Method to load user by username or email
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Check if a user exists with the given username or email
        Optional<User> superUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (superUser.isPresent()) {
            // If user exists, build CustomUserDetails from the user entity
            User users = superUser.get();
            return CustomUserDetails.build(users);
        } else {
            // If no user is found, check if a worker exists with the given username or email
            Optional<Worker> workerUser = workerRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            Worker worker = workerUser.orElseThrow(() -> new UsernameNotFoundException("Worker not found with username: " + usernameOrEmail));
            // Build CustomUserDetails from the worker entity
            return CustomUserDetails.build(worker);
        }
    }
}