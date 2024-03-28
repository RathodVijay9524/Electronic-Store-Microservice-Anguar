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
import java.util.UUID;

/**
 * Service implementation for authentication-related operations.
 */
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

    /**
     * Logs in a user using the provided login request.
     *
     * @param req Login request containing username/email and password.
     * @return LoginJWTResponse containing JWT token and user details upon successful authentication.
     */
    @Override
    public LoginJWTResponse login(LoginRequest req) {
        // Perform authentication using AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsernameOrEmail(), req.getPassword()));

        // Set the authentication object in SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Load UserDetails using UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsernameOrEmail());

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(authentication);

        // Map UserDetails to UserDto using ModelMapper
        UserDto response = mapper.map(userDetails, UserDto.class);

        // Build and return LoginJWTResponse
        return LoginJWTResponse.builder()
                .jwtToken(token)
                .user(response)
                .build();
    }

    /**
     * Registers a new user using the provided registration request.
     *
     * @param req Registration request containing user details.
     * @return Success message upon successful registration.
     * @throws AuthUserApiException if the username or email already exists or if the default role is not found.
     */
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

        // Generate a unique userId using UUID
        String userId = UUID.randomUUID().toString();

        // Map the RegistrationRequest to a new User object using ModelMapper
        User user = mapper.map(req, User.class);

        // Set the generated userId
        user.setUserId(userId);

       /* // Map the request to a new User object
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();*/

        // Encode the password before setting it in the User object
        user.setPassword(passwordEncoder.encode(req.getPassword()));

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


    /**
     * Registers a new worker using the provided registration request.
     *
     * @param req Registration request containing worker details.
     * @return RegistrationResponse containing worker details upon successful registration.
     * @throws AuthUserApiException if the username or email already exists, if the worker role is not found,
     *                              or if the current user cannot be retrieved.
     */
    @Override
    public RegistrationResponse registerWorker(RegistrationRequest req) {
        // Add check for username exists in database
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new AuthUserApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }
        // Add check for email exists in database
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new AuthUserApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        // Get the current user details
        UserDto currentUser = userService.getCurrentUser();

        // Map the current user details to a User entity
        User user = mapper.map(currentUser, User.class);

        // Create a new Worker entity from the registration request
        Worker worker = Worker.builder()
                .workerId(UUID.randomUUID().toString()) // Set workerId using UUID
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

        // Set the user for the worker
        worker.setUser(user);

        // Save the worker to the repository
        Worker savedWorker = workerRepository.save(worker);

        // Create a RegistrationResponse from the saved worker entity
        RegistrationResponse response = mapper.map(savedWorker, RegistrationResponse.class);

        // Set the userId in the response
        response.setUserId(savedWorker.getUser().getUserId());

        return response;
    }

    /**
     * Validates the authenticity of the provided JWT token.
     *
     * @param token JWT token to validate.
     */
    @Override
    public void validateToken(String token) {
        jwtTokenProvider.validateToken(token);
    }
}
