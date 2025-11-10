package com.example.loginbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        private final SecurityProperties securityProperties;

        @Autowired
        public SecurityConfig(SecurityProperties securityProperties) {
                this.securityProperties = securityProperties;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> {
                                })
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(securityProperties.getPermitAll()
                                                                .toArray(new String[0]))
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(securityProperties.getSessionPolicy()));

                return http.build();
        }
}