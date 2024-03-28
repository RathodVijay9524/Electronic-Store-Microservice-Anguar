package com.vijay.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    private String workerId;
    private String userId;
    private String name;
    private String username;
    private String email;
    private String password;

    private Set<RoleDto> roles = new HashSet<>();
}
