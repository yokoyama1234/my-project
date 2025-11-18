package com.example.loginbackend.rest.aop;

import com.example.loginbackend.domain.exception.UnauthorizedException;
import com.example.loginbackend.rest.annotation.SessionRequired;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * セッションベースの認証チェックを行う AOP クラス。
 * <p>
 * {@link SessionRequired} が付与されたメソッドの実行前にインターセプトし、
 * セッション内にユーザー情報が存在するかを検証します。
 * 未ログイン状態であれば {@link UnauthorizedException} をスローし、
 * Spring MVC により 401 Unauthorized が応答されます。
 * </p>
 *
 * <p>
 * 本 AOP はコントローラ層のメソッドに適用することを想定しており、
 * 認証済みユーザーのみがアクセスできる API の共通認証処理を実装できます。
 * </p>
 */
@Aspect
@Component
@RequiredArgsConstructor
public class SessionCheck {

    /** 現在のリクエストで使用されている HttpSession */
    private final HttpSession session;

    /**
     * 認証が必要な API 呼び出しの前にセッションをチェックする。
     *
     * <p>
     * Pointcut: {@code @annotation(sessionRequired)}
     * → メソッドに {@link SessionRequired} が付与されている場合に適用される。
     * </p>
     *
     * @param sessionRequired アノテーションインスタンス（使わないが受け取れる）
     * @throws UnauthorizedException セッションにユーザー情報が存在しない場合
     */
    @Before("@annotation(sessionRequired)")
    public void checkSession(SessionRequired sessionRequired) {

        Object user = session.getAttribute("user");

        if (user == null) {
            throw new UnauthorizedException("session.notfound");
        }
    }
}
