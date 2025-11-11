package com.example.loginbackend.rest.controller;

import com.example.loginbackend.domain.model.LoginRequest;
import com.example.loginbackend.domain.model.LoginResponse;
import com.example.loginbackend.domain.model.LogoutResponse;
import com.example.loginbackend.domain.service.LoginService;
import com.example.loginbackend.domain.service.SessionService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * ログイン・ログアウトAPIを提供するRESTコントローラ。
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MessageSource messageSource;
    private final SessionService sessionService;

    /**
     * ログイン処理。
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
            sessionService.setUser(session, user);
            return ResponseEntity.ok(buildLoginResponse(user, "login.success", HttpStatus.OK));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "session.error", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (NoSuchMessageException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "error.message_not_found", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * ログイン中のユーザー情報を取得。
     */
    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {
        LoginRequest user = sessionService.getUser(session);

        if (user != null) {
            return ResponseEntity.ok(buildLoginResponse(user, "login.already_logged_in", HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildLoginResponse(null, "login.not_logged_in", HttpStatus.UNAUTHORIZED));
        }
    }

    /**
     * ログアウト処理。
     */
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpSession session) {
        try {
            sessionService.invalidate(session);
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
