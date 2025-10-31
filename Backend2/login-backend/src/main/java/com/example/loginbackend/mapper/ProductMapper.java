package com.example.loginbackend.mapper;

import com.example.loginbackend.model.ProductResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品情報にアクセスするMyBatisマッパーインターフェース。
 * <p>
 * 商品一覧の取得や商品情報の更新を行います。
 * </p>
 */
@Mapper
public interface ProductMapper {

    /**
     * 全商品の一覧を取得する。
     *
     * @return {@link ProductResponse} のリスト
     */
    @Select("SELECT * FROM products")
    List<ProductResponse> findAll();

    /**
     * 指定された商品IDの名前を更新する。
     *
     * @param id   商品ID
     * @param name 新しい商品名
     * @return 更新された行数（通常は1）
     */
    @Update("UPDATE products SET name = #{name} WHERE id = #{id}")
    int updateProductName(@Param("id") Long id, @Param("name") String name);
}
