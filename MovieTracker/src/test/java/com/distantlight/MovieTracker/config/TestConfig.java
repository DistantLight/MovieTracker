package com.distantlight.MovieTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(lazyInit = true)
public class TestConfig {
    @Bean
    public RestTemplate restTemplateTest() {
        return new RestTemplate();
    }
}
