package com.vijay.authservice.client;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@AllArgsConstructor
public class WebClientConfig {


    private final LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient categoryWebClient() {
        return WebClient.builder().baseUrl("http://CATEGORY-SERVICE")
                .filter(filterFunction)
                .build();
    }
    @Bean
    public CategoryClientExchange inventoryClient() {
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(categoryWebClient())).build()
                .createClient(CategoryClientExchange.class);
    }

}