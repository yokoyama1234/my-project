package com.example.loginbackend.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import com.example.loginbackend.domain.model.Product;

import java.util.List;

/**
 * 商品関連のDB操作を行うMapperインターフェース
 * MyBatisを使用してSQLをマッピング
 */
@Mapper
public interface ProductMapper {

    /**
     * すべての商品情報を取得
     * 
     * @return Productオブジェクトのリスト
     */
    @Select("SELECT id, name, price FROM products")
    List<Product> findAll();

    /**
     * 指定した商品IDの名前を更新
     * 
     * @param id   更新対象の商品ID
     * @param name 更新後の名前
     * @return 更新件数（0の場合は対象なし）
     */
    @Update("UPDATE products SET name = #{name} WHERE id = #{id}")
    int updateProductName(@Param("id") Long id, @Param("name") String name);
}
