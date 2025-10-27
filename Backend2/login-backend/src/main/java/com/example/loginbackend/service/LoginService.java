package com.example.loginbackend.service;

import com.example.loginbackend.mapper.UserMapper;
import com.example.loginbackend.model.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserMapper userMapper;

    public LoginService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public LoginRequest login(String userId, String password) {
        return userMapper.findByUserIdAndPassword(userId, password);
    }
}
