package com.example.loginbackend.service.impl;

import com.example.loginbackend.mapper.ProductMapper;
import com.example.loginbackend.mapper.UserMapper;
import com.example.loginbackend.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.loginbackend.model.Product;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    public ProductServiceImpl(ProductMapper productMapper, UserMapper userMapper) {
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }

    @Transactional
    public void updateUserAndProductWithRollbackTest() {
        userMapper.updateUserName(1L, "管理者１");

        productMapper.updateProductName(1L, "DB設定で上限10文字に設定");

    }
}
