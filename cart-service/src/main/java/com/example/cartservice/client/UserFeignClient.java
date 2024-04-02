package com.example.cartservice.client;

import com.vijay.commonservice.user.response.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTH-SERVICE")
public interface UserFeignClient {


    @GetMapping("/api/auth/current")
    UserDto currentUser();

    @GetMapping("/api/auth/users/{userId}")
    UserDto getUser(@PathVariable String userId);

}
