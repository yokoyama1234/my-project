package com.example.loginbackend.controller;

import com.example.loginbackend.model.Product;
import com.example.loginbackend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/rollback")
    public String testRollback() {
        try {
            productService.updateUserAndProductWithRollbackTest();
            return "成功…のはずが例外が出るのでここは通らない";
        } catch (Exception e) {
            return "例外発生 → ロールバック成功！";
        }
    }

}
