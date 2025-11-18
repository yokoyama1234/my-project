package com.example.loginbackend.rest.service;

import com.example.loginbackend.domain.model.LoginUser;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 * セッション操作を共通化するサービスクラス。
 * <p>
 * ユーザー情報の保存・取得・削除などを一元管理します。
 * </p>
 */
@Service
public class SessionService {

    /** ログインユーザー情報を格納するキー */
    public static final String USER = "USER";

    /**
     * セッションにユーザー情報を保存する。
     *
     * @param session 現在のHTTPセッション
     * @param user    ログインしたユーザー情報
     */
    public void setUser(HttpSession session, LoginUser user) {
        session.setAttribute(USER, user);
    }

    /**
     * セッションからユーザー情報を取得する。
     *
     * @param session 現在のHTTPセッション
     * @return ログイン中のユーザー情報、または null
     */
    public LoginUser getUser(HttpSession session) {
        Object userObj = session.getAttribute(USER);
        return (userObj instanceof LoginUser) ? (LoginUser) userObj : null;
    }

    /**
     * セッションを無効化する（ログアウト時に使用）。
     *
     * @param session 現在のHTTPセッション
     */
    public void invalidate(HttpSession session) {
        session.invalidate();
    }

    /**
     * ユーザーがログイン中かどうかを判定する。
     *
     * @param session 現在のHTTPセッション
     * @return ログイン中なら true、未ログインなら false
     */
    public boolean isLoggedIn(HttpSession session) {
        return getUser(session) != null;
    }
}
