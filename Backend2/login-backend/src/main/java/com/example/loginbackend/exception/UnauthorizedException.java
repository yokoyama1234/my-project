package com.example.loginbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 認証されていないアクセスが行われた場合にスローされる例外クラス。
 * <p>
 * この例外がスローされると、Spring MVC により自動的に
 * {@code 401 Unauthorized} レスポンスが返却されます。
 * </p>
 *
 * <p>
 * 主な使用例:
 * </p>
 * <ul>
 * <li>ログインしていないユーザーが認証が必要なAPIにアクセスした場合</li>
 * <li>セッションが無効化されている状態でのリソースアクセス</li>
 * </ul>
 *
 * @see org.springframework.web.bind.annotation.ResponseStatus
 * @see HttpStatus#UNAUTHORIZED
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    /**
     * 指定したメッセージを使用して新しい {@code UnauthorizedException} を生成します。
     *
     * @param message エラーメッセージ（例：「ログインが必要です」など）
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
