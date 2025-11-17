package com.example.loginbackend;

import com.example.loginbackend.domain.exception.UnauthorizedException;
import com.example.loginbackend.domain.model.Product;
import com.example.loginbackend.domain.service.ProductService;
import com.example.loginbackend.rest.controller.ProductController;
import com.example.loginbackend.rest.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * {@link ProductController} の単体テストクラス（完全版）。
 */
@WebMvcTest(controllers = { ProductController.class,
        ProductControllerTest.GlobalExceptionHandler.class })
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private MessageSource messageSource;

    // --------------------------------------------
    // 商品取得 API
    // --------------------------------------------
    @Test
    void getProducts_LoggedIn_ReturnsProductList() throws Exception {
        when(sessionService.isLoggedIn(any())).thenReturn(true);

        List<Product> products = Arrays.asList(
                new Product(1, "商品A", 1000),
                new Product(2, "商品B", 2000));
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products").locale(Locale.JAPANESE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("商品A"))
                .andExpect(jsonPath("$[0].price").value(1000))
                .andExpect(jsonPath("$[1].name").value("商品B"))
                .andExpect(jsonPath("$[1].price").value(2000));
    }

    // --------------------------------------------
    // ロールバック API
    // --------------------------------------------
    @Test
    void rollback_WhenDataAccessException_ReturnsSuccessMessage() throws Exception {
        doThrow(new DataAccessResourceFailureException("DB error"))
                .when(productService).updateUserAndProductWithRollbackTest();

        when(messageSource.getMessage("rollback.success", null, Locale.JAPANESE))
                .thenReturn("ロールバック成功");

        mockMvc.perform(post("/api/products/rollback").locale(Locale.JAPANESE))
                .andExpect(status().isOk())
                .andExpect(content().string("ロールバック成功"));
    }

    @Test
    void rollback_WhenIllegalStateException_ReturnsSuccessMessage() throws Exception {
        doThrow(new IllegalStateException("不正状態"))
                .when(productService).updateUserAndProductWithRollbackTest();

        when(messageSource.getMessage("rollback.success", null, Locale.JAPANESE))
                .thenReturn("ロールバック成功");

        mockMvc.perform(post("/api/products/rollback").locale(Locale.JAPANESE))
                .andExpect(status().isOk())
                .andExpect(content().string("ロールバック成功"));
    }

    @Test
    void rollback_NoException_ReturnsUnexpectedMessage() throws Exception {
        doNothing().when(productService).updateUserAndProductWithRollbackTest();

        when(messageSource.getMessage("rollback.unexpected", null, Locale.JAPANESE))
                .thenReturn("ロールバックされませんでした");

        mockMvc.perform(post("/api/products/rollback").locale(Locale.JAPANESE))
                .andExpect(status().isOk())
                .andExpect(content().string("ロールバックされませんでした"));
    }

    // --------------------------------------------
    // UnauthorizedException を 401 にマッピングする例外ハンドラ
    // --------------------------------------------
    @RestControllerAdvice
    static class GlobalExceptionHandler {
        @ExceptionHandler(UnauthorizedException.class)
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public String handleUnauthorized(UnauthorizedException e) {
            return e.getMessage();
        }
    }
}
