package com.vijay.categoryservice.client;

import com.vijay.commonservice.user.response.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTH-SERVICE")
public interface UserFeignClient {

    @GetMapping("/api/auth/users/{userId}")
    UserDto getUserByUserId(@PathVariable String userId);

    @GetMapping("/api/auth/current")
    UserDto currentUser();

    @GetMapping("/api/auth/users/{userId}")
    UserDto getUser(@PathVariable String userId);

}
