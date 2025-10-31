package com.example.loginbackend.controller;

import com.example.loginbackend.exception.UnauthorizedException;
import com.example.loginbackend.model.ProductResponse;
import com.example.loginbackend.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(HttpSession session, Locale locale) {
        checkAuthentication(session, locale);
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/rollback")
    public ResponseEntity<String> testRollback(Locale locale) {
        try {
            productService.updateUserAndProductWithRollbackTest();
            String msg = messageSource.getMessage("rollback.unexpected", null, locale);
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            String msg = messageSource.getMessage("rollback.success", null, locale);
            return ResponseEntity.ok(msg);
        }
    }

    private void checkAuthentication(HttpSession session, Locale locale) {
        if (session.getAttribute("USER") == null) {
            String msg = messageSource.getMessage("error.not_logged_in", null, locale);
            throw new UnauthorizedException(msg);
        }
    }
}
