package com.vijay.commonservice.config;

import feign.codec.ErrorDecoder;
import com.vijay.commonservice.client.decoder.CustomErrorDecoder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FeignCong {

    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}