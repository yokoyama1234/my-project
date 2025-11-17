package com.example.loginbackend.rest.aop;

import com.example.loginbackend.domain.exception.UnauthorizedException;
import com.example.loginbackend.rest.annotation.SessionRequired;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SessionCheck {

    private final HttpSession session;

    /**
     * 認証が必要なAPI呼び出しの前にセッションをチェックする。
     */
    @Before("@annotation(sessionRequired)")
    public void checkSession(SessionRequired sessionRequired) {
        Object user = session.getAttribute("user");

        if (user == null) {
            throw new UnauthorizedException("session.notfound");
        }
    }
}
