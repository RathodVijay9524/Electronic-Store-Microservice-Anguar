package com.vijay.paymentservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    // Define a bean for ModelMapper
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
