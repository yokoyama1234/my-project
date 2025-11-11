package com.example.loginbackend.rest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {
    private List<String> permitAll;
    private SessionCreationPolicy sessionPolicy;
}