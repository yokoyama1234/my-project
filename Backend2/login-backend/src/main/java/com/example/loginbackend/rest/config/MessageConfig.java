package com.example.loginbackend.rest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@RequiredArgsConstructor
public class MessageConfig {

    private final MessageProperties messageProperties;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messageProperties.getBasename());
        messageSource.setDefaultEncoding(messageProperties.getEncoding());
        return messageSource;
    }
}
