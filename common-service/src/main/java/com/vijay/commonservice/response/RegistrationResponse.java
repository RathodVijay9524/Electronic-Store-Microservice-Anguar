package com.vijay.commonservice.response;


import com.vijay.commonservice.model.UserDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponse {

        private String message;
        private UserDto user;
}
