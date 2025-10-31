package com.example.loginbackend.mapper;

import com.example.loginbackend.model.LoginRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * ユーザー情報にアクセスするMyBatisマッパーインターフェース。
 * <p>
 * ログイン認証やユーザー情報の更新を行います。
 * </p>
 */
@Mapper
public interface LoginMapper {

    /**
     * 指定されたユーザーIDとパスワードに一致するユーザー情報を取得する。
     *
     * @param userId   ユーザーID
     * @param password パスワード
     * @return ユーザーが存在する場合は {@link LoginRequest}、存在しない場合は {@code null}
     */
    @Select("SELECT user_id, name, password FROM users WHERE user_id = #{userId} AND password = #{password}")
    LoginRequest findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    /**
     * 指定されたユーザーIDの名前を更新する。
     *
     * @param id   ユーザーのID
     * @param name 新しい名前
     * @return 更新された行数（通常は1）
     */
    @Update("UPDATE users SET name = #{name} WHERE id = #{id}")
    int updateUserName(@Param("id") Long id, @Param("name") String name);
}
