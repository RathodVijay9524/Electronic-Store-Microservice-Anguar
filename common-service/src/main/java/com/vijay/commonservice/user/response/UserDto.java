package com.vijay.commonservice.user.response;


import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String about;
    private String imageName;
    private Set<RoleDto> roles = new HashSet<>();
}
