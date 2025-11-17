package com.example.loginbackend.domain.repository;

import com.example.loginbackend.domain.mapper.ProductMapper;
import com.example.loginbackend.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ProductMapper productMapper;

    /**
     * 全商品の一覧を取得
     */
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    /**
     * 商品名を更新
     */
    public int updateProductName(Long id, String name) {
        return productMapper.updateProductName(id, name);
    }
}
