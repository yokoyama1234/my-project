package com.example.loginbackend.rest.aop;

import com.example.loginbackend.domain.exception.UnauthorizedException;
import com.example.loginbackend.domain.model.LoginResponse;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * アプリ全体で発生する例外を横断的にハンドリングするAOPクラス。
 * <p>
 * コントローラやサービス層で発生した例外を
 * 一括でログ出力・レスポンス整形します。
 * </p>
 */
@Aspect
@Component
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * UnauthorizedException がスローされたときの共通処理。
     */
    @AfterThrowing(pointcut = "execution(* com.example.loginbackend..*(..))", throwing = "ex")
    public ResponseEntity<LoginResponse> handleUnauthorizedException(UnauthorizedException ex) {
        log.warn("認証エラー: {}", ex.getMessage());
        LoginResponse response = new LoginResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * その他の例外（RuntimeExceptionなど）を共通ハンドリング。
     */
    @AfterThrowing(pointcut = "execution(* com.example.loginbackend..*(..))", throwing = "ex")
    public ResponseEntity<LoginResponse> handleGenericException(RuntimeException ex) {
        log.error("予期しないエラー: {}", ex.getMessage(), ex);
        String message = messageSource.getMessage("error.internal", null, Locale.JAPAN);
        LoginResponse response = new LoginResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message,
                null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
