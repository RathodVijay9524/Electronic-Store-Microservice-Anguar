package com.vijay.authservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
