package com.example.loginbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final WebProperties webProperties;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(webProperties.getMapping())
                        .allowedOrigins(webProperties.getAllowedOrigins().toArray(new String[0]))
                        .allowedMethods(webProperties.getAllowedMethods().toArray(new String[0]))
                        .allowedHeaders(webProperties.getAllowedHeaders().toArray(new String[0]))
                        .allowCredentials(webProperties.isAllowCredentials());
            }
        };
    }
}
