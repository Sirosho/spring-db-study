package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookSpringRepositoryTest {

    @Autowired
    BookSpringRepository bookSpringRepository;

    @Test
    @DisplayName("스프링 JDBC로 도서를 생성한다.")
    void saveTest() {
        //given
        Book newBook = Book.builder()
                .title("스프링 JDBC")
                .author("자바왕")
                .isbn("S001")
                .build();
        //when
        boolean flag = bookSpringRepository.save(newBook);
        //then
        assertTrue(flag);

    }

    @Test
    @DisplayName("스프링 JDBC로 책 제목과 저자를 수정한다")
    void updateTest() {
        //given
        Book book = Book.builder().title("듄").author("프랭크 허버트").id(9L).build();
        //when
        boolean flag2 = bookSpringRepository.updateTitleAndAuthor(book);
        //then
        assertTrue(flag2);
    }

    @Test
    @DisplayName("ID를 주입받아서 해당 도서를 삭제")
    void deleteTest() {
        //given
        Long givenId = 4L;
        //when
        boolean flag3 = bookSpringRepository.deleteById(givenId);
        //then
        assertTrue(flag3);
    }


}