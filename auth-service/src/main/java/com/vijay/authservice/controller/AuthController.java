package com.vijay.authservice.controller;

import com.vijay.authservice.config.security.JwtTokenProvider;
import com.vijay.authservice.model.*;
import com.vijay.authservice.service.AuthService;
import com.vijay.authservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.modelmapper.ModelMapper;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor                   // Constructor injection for dependencies
public class AuthController {
    // Autowired dependencies
    private final AuthService authService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    // Login REST API endpoint
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<LoginJWTResponse> login(@RequestBody LoginRequest request) {
        LoginJWTResponse login = authService.login(request);
        return ResponseEntity.ok(login);
    }
    // Generate token endpoint
    @PostMapping("/token")
    public String getToken(@RequestBody LoginRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsernameOrEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtTokenProvider.generateToken(authenticate);
        } else {
            throw new RuntimeException("Invalid access");
        }
    }

    // Validate token endpoint
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }

    // Register user endpoint
    @PostMapping(value = {"/signup"})
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        String response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Register worker endpoint
    @PostMapping("/worker")
    public ResponseEntity<RegistrationResponse> registerWorker(@RequestBody RegistrationRequest registerDto) {
        RegistrationResponse registerWorker = authService.registerWorker(registerDto);
        return new ResponseEntity<>(registerWorker, HttpStatus.CREATED);
    }

    // Get current user endpoint
    @GetMapping("/current")
    public ResponseEntity<UserDto> currentUser() {
        UserDto currentUser = userService.getCurrentUser();
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    // Get current user endpoint using Principal
    @GetMapping("/currents")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        return new ResponseEntity<>(modelMapper.map(
                userDetailsService.loadUserByUsername(principal.getName()), UserDto.class), HttpStatus.OK);
    }
}
