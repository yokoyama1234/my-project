package com.example.loginbackend.service;

import com.example.loginbackend.model.LoginRequest;

public interface LoginService {
    LoginRequest login(String userId, String password);
}
