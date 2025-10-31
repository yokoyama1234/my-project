package com.example.loginbackend.controller;

import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.model.LoginResponse;
import com.example.loginbackend.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;
import java.util.HashMap;
import java.util.Map;
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
        try {
            LoginRequest user = loginService.login(request.getUserId(), request.getPassword());
            if (user != null) {
                session.setAttribute("USER", user);
                return ResponseEntity.ok(buildLoginResponse(user, "login.success", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(buildLoginResponse(null, "login.failure", HttpStatus.UNAUTHORIZED));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "login.error", HttpStatus.INTERNAL_SERVER_ERROR));
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
        LoginRequest user = (LoginRequest) session.getAttribute("USER");
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
     * 現在のセッションを破棄し、ユーザーをログアウト状態にします。
     * すでにセッションが無効な場合（例：二重ログアウトなど）は、
     * IllegalStateExceptionをキャッチして適切なメッセージを返します。
     * </p>
     *
     * @param session 現在のHTTPセッション
     * @return ステータスコードとメッセージを含むレスポンス
     *         <ul>
     *         <li>成功時: 200, メッセージ「ログアウトしました」</li>
     *         <li>すでにログアウト済み: 400, メッセージ「すでにログアウト済みです」</li>
     *         </ul>
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            session.invalidate();
            response.put("status", HttpStatus.OK);
            response.put("message", "logout.success");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", "logout.failure");
            return ResponseEntity.badRequest().body(response);
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
        String displayName = (user != null && user.getName() != null) ? user.getName()
                : (user != null ? user.getUserId() : null);
        return new LoginResponse(status.value(), msg, displayName);
    }
}
