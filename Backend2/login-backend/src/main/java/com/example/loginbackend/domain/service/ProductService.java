package com.example.loginbackend.domain.service;

import java.util.List;

import com.example.loginbackend.domain.model.ProductResponse;

/**
 * 商品情報に関するビジネスロジックを提供するサービスインターフェース。
 * <p>
 * 実装クラスでは、商品一覧の取得やトランザクションを伴う更新処理などを提供します。
 * </p>
 *
 * @see com.example.loginbackend.domain.service.impl.ProductServiceImpl
 */
public interface ProductService {

    /**
     * 全商品の一覧を取得する。
     *
     * @return {@link ProductResponse} のリスト
     */
    List<ProductResponse> getAllProducts();

    /**
     * ユーザー名と商品名を更新するテスト用メソッド。
     * <p>
     * 実装クラスではトランザクションが有効で、どちらかの更新で例外が発生した場合は
     * 自動的にロールバックされることを想定しています。
     * </p>
     *
     * <p>
     * 例：商品名がDB制約（文字数上限）に違反すると例外が発生し、両方の更新が取り消されます。
     * </p>
     */
    void updateUserAndProductWithRollbackTest();
}
