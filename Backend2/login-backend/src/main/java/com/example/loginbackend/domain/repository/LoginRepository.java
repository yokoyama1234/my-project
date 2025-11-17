package com.example.loginbackend.domain.repository;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import com.example.loginbackend.domain.mapper.LoginMapper;
import com.example.loginbackend.domain.model.LoginUser;

/**
 * ログイン関連のデータアクセスを担当するリポジトリ
 * Mapperを介してDB操作を行う
 */
@Repository
@RequiredArgsConstructor
public class LoginRepository {

    // DIされたLoginMapper
    private final LoginMapper loginMapper;

    /**
     * ユーザーIDとパスワードでログインユーザーを検索
     */
    public LoginUser findByUserIdAndPassword(String userId, String password) {
        return loginMapper.findByUserIdAndPassword(userId, password);
    }

    /**
     * ユーザー名を更新
     */
    public int updateUserName(Long id, String name) {
        return loginMapper.updateUserName(id, name);
    }
}
