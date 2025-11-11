package com.example.loginbackend.rest.controller;

import com.example.loginbackend.domain.exception.UnauthorizedException;
import com.example.loginbackend.domain.model.ProductResponse;
import com.example.loginbackend.domain.service.ProductService;
import com.example.loginbackend.domain.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * 商品情報に関するAPIを提供するコントローラクラス。
 * <p>
 * このクラスでは、ログイン済みユーザーのみがアクセスできる商品一覧取得APIや、
 * トランザクションのロールバックテスト用APIを提供します。
 * </p>
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    /** 商品関連のビジネスロジックを提供するサービス */
    private final ProductService productService;

    /** セッション管理サービス */
    private final SessionService sessionService;

    /** メッセージリソース（国際化対応メッセージを取得） */
    private final MessageSource messageSource;

    /**
     * 商品一覧を取得するエンドポイント。
     * <p>
     * ユーザーがログイン済みであることを前提とし、認証されていない場合は
     * 401 をスローします。
     * </p>
     *
     * @param session 現在のHTTPセッション（ログイン状態を確認）
     * @param locale  メッセージ取得に使用するロケール
     * @return 商品情報のリストを含む
     * @throws UnauthorizedException ログインしていない場合に発生
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(HttpSession session, Locale locale) {
        if (!sessionService.isLoggedIn(session)) {
            String msg = messageSource.getMessage("error.not_logged_in", null, locale);
            throw new UnauthorizedException(msg);
        }

        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * トランザクションのロールバック動作を確認するテスト用エンドポイント。
     * <p>
     * 正常実行時に例外を意図的に発生させ、ロールバックが行われることを確認します。
     * </p>
     *
     * @param locale メッセージ取得に使用するロケール
     * @return ロールバック結果のメッセージを含む {@link ResponseEntity}
     */
    @PostMapping("/rollback")
    public ResponseEntity<String> testRollback(Locale locale) {
        try {
            productService.updateUserAndProductWithRollbackTest();
        } catch (DataAccessException | IllegalStateException e) {
            String msg = messageSource.getMessage("rollback.success", null, locale);
            return ResponseEntity.ok(msg);
        }
        String msg = messageSource.getMessage("rollback.unexpected", null, locale);
        return ResponseEntity.ok(msg);
    }
}
