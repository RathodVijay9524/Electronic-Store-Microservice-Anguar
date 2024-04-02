package com.vijay.commonservice.user.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String userId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String about;
    private String imageName;
}
