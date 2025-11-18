package com.example.loginbackend.rest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "message")
@Data
public class MessageProperties {
    private String basename;
    private String encoding;
}
