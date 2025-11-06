package com.example.loginbackend.constant;

/**
 * セッションで使用するキー名を管理する定数クラス。
 * <p>
 * セッション関連の定数を一元管理することで、
 * コントローラーやサービスでのハードコーディングを防ぎ、
 * 保守性を高める目的で使用します。
 * </p>
 */
public final class SessionConstants {

    /** ログインユーザー情報を格納するキー */
    public static final String USER = "USER";

    // インスタンス化を防止
    private SessionConstants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
}
