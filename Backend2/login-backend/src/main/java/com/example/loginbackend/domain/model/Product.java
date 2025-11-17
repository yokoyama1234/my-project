package com.example.loginbackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product テーブルに対応するドメインモデル
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    /**
     * 商品を一意に識別するID（DBの主キー）
     */
    private int id;
    /**
     * 商品名
     */
    private String name;
    /**
     * 値段
     */
    private int price;
}
