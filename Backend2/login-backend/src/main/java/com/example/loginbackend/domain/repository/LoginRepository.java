package com.example.loginbackend.domain.repository;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import com.example.loginbackend.domain.mapper.LoginMapper;
import com.example.loginbackend.domain.model.LoginUser;

@Repository
@RequiredArgsConstructor
public class LoginRepository {

    private final LoginMapper loginMapper;

    public LoginUser findByUserIdAndPassword(String userId, String password) {
        return loginMapper.findByUserIdAndPassword(userId, password);
    }

    public int updateUserName(Long id, String name) {
        return loginMapper.updateUserName(id, name);
    }
}
