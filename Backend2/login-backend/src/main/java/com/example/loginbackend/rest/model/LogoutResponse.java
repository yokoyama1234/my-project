package com.example.loginbackend.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ログアウト処理のレスポンスを表すデータモデルクラス
 * <p>
 * ログアウトAPIの結果をクライアントに返すために使用されます。
 * ステータスコードとメッセージを保持し、処理結果を簡潔に表現します。
 * </p>
 *
 * <p>
 * 主に以下の情報を含みます：
 * </p>
 * <ul>
 * <li>HTTPステータスコード</li>
 * <li>処理結果メッセージ</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutResponse {

    /**
     * HTTPステータスコード
     */
    private int status;

    /**
     * ログアウト処理の結果を示すメッセージ
     */
    private String message;
}
