package com.example.loginbackend.rest.controller;

import com.example.loginbackend.domain.model.User;
import com.example.loginbackend.domain.service.LoginService;
import com.example.loginbackend.rest.model.LoginRequest;
import com.example.loginbackend.rest.model.LoginResponse;
import com.example.loginbackend.rest.model.LogoutResponse;
import com.example.loginbackend.rest.service.SessionService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MessageSource messageSource;
    private final SessionService sessionService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        log.info("ログインAPIスタート: userId={}", request.getUserId());

        User user;
        try {
            user = loginService.login(request.getUserId(), request.getPassword());
        } catch (DataAccessException e) {
            log.error("DBエラー: userId={}", request.getUserId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "login.db.error", HttpStatus.INTERNAL_SERVER_ERROR));
        }

        if (user == null) {
            log.info("ログイン失敗: userId={}", request.getUserId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildLoginResponse(null, "login.failure", HttpStatus.UNAUTHORIZED));
        }

        try {
            sessionService.setUser(session, user);
            log.info("ログイン成功: userId={}", request.getUserId());
            return ResponseEntity.ok(buildLoginResponse(user, "login.success", HttpStatus.OK));
        } catch (IllegalStateException e) {
            log.error("セッションエラー: userId={}", request.getUserId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "session.error", HttpStatus.INTERNAL_SERVER_ERROR));
        } finally {
            log.info("ログインAPI終了: userId={}", request.getUserId());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {

        User user = sessionService.getUser(session);
        log.info("ログイン確認APIスタート");
        if (user != null) {
            log.info("ログイン中");
            log.info("ログイン確認API終了");
            return ResponseEntity.ok(buildLoginResponse(user, "login.already_logged_in", HttpStatus.OK));
        } else {
            log.info("ログアウト済み");
            log.info("ログイン確認API終了");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildLoginResponse(null, "login.not_logged_in", HttpStatus.UNAUTHORIZED));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpSession session) {
        log.info("ログアウトAPIスタート");
        try {
            sessionService.invalidate(session);
            log.info("ログアウト成功");
            return ResponseEntity.ok(new LogoutResponse(HttpStatus.OK.value(), "logout.success"));
        } catch (IllegalStateException e) {
            log.error("ログアウト失敗", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LogoutResponse(HttpStatus.BAD_REQUEST.value(), "logout.failure"));
        } finally {
            log.info("ログアウトAPI終了");
        }
    }

    // domain/model/LoginUser → REST DTO/LoginResponse に変換
    private LoginResponse buildLoginResponse(User user, String messageKey, HttpStatus status) {
        String msg = messageSource.getMessage(messageKey, null, Locale.JAPANESE);
        String displayName = null;

        if (user != null) {
            displayName = (user.getName() != null) ? user.getName() : user.getUserId();
        }

        return new LoginResponse(status.value(), msg, displayName);
    }
}
