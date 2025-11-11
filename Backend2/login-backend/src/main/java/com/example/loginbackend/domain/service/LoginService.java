package com.example.loginbackend.domain.service;

import com.example.loginbackend.domain.model.LoginRequest;

/**
 * ユーザーのログイン認証処理を提供するサービスインターフェース。
 * <p>
 * 実装クラスでは、ユーザーIDとパスワードを用いた認証処理を行い、
 * 認証に成功した場合はユーザー情報を返却します。
 * </p>
 *
 * @see com.example.loginbackend.domain.impl.LoginServiceImpl
 */
public interface LoginService {

    /**
     * 指定されたユーザーIDとパスワードでログイン認証を行う。
     *
     * @param userId   ログインに使用するユーザーID
     * @param password ログインに使用するパスワード
     * @return 認証に成功した場合は {@link LoginRequest} オブジェクト
     * @throws com.example.loginbackend.domain.exception.UnauthorizedException
     *                                                                         認証に失敗した場合にスローされる
     */
    LoginRequest login(String userId, String password);
}
