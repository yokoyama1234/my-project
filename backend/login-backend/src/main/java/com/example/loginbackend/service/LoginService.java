package com.example.loginbackend.service;

import com.example.loginbackend.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    // 固定ユーザ情報
    private static final String FIXED_USER_ID = "user1";
    private static final String FIXED_PASSWORD = "password123";

    public Optional<User> login(String userId, String password) {
        // 固定値チェック
        if (FIXED_USER_ID.equals(userId) && FIXED_PASSWORD.equals(password)) {
            User user = new User();
            user.setUserId(userId);
            user.setName("テストユーザ"); // 任意
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
