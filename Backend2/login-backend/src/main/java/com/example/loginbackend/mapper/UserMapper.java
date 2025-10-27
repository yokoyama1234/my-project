package com.example.loginbackend.mapper;

import com.example.loginbackend.model.LoginRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT user_id, name, password FROM users WHERE user_id = #{userId} AND password = #{password}")
    LoginRequest findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

}
