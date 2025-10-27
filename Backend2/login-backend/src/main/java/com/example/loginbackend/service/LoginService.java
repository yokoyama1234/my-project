package com.example.loginbackend.service;

import com.example.loginbackend.model.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private static final String FIXED_USER_ID = "user1";
    private static final String FIXED_PASSWORD = "password123";

    public Optional<LoginRequest> login(String userId, String password) {
        if (FIXED_USER_ID.equals(userId) && FIXED_PASSWORD.equals(password)) {
            LoginRequest user = new LoginRequest();
            user.setUserId(userId);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
