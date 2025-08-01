package com.spring.database.chap02.entity;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

//    CREATE TABLE products (
//            id BIGINT AUTO_INCREMENT,
//            name VARCHAR(100) NOT NULL COMMENT '상품명',
//    price INT NOT NULL COMMENT '가격',
//    stock_quantity INT NOT NULL COMMENT '재고수량',
//    description TEXT COMMENT '상품설명',
//    seller VARCHAR(50) NOT NULL COMMENT '판매자',
//    status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '상품상태',
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    PRIMARY KEY (id)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='상품';


    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String description;
    private String seller;
    //ACTIVE : 삭제되지 않은 상품, DELETED : 삭제된 것 (논리적 삭제: 실제로는 남아있음)
    private String status;
    private LocalDateTime createdAt;


    public Product(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.name = rs.getString("name");
        this.price = rs.getInt("price");
        this.stockQuantity = rs.getInt("stock_quantity");
        this.description = rs.getString("description");
        this.seller = rs.getString("seller");
        this.status = rs.getString("status");
        this.createdAt = rs.getTimestamp("created_at").toLocalDateTime();
    }
}
