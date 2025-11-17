package com.example.loginbackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * users テーブルに対応するドメインモデル
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
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
