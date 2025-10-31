package com.example.loginbackend.impl;

import com.example.loginbackend.exception.UnauthorizedException;
import com.example.loginbackend.mapper.LoginMapper;
import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper userMapper;

    @Override
    public LoginRequest login(String userId, String password) {
        try {
            LoginRequest user = userMapper.findByUserIdAndPassword(userId, password);
            if (user == null) {
                throw new UnauthorizedException("ユーザーIDまたはパスワードが正しくありません");
            }
            return user;
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("ログイン処理中に予期せぬエラーが発生しました", e);
        }
    }
}
