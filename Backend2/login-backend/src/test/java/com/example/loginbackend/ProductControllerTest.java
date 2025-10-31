package com.example.loginbackend;

import com.example.loginbackend.controller.ProductController;
import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.model.ProductResponse;
import com.example.loginbackend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(ProductController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductService productService;

        @MockBean
        private MessageSource messageSource;

        @Test
        void testGetProductsLoggedIn() throws Exception {
                MockHttpSession session = new MockHttpSession();
                LoginRequest user = new LoginRequest();
                user.setUserId("dummyUser");
                user.setName("テストユーザー");
                session.setAttribute("USER", user);

                when(productService.getAllProducts()).thenReturn(
                                Arrays.asList(
                                                new ProductResponse(1, "商品A", 1000),
                                                new ProductResponse(2, "商品B", 2000)));

                mockMvc.perform(get("/api/products").session(session).locale(Locale.JAPANESE))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("商品A"))
                                .andExpect(jsonPath("$[1].price").value(2000));
        }

        @Test
        void testGetProductsNotLoggedIn() throws Exception {
                when(messageSource.getMessage("error.not_logged_in", null, Locale.JAPANESE))
                                .thenReturn("ログインしていません");

                mockMvc.perform(get("/api/products").locale(Locale.JAPANESE))
                                .andExpect(status().isUnauthorized())
                                .andExpect(result -> {
                                        Exception resolved = result.getResolvedException();
                                        assert resolved != null;
                                        assertEquals("ログインしていません", resolved.getMessage());
                                });
        }

        @Test
        void testRollbackSuccess() throws Exception {
                doThrow(new RuntimeException()).when(productService).updateUserAndProductWithRollbackTest();
                when(messageSource.getMessage("rollback.success", null, Locale.JAPANESE))
                                .thenReturn("ロールバック成功");

                mockMvc.perform(post("/api/products/rollback").locale(Locale.JAPANESE))
                                .andExpect(status().isOk())
                                .andExpect(content().string("ロールバック成功"));
        }

        @Test
        void testRollbackUnexpected() throws Exception {
                doNothing().when(productService).updateUserAndProductWithRollbackTest();
                when(messageSource.getMessage("rollback.unexpected", null, Locale.JAPANESE))
                                .thenReturn("ロールバックされませんでした");

                mockMvc.perform(post("/api/products/rollback").locale(Locale.JAPANESE))
                                .andExpect(status().isOk())
                                .andExpect(content().string("ロールバックされませんでした"));
        }
}
