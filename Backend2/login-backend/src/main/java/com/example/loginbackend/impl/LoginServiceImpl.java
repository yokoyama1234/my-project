package com.example.loginbackend.impl;

import com.example.loginbackend.mapper.LoginMapper;
import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginMapper userMapper;

    public LoginServiceImpl(LoginMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public LoginRequest login(String userId, String password) {
        return userMapper.findByUserIdAndPassword(userId, password);
    }
}
