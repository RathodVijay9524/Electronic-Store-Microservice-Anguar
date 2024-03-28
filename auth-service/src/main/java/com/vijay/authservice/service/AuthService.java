package com.vijay.authservice.service;

import com.vijay.authservice.model.LoginJWTResponse;
import com.vijay.authservice.model.LoginRequest;
import com.vijay.authservice.model.RegistrationRequest;
import com.vijay.authservice.model.RegistrationResponse;

public interface AuthService {

    LoginJWTResponse login(LoginRequest req);

    void validateToken(String token);

    String register(RegistrationRequest req);

    RegistrationResponse registerWorker(RegistrationRequest req);
}
