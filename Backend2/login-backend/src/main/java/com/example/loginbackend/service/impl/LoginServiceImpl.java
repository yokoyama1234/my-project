package com.example.loginbackend.service.impl;

import com.example.loginbackend.mapper.UserMapper;
import com.example.loginbackend.model.LoginUser;
import com.example.loginbackend.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;

    public LoginServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public LoginUser login(String userId, String password) {
        return userMapper.findByUserIdAndPassword(userId, password);
    }
}
