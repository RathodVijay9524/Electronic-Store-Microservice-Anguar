package com.vijay.authservice.service;
import com.vijay.authservice.config.security.JwtTokenProvider;
import com.vijay.authservice.entity.Role;
import com.vijay.authservice.entity.User;
import com.vijay.authservice.entity.Worker;
import com.vijay.authservice.exception.AuthUserApiException;
import com.vijay.authservice.model.*;
import com.vijay.authservice.repository.RoleRepo;
import com.vijay.authservice.repository.UserRepository;
import com.vijay.authservice.repository.WorkerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final ModelMapper mapper;
    private final WorkerRepository workerRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void validateToken(String token) {
        jwtTokenProvider.validateToken(token);
    }

    @Override
    public LoginJWTResponse login(LoginRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsernameOrEmail(), req.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsernameOrEmail());
        String token = jwtTokenProvider.generateToken(authentication);

        UserDto response = mapper.map(userDetails, UserDto.class);
        return LoginJWTResponse.builder()
                .jwtToken(token)
                .user(response)
                .build();
    }

    @Override
    public String register(RegistrationRequest req) {
        // Add check for username exists in database
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new AuthUserApiException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }

        // Add check for email exists in database
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new AuthUserApiException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }

        // Map the request to a new User object
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();

        // Fetch the user role from the repository
        Role userRole = roleRepository.findByRoleName("ROLE_NORMAL")
                .orElseThrow(() -> new AuthUserApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Default role not found."));

        // Initialize the roles field if it's null
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        // Add the user role to the roles set
        user.getRoles().add(userRole);

        // Save the user to the database
        userRepository.save(user);

        return "User registered successfully.";
    }

    @Override
    public RegistrationResponse registerWorker(RegistrationRequest req) {
        // add check for username exists in database
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new AuthUserApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new AuthUserApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        UserDto currentUser = userService.getCurrentUser();
        User user = mapper.map(currentUser, User.class);

        Worker worker = Worker.builder()
                .name(req.getName())
                .email(req.getEmail())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();

        // Fetch the worker role from the repository
        Role workerRole = roleRepository.findByRoleName("ROLE_WORKER")
                .orElseThrow(() -> new AuthUserApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Worker role not found."));
        // Initialize the roles field if it's null
        if (worker.getRoles() == null) {
            worker.setRoles(new HashSet<>());
        }
        // Add the worker role to the roles set
        worker.getRoles().add(workerRole);
        worker.setUser(user);
        workerRepository.save(worker);
        return mapper.map(worker, RegistrationResponse.class);

    }
}
