package com.example.loginbackend.controller;

import com.example.loginbackend.model.LoginUser;
import com.example.loginbackend.dto.LoginResponse;
import com.example.loginbackend.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUser request, HttpSession session) {
        LoginUser user = loginService.login(request.getUserId(), request.getPassword());
        if (user != null) {
            session.setAttribute("USER", user);
            return ResponseEntity.ok(new LoginResponse(HttpStatus.OK.value(), "ログイン成功", user.getName()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(HttpStatus.UNAUTHORIZED.value(), "ユーザIDまたはパスワードが間違っています"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {
        LoginUser user = (LoginUser) session.getAttribute("USER");
        if (user != null) {
            return ResponseEntity.ok(new LoginResponse(HttpStatus.OK.value(), "ログイン中", user.getUserId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(HttpStatus.UNAUTHORIZED.value(), "ログインしていません"));
        }
    }
}
