package com.example.loginbackend.service;

import com.example.loginbackend.model.LoginUser;

public interface LoginService {
    LoginUser login(String userId, String password);
}
