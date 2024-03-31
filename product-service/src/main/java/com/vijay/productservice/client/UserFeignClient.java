package com.vijay.productservice.client;

import com.vijay.commonservice.user.response.UserDto;
import com.vijay.commonservice.user.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
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
