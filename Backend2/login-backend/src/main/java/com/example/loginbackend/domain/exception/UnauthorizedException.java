package com.example.loginbackend.domain.exception;

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
@ResponseStatus(HttpStatus.UNAUTHORIZED) // この例外が投げられたとき、レスポンスとして 401 を返すよう指定
public class UnauthorizedException extends RuntimeException {

    /**
     * メッセージを指定して {@code UnauthorizedException} を生成します。
     * <p>
     * 例外メッセージは API レスポンスの "message" フィールドとして返却される可能性があるため、
     * クライアント側に伝わる文言として適切な内容にする必要があります。
     * </p>
     *
     * @param message クライアントに返したい例外メッセージ
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
