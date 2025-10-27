package com.example.loginbackend.controller;

import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class LoginController {

    private static final String FIXED_USER = "admin";
    private static final String FIXED_PASS = "password";
    private static final String SESSION_USER_KEY = "USER";

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        if (FIXED_USER.equals(request.getUserId()) && FIXED_PASS.equals(request.getPassword())) {
            session.setAttribute(SESSION_USER_KEY, FIXED_USER);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new LoginResponse(HttpStatus.OK.value(), "ログイン成功", FIXED_USER));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(HttpStatus.UNAUTHORIZED.value(), "ユーザIDまたはパスワードが間違っています"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {
        String user = (String) session.getAttribute(SESSION_USER_KEY);
        if (user != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new LoginResponse(HttpStatus.OK.value(), "ログイン中", user));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(HttpStatus.UNAUTHORIZED.value(), "ログインしていません"));
        }
    }
}
