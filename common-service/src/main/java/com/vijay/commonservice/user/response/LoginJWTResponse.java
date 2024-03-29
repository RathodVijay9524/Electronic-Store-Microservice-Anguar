package com.vijay.commonservice.user.response;

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
