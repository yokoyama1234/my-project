package com.example.loginbackend.domain.serviceimpl;

import com.example.loginbackend.domain.model.Product;
import com.example.loginbackend.domain.repository.ProductRepository;
import com.example.loginbackend.domain.repository.LoginRepository;
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

    /** 商品情報を操作するリポジトリ */
    private final ProductRepository productRepository;

    /** テスト用に使用する商品ID（DB上に存在することを前提） */
    private static final long TEST_PRODUCT_ID = 1L;

    /** ユーザー情報を操作するリポジトリ */
    private final LoginRepository loginRepository;

    /** テスト用に使用するユーザーID（DB上に存在することを前提） */
    private static final long TEST_USER_ID = 1L;

    /**
     * 全商品の一覧を取得する。
     *
     * @return {@link Product} のリスト
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new Product(product.getId(), product.getName(), product.getPrice()))
                .toList();
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
        loginRepository.updateUserName(TEST_USER_ID, "db.username");
        productRepository.updateProductName(TEST_PRODUCT_ID, "db.productname");
    }
}
