package com.example.loginbackend.mapper;

import com.example.loginbackend.model.LoginRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LoginMapper {

    @Select("SELECT user_id, name, password FROM users WHERE user_id = #{userId} AND password = #{password}")
    LoginRequest findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    @Update("UPDATE users SET name = #{name} WHERE id = #{id}")
    int updateUserName(@Param("id") Long id, @Param("name") String name);
}
