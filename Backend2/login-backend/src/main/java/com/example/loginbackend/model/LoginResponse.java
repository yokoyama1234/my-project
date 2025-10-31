package com.example.loginbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ログイン処理のレスポンスを表すデータモデルクラス。
 * <p>
 * APIのログイン、ログアウト、認証チェックなどのレスポンスとして使用されます。
 * </p>
 *
 * <p>
 * 主に以下の情報を保持します：
 * </p>
 * <ul>
 * <li>HTTPステータスコード</li>
 * <li>処理結果メッセージ</li>
 * <li>ユーザー名</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    /**
     * HTTPステータスコード
     */
    private int status;

    /**
     * 処理結果メッセージ
     */
    private String message;

    /**
     * ユーザー名
     */
    private String name;

    /**
     * ユーザー名が不要な場合に使用するコンストラクタ。
     *
     * @param status  HTTPステータスコード
     * @param message 処理結果メッセージ
     */
    public LoginResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
