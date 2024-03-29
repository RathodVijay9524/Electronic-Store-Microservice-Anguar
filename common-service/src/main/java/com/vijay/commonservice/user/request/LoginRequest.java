package com.vijay.commonservice.user.request;

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
