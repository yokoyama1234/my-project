package com.example.loginbackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "cors")
@Data
public class CorsProperties {
    private String mapping;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
}
