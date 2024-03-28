package com.vijay.authservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginJWTResponse {

    private String jwtToken;
    private UserDto user;

}
