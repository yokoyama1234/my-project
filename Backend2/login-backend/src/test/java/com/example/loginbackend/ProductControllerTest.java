package com.example.loginbackend;

import com.example.loginbackend.domain.model.ProductResponse;
import com.example.loginbackend.domain.service.ProductService;
import com.example.loginbackend.domain.service.SessionService;
import com.example.loginbackend.rest.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
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

        @Test
        void testGetProductsLoggedIn() throws Exception {
                when(sessionService.isLoggedIn(any())).thenReturn(true);
                when(productService.getAllProducts()).thenReturn(
                                Arrays.asList(
                                                new ProductResponse(1, "商品A", 1000),
                                                new ProductResponse(2, "商品B", 2000)));

                mockMvc.perform(get("/api/products").locale(Locale.JAPANESE))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("商品A"))
                                .andExpect(jsonPath("$[1].price").value(2000));
        }

        @Test
        void testRollback_DataAccessException() throws Exception {
                doThrow(new DataAccessResourceFailureException("DB error"))
                                .when(productService).updateUserAndProductWithRollbackTest();

                when(messageSource.getMessage("rollback.success", null, Locale.JAPANESE))
                                .thenReturn("ロールバック成功");

                mockMvc.perform(post("/api/products/rollback").locale(Locale.JAPANESE))
                                .andExpect(status().isOk())
                                .andExpect(content().string("ロールバック成功"));
        }

        @Test
        void testRollback_IllegalStateException() throws Exception {
                doThrow(new IllegalStateException("不正状態"))
                                .when(productService).updateUserAndProductWithRollbackTest();

                when(messageSource.getMessage("rollback.success", null, Locale.JAPANESE))
                                .thenReturn("ロールバック成功");

                mockMvc.perform(post("/api/products/rollback").locale(Locale.JAPANESE))
                                .andExpect(status().isOk())
                                .andExpect(content().string("ロールバック成功"));
        }

        @Test
        void testRollback_NoException() throws Exception {
                doNothing().when(productService).updateUserAndProductWithRollbackTest();
                when(messageSource.getMessage("rollback.unexpected", null, Locale.JAPANESE))
                                .thenReturn("ロールバックされませんでした");

                mockMvc.perform(post("/api/products/rollback").locale(Locale.JAPANESE))
                                .andExpect(status().isOk())
                                .andExpect(content().string("ロールバックされませんでした"));
        }
}
