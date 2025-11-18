package com.example.loginbackend.rest.exception;

import com.example.loginbackend.domain.exception.UnauthorizedException;
import com.example.loginbackend.rest.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * REST API 全体の例外を一括して処理するクラス。
 * 
 * - @RestControllerAdvice を付与することで、全てのコントローラに対する例外ハンドラとして動作。
 * - @Slf4j によりログ出力が簡単に可能。
 * - @RequiredArgsConstructor により final フィールドのコンストラクタが自動生成される。
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

        /** メッセージを多言語化するための MessageSource（DI される） */
        private final MessageSource messageSource;

        /**
         * 認証エラー（ログインしていない／権限がない等）を処理するハンドラ。
         * 
         * @param ex UnauthorizedException
         * @return 401 Unauthorized のレスポンス
         */
        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<LoginResponse> handleUnauthorized(UnauthorizedException ex) {

                String message = messageSource.getMessage(
                                ex.getMessage(),
                                null,
                                Locale.JAPAN);

                LoginResponse response = new LoginResponse(
                                HttpStatus.UNAUTHORIZED.value(),
                                message,
                                null);

                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(response);
        }

        /**
         * その他の予期しない RuntimeException（NPE など）を捕捉するハンドラ。
         * 
         * ※ここに来る場合は想定外のエラーのため、ログレベルは ERROR にしている。
         *
         * @param ex RuntimeException
         * @return 500 Internal Server Error のレスポンス
         */
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<LoginResponse> handleRuntime(RuntimeException ex) {

                // 予期しないエラーはログに詳細を出力
                log.error("予期しないエラー: {}", ex.getMessage(), ex);

                // 内部エラー用のメッセージを message.properties から取得
                String message = messageSource.getMessage(
                                "error.internal",
                                null,
                                Locale.JAPAN);

                // 共通レスポンス DTO に詰める
                LoginResponse response = new LoginResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                message,
                                null);

                // HTTP ステータス 500 で返却
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(response);
        }
}
