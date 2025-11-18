package com.example.loginbackend.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.example.loginbackend.domain.model.Product;

import java.util.List;

/**
 * 商品関連のデータアクセスを担当するリポジトリ
 * MyBatis アノテーションを直接使用し、Mapper を分離しない構成
 */
@Repository
@Mapper
public interface ProductRepository {

    /**
     * すべての商品情報を取得
     */
    @Select("SELECT id, name, price FROM products")
    List<Product> findAll();

    /**
     * 指定した商品IDの名前を更新
     */
    @Update("UPDATE products SET name = #{name} WHERE id = #{id}")
    int updateProductName(@Param("id") Long id, @Param("name") String name);
}
