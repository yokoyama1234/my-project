package com.example.loginbackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品情報のレスポンスを表すデータモデルクラス。
 * <p>
 * APIで商品一覧や商品情報を返す際に使用される
 * </p>
 *
 * <p>
 * 主に以下の情報を保持します：
 * </p>
 * <ul>
 * <li>商品ID</li>
 * <li>商品名</li>
 * <li>価格</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    /**
     * 商品を一意に識別するID
     */
    private long id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品価格
     */
    private int price;
}
