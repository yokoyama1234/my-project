package com.example.loginbackend;

import com.example.loginbackend.constant.SessionConstants;
import com.example.loginbackend.controller.LoginController;
import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
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
                LoginRequest request = new LoginRequest(1, FIXED_USER, FIXED_PASS, FIXED_NAME);
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
        void testLoginSuccessWithNullName() throws Exception {
                LoginRequest request = new LoginRequest(1, FIXED_USER, FIXED_PASS, null);
                when(loginService.login(FIXED_USER, FIXED_PASS)).thenReturn(request);
                when(messageSource.getMessage("login.success", null, Locale.JAPANESE))
                                .thenReturn("ログイン成功");

                mockMvc.perform(post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value(FIXED_USER));
        }

        @Test
        void testLoginFail() throws Exception {
                LoginRequest request = new LoginRequest(1, "wrong", "wrong", null);
                when(loginService.login("wrong", "wrong")).thenReturn(null);
                when(messageSource.getMessage("login.failure", null, Locale.JAPANESE))
                                .thenReturn("ユーザIDまたはパスワードが間違っています");

                mockMvc.perform(post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isUnauthorized())
                                .andExpect(jsonPath("$.message").value("ユーザIDまたはパスワードが間違っています"));
        }

        @Test
        void testLoginDataAccessException() throws Exception {
                LoginRequest request = new LoginRequest(1, FIXED_USER, FIXED_PASS, null);
                when(loginService.login(FIXED_USER, FIXED_PASS))
                                .thenThrow(mock(DataAccessException.class));
                when(messageSource.getMessage("login.db.error", null, Locale.JAPANESE))
                                .thenReturn("DBエラー");

                mockMvc.perform(post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.message").value("DBエラー"));
        }

        @Test
        void testLoginSessionError() throws Exception {
                LoginRequest request = new LoginRequest(1, FIXED_USER, FIXED_PASS, FIXED_NAME);
                when(loginService.login(FIXED_USER, FIXED_PASS)).thenReturn(request);
                when(messageSource.getMessage("session.error", null, Locale.JAPANESE))
                                .thenReturn("セッションエラー");

                MockHttpSession sessionSpy = Mockito.spy(new MockHttpSession());
                doThrow(new IllegalStateException()).when(sessionSpy)
                                .setAttribute(eq(SessionConstants.USER), any());

                mockMvc.perform(post("/api/login").session(sessionSpy)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.message").value("セッションエラー"));
        }

        @Test
        void testLoginNoSuchMessageException() throws Exception {
                LoginRequest request = new LoginRequest(1, FIXED_USER, FIXED_PASS, FIXED_NAME);
                when(loginService.login(FIXED_USER, FIXED_PASS)).thenReturn(request);
                when(messageSource.getMessage("login.success", null, Locale.JAPANESE))
                                .thenThrow(new NoSuchMessageException("login.success"));
                when(messageSource.getMessage("error.message_not_found", null, Locale.JAPANESE))
                                .thenReturn("メッセージ未定義");

                mockMvc.perform(post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.message").value("メッセージ未定義"));
        }

        @Test
        void testMeWhenLoggedIn() throws Exception {
                LoginRequest user = new LoginRequest(1, FIXED_USER, FIXED_PASS, FIXED_NAME);
                MockHttpSession session = new MockHttpSession();
                session.setAttribute(SessionConstants.USER, user);
                when(messageSource.getMessage("login.already_logged_in", null, Locale.JAPANESE))
                                .thenReturn("ログイン中");

                mockMvc.perform(get("/api/me").session(session))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.message").value("ログイン中"))
                                .andExpect(jsonPath("$.name").value(FIXED_NAME));
        }

        @Test
        void testMeWhenNotLoggedIn() throws Exception {
                when(messageSource.getMessage("login.not_logged_in", null, Locale.JAPANESE))
                                .thenReturn("未ログイン");

                mockMvc.perform(get("/api/me"))
                                .andExpect(status().isUnauthorized())
                                .andExpect(jsonPath("$.message").value("未ログイン"));
        }

        @Test
        void testLogoutSuccess() throws Exception {
                MockHttpSession session = new MockHttpSession();

                mockMvc.perform(post("/api/logout").session(session))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.message").value("logout.success"));
        }

        @Test
        void testLogoutFailure() throws Exception {
                MockHttpSession session = Mockito.spy(new MockHttpSession());
                doThrow(new IllegalStateException()).when(session).invalidate();

                mockMvc.perform(post("/api/logout").session(session))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value("logout.failure"));
        }
}
