package com.example.loginbackend.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.loginbackend.domain.model.LoginUser;

/**
 * ログイン関連のDB操作を行うMapperインターフェース
 * MyBatisを使用してSQLをマッピング
 */
@Mapper
public interface LoginMapper {

    /**
     * ユーザーIDとパスワードでユーザー情報を取得
     * 
     * @param userId   検索対象のユーザーID
     * @param password 検索対象のパスワード
     * @return 該当するLoginUserオブジェクト（存在しない場合はnull）
     */
    @Select("SELECT user_id, name, password FROM users WHERE user_id = #{userId} AND password = #{password}")
    LoginUser findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    /**
     * 指定したユーザーIDの名前を更新
     * 
     * @param id   更新対象のユーザーID
     * @param name 更新する名前
     * @return 更新件数（0の場合は対象なし）
     */
    @Update("UPDATE users SET name = #{name} WHERE id = #{id}")
    int updateUserName(@Param("id") Long id, @Param("name") String name);
}
