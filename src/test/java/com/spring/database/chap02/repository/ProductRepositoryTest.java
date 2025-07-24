package com.spring.database.chap02.repository;

import com.spring.database.chap02.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품객체를 입력받아 DB에 추가하는매서드")
    void addProductTest() {
        //given
        Product product = Product.builder()
                .name("로봇청소기")
                .price(140000)
                .stockQuantity(30)
                .description("청소를해줌")
                .seller("LG")
                .status("ACTIVE")
                .build();

        //when
        boolean flag1 = productRepository.addProduct(product);

        //then
        assertTrue(flag1);
    }

    @Test
    @DisplayName("상품수정")
    void updateProductTest () {
        //given

        //when

        //then
    }


    @Test
    @DisplayName("상품삭제")
    void deleteProductTest() {
        //given
        Long givenId = 1L;
        //when
        boolean flag3 = productRepository.deleteProduct(givenId);
        //then
        assertTrue(flag3);
    }

    @Test
    @DisplayName("상품 재등록")
    void activeProductTest() {
        //given
        Long givenId = 1L;
        //when
        boolean flag4 = productRepository.activeProduct(givenId);
        //then
        assertTrue(flag4);
    }

    @Test
    @DisplayName("전체조회")
    void testFindAll() {
        //given

        //when
        List<Product> productList = productRepository.findAll();
        productList.forEach(System.out::println);
        //then
        assertEquals(22, productList.size());
    }

    @Test
    @DisplayName("단일조회")
    void testFindById() {
        //given
        Long givenId = 1L;
        //when
        Product foundProduct = productRepository.findById(givenId);
        System.out.println("foundProduct = " + foundProduct);
        //then
        assertNotNull(foundProduct);
    }

    @Test
    @DisplayName("총액과 평균액조회")
  public void sumAvgTest(){

        productRepository.getPriceInfo();


    }




}