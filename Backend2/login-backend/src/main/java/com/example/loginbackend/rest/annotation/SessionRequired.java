package com.example.loginbackend.rest.annotation;

import java.lang.annotation.*;

/**
 * セッションに有効なログイン情報が存在することを要求するアノテーション。
 * <p>
 * このアノテーションが付与されたコントローラメソッドにアクセスした場合、
 * AOP などで事前にセッションチェックを行い、未ログイン状態であれば
 * {@code UnauthorizedException} をスローするなどの処理を実行できます。
 * </p>
 *
 * <p>
 * 主な用途:
 * <ul>
 * <li>ログイン必須 API のアクセス制御</li>
 * <li>セッションが存在しない場合の共通エラーハンドリング</li>
 * </ul>
 * </p>
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionRequired {
}
