package com.example.loginbackend.domain.exception;

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
public class UnauthorizedException extends RuntimeException {

    /**
     * 指定したメッセージを使用して新しい {@code UnauthorizedException} を生成します。
     *
     * @param message
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
