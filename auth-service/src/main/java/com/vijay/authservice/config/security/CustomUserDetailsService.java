package com.vijay.authservice.config.security;

import com.vijay.authservice.entity.User;
import com.vijay.authservice.entity.Worker;
import com.vijay.authservice.repository.UserRepository;
import com.vijay.authservice.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.Optional;


@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return CustomUserDetails.build(user);
        } else {
            Optional<Worker> workerOptional = workerRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            Worker worker = workerOptional.orElseThrow(() -> new UsernameNotFoundException("Worker not found with username: " + usernameOrEmail));
            return CustomUserDetails.build(worker);
        }
    }
}


