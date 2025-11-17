package com.example.loginbackend.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import com.example.loginbackend.domain.model.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT id, name, price FROM products")
    List<Product> findAll();

    @Update("UPDATE products SET name = #{name} WHERE id = #{id}")
    int updateProductName(@Param("id") Long id, @Param("name") String name);
}
