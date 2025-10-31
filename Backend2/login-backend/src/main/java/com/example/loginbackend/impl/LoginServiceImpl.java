package com.example.loginbackend.impl;

import com.example.loginbackend.exception.UnauthorizedException;
import com.example.loginbackend.mapper.LoginMapper;
import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ユーザーのログイン認証を実装するサービスクラス。
 * <p>
 * {@link LoginService} インターフェースを実装し、DBからユーザー情報を取得して認証処理を行います。
 * </p>
 *
 * <p>
 * 認証に失敗した場合は401をスローします。
 * </p>
 *
 * @see LoginService
 * @see UnauthorizedException
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    /** ユーザー情報を取得するためのマッパー */
    private final LoginMapper userMapper;

    /**
     * 指定されたユーザーIDとパスワードでログイン認証を行う。
     *
     * @param userId   ユーザーID
     * @param password パスワード
     * @return 認証に成功した場合は {@link LoginRequest} オブジェクト
     * @throws UnauthorizedException ユーザーIDまたはパスワードが正しくない場合に発生
     */
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
        }
    }
}
