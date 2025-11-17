package com.example.loginbackend.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.loginbackend.domain.model.LoginUser;

@Mapper
public interface LoginMapper {

    @Select("SELECT user_id, name, password FROM users WHERE user_id = #{userId} AND password = #{password}")
    LoginUser findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    @Update("UPDATE users SET name = #{name} WHERE id = #{id}")
    int updateUserName(@Param("id") Long id, @Param("name") String name);
}
