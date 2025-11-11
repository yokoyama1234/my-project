package com.example.loginbackend.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {

    private final MessageProperties messageProperties;

    @Autowired
    public MessageConfig(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messageProperties.getBasename());
        messageSource.setDefaultEncoding(messageProperties.getEncoding());
        return messageSource;
    }
}