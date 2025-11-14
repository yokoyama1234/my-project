package com.example.loginbackend.rest.exception;

import com.example.loginbackend.domain.exception.UnauthorizedException;
import com.example.loginbackend.domain.model.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        private final MessageSource messageSource;

        public GlobalExceptionHandler(MessageSource messageSource) {
                this.messageSource = messageSource;
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<LoginResponse> handleUnauthorized(UnauthorizedException ex) {

                // ← messageSource で翻訳する
                String message = messageSource.getMessage(
                                ex.getMessage(), // error.message_not_found
                                null,
                                Locale.JAPAN);

                LoginResponse response = new LoginResponse(
                                HttpStatus.UNAUTHORIZED.value(),
                                message, // ← 翻訳済みの文言を返す
                                null);

                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(response);
        }

        /**
         * その他の実行時例外
         */
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<LoginResponse> handleRuntime(RuntimeException ex) {
                log.error("予期しないエラー: {}", ex.getMessage(), ex);

                String message = messageSource.getMessage(
                                "error.internal",
                                null,
                                Locale.JAPAN);

                LoginResponse response = new LoginResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                message,
                                null);

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(response);
        }
}
