package com.example.loginbackend.model;

public class LoginResponse {
    private int status;
    private String message;
    private String userId;

    public LoginResponse(int status, String message, String userId) {
        this.status = status;
        this.message = message;
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
