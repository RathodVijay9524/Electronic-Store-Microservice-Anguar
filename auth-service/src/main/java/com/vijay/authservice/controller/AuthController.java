package com.vijay.authservice.controller;

import com.vijay.authservice.config.security.JwtTokenProvider;
import com.vijay.authservice.service.AuthService;
import com.vijay.authservice.service.UserService;
import com.vijay.commonservice.user.request.LoginRequest;
import com.vijay.commonservice.user.request.RegistrationRequest;
import com.vijay.commonservice.user.response.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = {"/login", "/signing"})
    public ResponseEntity<LoginJWTResponse> login(@RequestBody LoginRequest request) {
        logger.info("Attempting login for user: {}", request.getUsernameOrEmail());
        LoginJWTResponse login = authService.login(request);
        logger.info("Login successful for user: {}", request.getUsernameOrEmail());
        return ResponseEntity.ok(login);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody LoginRequest authRequest) {
        logger.info("Generating token for user: {}", authRequest.getUsernameOrEmail());
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsernameOrEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token = jwtTokenProvider.generateToken(authenticate);
            logger.info("Token generated successfully for user: {}", authRequest.getUsernameOrEmail());
            return token;
        } else {
            logger.error("Failed to generate token for user: {}", authRequest.getUsernameOrEmail());
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        logger.info("Validating token: {}", token);
        authService.validateToken(token);
        logger.info("Token validated successfully: {}", token);
        return "Token is valid";
    }

    @PostMapping(value = {"/signup"})
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        logger.info("Registering new user: {}", request.getEmail());
        String response = authService.register(request);
        logger.info("New user registered successfully: {}", request.getEmail());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/worker")
    public ResponseEntity<RegistrationResponse> registerWorker(@RequestBody RegistrationRequest registerDto) {
        logger.info("Registering new worker: {}", registerDto.getEmail());
        RegistrationResponse registerWorker = authService.registerWorker(registerDto);
        logger.info("New worker registered successfully: {}", registerDto.getEmail());
        return new ResponseEntity<>(registerWorker, HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> currentUser() {
        logger.info("Retrieving current user");
        UserDto currentUser = userService.getCurrentUser();
        logger.info("Current user retrieved successfully");
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @GetMapping("/currents")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        logger.info("Retrieving current user using Principal");
        UserDto currentUserDto = modelMapper.map(
                userDetailsService.loadUserByUsername(principal.getName()), UserDto.class);
        logger.info("Current user retrieved successfully using Principal");
        return new ResponseEntity<>(currentUserDto, HttpStatus.OK);
    }
}
