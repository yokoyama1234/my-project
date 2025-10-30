package com.example.loginbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private int status;
    private String message;
    private String name;

    public LoginResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
