package com.example.loginbackend.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import com.example.loginbackend.domain.model.LoginUser;

/**
 * ログイン関連のデータアクセスを担当するリポジトリ
 * 直接 MyBatis アノテーションを使用し、Mapperを分離しない構成
 */
@Mapper
@Repository
public interface LoginRepository {

        /**
         * ユーザーIDとパスワードでユーザー情報を取得
         */
        @Select("SELECT user_id, name, password FROM users WHERE user_id = #{userId} AND password = #{password}")
        LoginUser findByUserIdAndPassword(@Param("userId") String userId,
                        @Param("password") String password);

        /**
         * 指定したユーザーIDの名前を更新
         */
        @Update("UPDATE users SET name = #{name} WHERE id = #{id}")
        int updateUserName(@Param("id") Long id, @Param("name") String name);
}
