package com.example.loginbackend.impl;

import com.example.loginbackend.mapper.ProductMapper;
import com.example.loginbackend.mapper.LoginMapper;
import com.example.loginbackend.service.ProductService;
import com.example.loginbackend.model.ProductResponse;
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
        userMapper.updateUserName(1L, "管理者１");
        productMapper.updateProductName(1L, "DB設定で上限10文字に設定");
    }
}
