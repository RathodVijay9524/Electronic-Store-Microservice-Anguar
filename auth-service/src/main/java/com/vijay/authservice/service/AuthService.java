package com.vijay.authservice.service;

import com.vijay.authservice.model.LoginJWTResponse;
import com.vijay.authservice.model.LoginRequest;
import com.vijay.authservice.model.RegistrationRequest;
import com.vijay.authservice.model.RegistrationResponse;

public interface AuthService {

    /**
     * Logs in a user using the provided login request.
     *
     * @param req Login request containing username/email and password.
     * @return LoginJWTResponse containing JWT token and user details upon successful authentication.
     */
    LoginJWTResponse login(LoginRequest req);

    /**
     * Validates the authenticity of the provided JWT token.
     *
     * @param token JWT token to validate.
     */
    void validateToken(String token);

    /**
     * Registers a new user using the provided registration request.
     *
     * @param req Registration request containing user details.
     * @return Success message upon successful registration.
     */
    String register(RegistrationRequest req);

    /**
     * Registers a new worker using the provided registration request.
     *
     * @param req Registration request containing worker details.
     * @return RegistrationResponse containing worker details upon successful registration.
     */
    RegistrationResponse registerWorker(RegistrationRequest req);

}
