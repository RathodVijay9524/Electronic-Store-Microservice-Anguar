package com.vijay.commonservice.response;

import com.vijay.commonservice.model.UserDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private UserDto user;
}
