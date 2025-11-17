package com.example.loginbackend.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * ログインリクエストを表すデータモデルクラス。
 * <p>
 * ユーザー認証やログイン処理で使用される
 * </p>
 *
 * <p>
 * 主に以下の情報を保持します：
 * </p>
 * <ul>
 * <li>ユーザーID</li>
 * <li>パスワード</li>
 * <li>ユーザー名</li>
 * <li>データベースのID（オプション）</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * ユーザーを一意に識別するID（DBの主キー）
     */
    private Integer id;

    /**
     * ログインに使用するユーザーID
     */
    private String userId;

    /**
     * ログインに使用するパスワード
     */
    private String password;

    /**
     * ユーザーの表示名
     */
    private String name;
}
