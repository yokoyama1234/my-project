package com.example.loginbackend;

import com.example.loginbackend.controller.LoginController;
import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private LoginService loginService;

        @MockBean
        private MessageSource messageSource;

        private final String FIXED_USER = "admin";
        private final String FIXED_PASS = "password";
        private final String FIXED_NAME = "山田太郎";

        @Test
        void testLoginSuccess() throws Exception {
                LoginRequest request = new LoginRequest();
                request.setUserId(FIXED_USER);
                request.setPassword(FIXED_PASS);
                request.setName(FIXED_NAME);

                when(loginService.login(FIXED_USER, FIXED_PASS)).thenReturn(request);
                when(messageSource.getMessage("login.success", null, Locale.JAPANESE))
                                .thenReturn("ログイン成功");

                mockMvc.perform(post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                                .andExpect(jsonPath("$.message").value("ログイン成功"))
                                .andExpect(jsonPath("$.name").value(FIXED_NAME));
        }

        @Test
        void testLoginFail() throws Exception {
                LoginRequest request = new LoginRequest();
                request.setUserId("wrong");
                request.setPassword("wrong");

                when(loginService.login("wrong", "wrong")).thenReturn(null);
                when(messageSource.getMessage("login.failure", null, Locale.JAPANESE))
                                .thenReturn("ユーザIDまたはパスワードが間違っています");

                mockMvc.perform(post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isUnauthorized())
                                .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()))
                                .andExpect(jsonPath("$.message").value("ユーザIDまたはパスワードが間違っています"))
                                .andExpect(jsonPath("$.name").doesNotExist());
        }

        @Test
        void testMeWhenLoggedIn() throws Exception {
                LoginRequest user = new LoginRequest();
                user.setUserId(FIXED_USER);
                user.setName(FIXED_NAME);

                MockHttpSession session = new MockHttpSession();
                session.setAttribute("USER", user);

                when(messageSource.getMessage("login.already_logged_in", null, Locale.JAPANESE))
                                .thenReturn("ログイン中");

                mockMvc.perform(get("/api/me").session(session))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                                .andExpect(jsonPath("$.message").value("ログイン中"))
                                .andExpect(jsonPath("$.name").value(FIXED_NAME));
        }

        @Test
        void testMeWhenNotLoggedIn() throws Exception {
                when(messageSource.getMessage("login.not_logged_in", null, Locale.JAPANESE))
                                .thenReturn("ログインしていません");

                mockMvc.perform(get("/api/me"))
                                .andExpect(status().isUnauthorized())
                                .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()))
                                .andExpect(jsonPath("$.message").value("ログインしていません"))
                                .andExpect(jsonPath("$.name").doesNotExist());
        }
}
