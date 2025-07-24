package com.spring.database.chap02.repository;

import com.spring.database.chap02.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate template;


    // 상품의 기본적인 CRUD를 5개
    // 생성, 수정 ,논리삭제 ,전체조회, 단일조회
    // 전체 상품의 총액과 평균가격을 가져오는 기능


    //상품 생성
    public boolean addProduct(Product product) {
        String sql = """
                INSERT INTO products
                    (name, price, stock_quantity,description,seller,status)
                VALUES (?,?,?,?,?,?)
                """;

        return template.update(
                sql,
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getSeller(),
                product.getStatus()) == 1;
    }

    // 상품 수정
    public boolean updateProduct(Product product) {
        String sql = """
                UPDATE products
                SET name = ?,
                    price   =?,
                    stock_quantity =?,
                    description =?,
                    seller = ?
                WHERE id = ?
                
                """;
        return template.update(sql,
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getSeller(),
                product.getId()
        ) ==1;

    }
        // 상품삭제
        public boolean deleteProduct(Long id) {
            String sql = """
                    UPDATE products
                    SET status = 'DELETED'
                    WHERE id = ?
                    """;
            return template.update(sql,id) == 1;
        }
    // 상품 재등록
    public boolean activeProduct(Long id) {
        String sql = """
                    UPDATE products
                    SET status = 'ACTIVE'
                    WHERE id = ?
                    """;
        return template.update(sql,id) == 1;
    }


    //전체조회 - 논리삭제된 행을 제외해야 함

    public List<Product> findAll() {
        String sql = """
                    SELECT * FROM products
                    WHERE status != 'DELETED'
                    """;

//        return template.query(sql, (ResultSet rs, int rowNum)->new Product(rs));
        // BeanPropertyRowMapper : 테이블의 컬럼명과 엔터티클래스의 필드명이
        // 똑같을 경우 (camel,snake 차이도 스펠링 맞으면 됨) 자동 매핑해줌
        // 필드명이 다르면 null값이 들어감
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    // 단일 조회

    public Product findById(Long id) {
        String sql = """
                    SELECT * FROM products
                    WHERE id = ?
                    """;
        return template.queryForObject(sql, (ResultSet rs, int rowNum)->new Product(rs),id);

    }

    //전체상품 총액과 평균가격

    public Map<String, Object> getPriceInfo() {
        String sql = """
                SELECT
                   SUM(price) AS total_price
                    , AVG(price) AS average_price
               FROM PRODUCTS
               WHERE status = 'ACTIVE'
               """;
        return template.queryForObject(sql,
                (rs,  rowNum) -> {
                    int totalPrice = rs.getInt("total_price");
                    double averagePrice = rs.getDouble("average_price");

                    return Map.of(
                            "total", totalPrice,
                            "average", averagePrice
                    );
                });
    }





}
