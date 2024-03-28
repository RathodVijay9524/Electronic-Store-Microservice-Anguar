package com.vijay.authservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    private String workerId;
    private String name;
    private String username;
    private String email;
    private String password;
}
