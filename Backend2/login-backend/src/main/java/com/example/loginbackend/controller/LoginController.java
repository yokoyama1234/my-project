package com.example.loginbackend.controller;

import com.example.loginbackend.model.LoginRequest;
import com.example.loginbackend.model.LoginResponse;
import com.example.loginbackend.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;
import java.util.Locale;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MessageSource messageSource;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        LoginRequest user = loginService.login(request.getUserId(), request.getPassword());
        if (user != null) {
            session.setAttribute("USER", user);
            String msg = messageSource.getMessage("login.success", null, Locale.JAPANESE);
            return ResponseEntity.ok(new LoginResponse(HttpStatus.OK.value(), msg, user.getName()));
        } else {
            String msg = messageSource.getMessage("login.failure", null, Locale.JAPANESE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(HttpStatus.UNAUTHORIZED.value(), msg));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {
        LoginRequest user = (LoginRequest) session.getAttribute("USER");
        if (user != null) {
            String msg = messageSource.getMessage("login.already_logged_in", null, Locale.JAPANESE);
            return ResponseEntity.ok(new LoginResponse(HttpStatus.OK.value(), msg, user.getName()));
        } else {
            String msg = messageSource.getMessage("login.not_logged_in", null, Locale.JAPANESE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(HttpStatus.UNAUTHORIZED.value(), msg));
        }
    }
}
