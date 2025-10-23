package com.example.loginbackend.repository;

import com.example.loginbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // 必要に応じて検索メソッドを定義
    // Optional<User> findByUserIdAndPassword(String userId, String password);
}
