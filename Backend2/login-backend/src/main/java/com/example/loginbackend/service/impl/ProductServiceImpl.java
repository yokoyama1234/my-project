package com.example.loginbackend.service.impl;

import com.example.loginbackend.mapper.ProductMapper;
import com.example.loginbackend.model.Product;
import com.example.loginbackend.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }
}
