package com.example.loginbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // RESTではCSRF無効
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/products", "/h2-console/**").permitAll() // ログインとH2は無条件許可
                        .anyRequest().authenticated() // 他は認証必須
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // H2用

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
