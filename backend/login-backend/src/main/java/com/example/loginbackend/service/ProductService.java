package com.example.loginbackend.service;

import com.example.loginbackend.mapper.ProductMapper;
import com.example.loginbackend.model.Product;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }
}
