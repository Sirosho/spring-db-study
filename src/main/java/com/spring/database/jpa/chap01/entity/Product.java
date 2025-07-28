package com.spring.database.jpa.chap01.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

// 상품정보를 데이터베이스에 관리
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity // 이 클래스는 데이터베이스 테이블과 1:1로 매칭되는 클래스입니다.
@Table(name = "tbl_product") // 테이블 이름 정하기, 생략시 클래스이름과 똑같음
public class Product {

    @Id // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 설정문 적는 어노테이션 (자동으로 1씩 증가)
    @Column(name = "prod_id") // 컬럼명 지정 생략시 필드이름과 동일
    private Long id;

    @Column(name = "prod_nm" , length = 50, nullable = false) // 컬럼명, 길이, null 가능 여부
    private String name; // 상품명
    @Column(name = "prod_price", nullable = false)
    private int price; // 가격

    @CreationTimestamp // INSERT 시 자동으로 생성되는 시간을 저장
    @Column(updatable = false) // 수정 불가 옵션
    private LocalDateTime createdAt; // 상품 등록 시간

    @UpdateTimestamp // UPDATE 시 자동으로 수정되는 시간을 저장
    private LocalDateTime updatedAt; // 상품 최종 수정 시간

    // 열거형 데이터는 따로 옵션을 안주면 숫자로 저장함.
    // FOOD : 1 , FASHION : 2 , ...
    @Enumerated(EnumType.STRING)// 문자열로 저장 (기본값은 ORDINAL (숫자))
    @Column(nullable = false)
    private Category category; // 상품 카테고리

    public void changeProduct(String newName, int newPrice, Category newCategory) {
    }

    public enum Category {
        FOOD,FASHION,ELECTRONIC
    }
}
