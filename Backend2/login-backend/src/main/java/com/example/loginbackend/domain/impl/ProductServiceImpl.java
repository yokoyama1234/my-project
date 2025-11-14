package com.example.loginbackend.domain.impl;

import com.example.loginbackend.domain.mapper.LoginMapper;
import com.example.loginbackend.domain.mapper.ProductMapper;
import com.example.loginbackend.domain.model.ProductResponse;
import com.example.loginbackend.domain.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品情報に関するビジネスロジックを提供するサービス実装クラス。
 * <p>
 * {@link ProductService} インターフェースを実装し、商品情報の取得や
 * トランザクションを伴う更新処理を提供します。
 * </p>
 *
 * @see ProductService
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    /** 商品情報を操作するマッパー */
    private final ProductMapper productMapper;

    /** ユーザー情報を操作するマッパー（テスト用の更新処理に使用） */
    private final LoginMapper userMapper;
    private static final long TEST_USER_ID = 1L;
    private static final long TEST_PRODUCT_ID = 1L;

    /**
     * 全商品の一覧を取得する。
     *
     * @return {@link ProductResponse} のリスト
     */
    @Override
    public List<ProductResponse> getAllProducts() {
        return productMapper.findAll();
    }

    /**
     * ユーザー名と商品名を更新するテスト用メソッド。
     * <p>
     * トランザクションが有効で、どちらかの更新で例外が発生した場合は
     * 自動的にロールバックされます。
     * </p>
     * <p>
     * 例：DB制約（商品名上限10文字）に違反すると例外が発生し、両方の更新が取り消されます。
     * </p>
     *
     * @throws RuntimeException 更新時に例外が発生した場合、トランザクションはロールバックされます
     */
    @Transactional
    public void updateUserAndProductWithRollbackTest() {
        userMapper.updateUserName(TEST_USER_ID, "db.username");
        productMapper.updateProductName(TEST_PRODUCT_ID, "db.productname");
    }
}
