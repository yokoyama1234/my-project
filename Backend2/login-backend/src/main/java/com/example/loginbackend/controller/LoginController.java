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
        try {
            LoginRequest user = loginService.login(request.getUserId(), request.getPassword());
            if (user != null) {
                session.setAttribute("USER", user);
                return ResponseEntity.ok(buildLoginResponse(user, "login.success", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(buildLoginResponse(null, "login.failure", HttpStatus.UNAUTHORIZED));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildLoginResponse(null, "login.error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {
        LoginRequest user = (LoginRequest) session.getAttribute("USER");
        if (user != null) {
            return ResponseEntity.ok(buildLoginResponse(user, "login.already_logged_in", HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildLoginResponse(null, "login.not_logged_in", HttpStatus.UNAUTHORIZED));
        }
    }

    private LoginResponse buildLoginResponse(LoginRequest user, String messageKey, HttpStatus status) {
        String msg = messageSource.getMessage(messageKey, null, Locale.JAPANESE);
        String displayName = (user != null && user.getName() != null) ? user.getName()
                : (user != null ? user.getUserId() : null);
        return new LoginResponse(status.value(), msg, displayName);
    }
}
