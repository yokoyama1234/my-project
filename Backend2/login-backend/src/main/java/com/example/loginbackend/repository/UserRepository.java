package com.example.loginbackend.repository;

import com.example.loginbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
