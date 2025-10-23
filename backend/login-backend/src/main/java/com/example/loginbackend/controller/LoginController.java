package com.example.loginbackend.controller;

import com.example.loginbackend.model.User;
import com.example.loginbackend.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoginController {

    // 固定値ユーザ
    private final String FIXED_USER = "admin";
    private final String FIXED_PASS = "password";

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User request, HttpSession session) {
        if (FIXED_USER.equals(request.getUserId()) && FIXED_PASS.equals(request.getPassword())) {
            session.setAttribute("USER", FIXED_USER);
            return ResponseEntity.ok(new LoginResponse(200, "ログイン成功", FIXED_USER));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(401, "ユーザIDまたはパスワードが間違っています"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {
        String user = (String) session.getAttribute("USER");
        if (user != null) {
            return ResponseEntity.ok(new LoginResponse(200, "ログイン中", user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(401, "ログインしていません"));
        }
    }
}
