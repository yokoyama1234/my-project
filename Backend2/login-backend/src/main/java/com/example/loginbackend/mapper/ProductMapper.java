package com.example.loginbackend.mapper;

import com.example.loginbackend.model.ProductResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM products")
    List<ProductResponse> findAll();

    @Update("UPDATE products SET name = #{name} WHERE id = #{id}")
    int updateProductName(@Param("id") Long id, @Param("name") String name);
}
