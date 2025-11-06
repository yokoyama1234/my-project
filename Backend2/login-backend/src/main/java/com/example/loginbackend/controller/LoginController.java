package com.example.loginbackend.controller;

import com.example.loginbackend.constant.SessionConstants;
import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.model.LoginResponse;
import com.example.loginbackend.model.LogoutResponse;
import com.example.loginbackend.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataAccessException;

import java.util.Locale;

/**
 * ログイン機能を提供するコントローラクラス。
 * <p>
 * ユーザーのログイン、ログアウト、ログイン状態確認を行うAPIエンドポイントを提供します。
 * </p>
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    /** ログイン関連の業務ロジックを提供するサービス */
    private final LoginService loginService;

    /** メッセージプロパティからメッセージを取得するためのMessageSource */
    private final MessageSource messageSource;

    /**
     * ユーザーのログイン処理を行う。
     * <p>
     * ユーザーIDとパスワードを検証し、認証に成功した場合はセッションにユーザー情報を保持します。
     * </p>
     *
     * @param request ログインリクエスト（ユーザーID、パスワードを含む）
     * @param session 現在のHTTPセッション
     * @return ログイン結果を表す
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {

        LoginRequest user;
        try {
            user = loginService.login(request.getUserId(), request.getPassword());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "login.db.error", HttpStatus.INTERNAL_SERVER_ERROR));
        }

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildLoginResponse(null, "login.failure", HttpStatus.UNAUTHORIZED));
        }

        try {
            session.setAttribute(SessionConstants.USER, user);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "session.error", HttpStatus.INTERNAL_SERVER_ERROR));
        }

        try {
            return ResponseEntity.ok(buildLoginResponse(user, "login.success", HttpStatus.OK));
        } catch (NoSuchMessageException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "error.message_not_found", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 現在ログイン中のユーザー情報を取得する。
     *
     * @param session 現在のHTTPセッション
     * @return ログイン中のユーザー情報を含むレスポンス。
     *         ログイン済みであればユーザー情報を返し、未ログインであれば401を返す。
     */
    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {
        LoginRequest user = (LoginRequest) session.getAttribute(SessionConstants.USER);
        if (user != null) {
            return ResponseEntity.ok(buildLoginResponse(user, "login.already_logged_in", HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildLoginResponse(null, "login.not_logged_in", HttpStatus.UNAUTHORIZED));
        }
    }

    /**
     * ログアウト処理を行うエンドポイント。
     * <p>
     * 現在のセッションを破棄（invalidate）し、ユーザーをログアウトさせます。<br>
     * すでにセッションが無効な場合（例：二重ログアウトなど）は400を返します。
     * </p>
     * 
     * @param session 現在のHTTPセッション
     * @return {@link ResponseEntity} 型のレスポンス
     */
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpSession session) {
        try {
            session.invalidate();
            return ResponseEntity.ok(new LogoutResponse(HttpStatus.OK.value(), "logout.success"));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LogoutResponse(HttpStatus.BAD_REQUEST.value(), "logout.failure"));
        }
    }

    /**
     * レスポンスを構築する内部ヘルパーメソッド。
     *
     * @param user       ログインユーザー情報（存在しない場合はnull）
     * @param messageKey メッセージキー（messages.propertiesから取得）
     * @param status     HTTPステータス
     * @return {@link LoginResponse} オブジェクト
     */
    private LoginResponse buildLoginResponse(LoginRequest user, String messageKey, HttpStatus status) {
        String msg = messageSource.getMessage(messageKey, null, Locale.JAPANESE);
        String displayName = null;

        if (user != null) {
            if (user.getName() != null) {
                displayName = user.getName();
            } else {
                displayName = user.getUserId();
            }
        }

        return new LoginResponse(status.value(), msg, displayName);
    }
}
