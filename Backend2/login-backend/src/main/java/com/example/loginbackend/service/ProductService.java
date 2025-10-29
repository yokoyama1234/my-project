package com.example.loginbackend.service;

import com.example.loginbackend.model.ProductResponse;
import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    void updateUserAndProductWithRollbackTest();
}