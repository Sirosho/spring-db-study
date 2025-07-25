package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 컨텍스트에서 관리되는 빈을 꺼내올 수 있음
                // main과 test는 패키지가 달라서 클래스를 참조하는데에
                // 제약이 있어서 이걸 꼭적음
class BookJdbcRepositoryTest {

    // 테스트 프레임워크 : JUnit
    // 5버전에서부터는 생성자주입을 막아놈 - 필드주입 해야함
    @Autowired
    BookJdbcRepository bookJdbcRepository;

    // 테스트 메서드
    @Test
    // 테스트의 목적을 주석처럼 적는다. 여기서 문장표현은 단언(Assertion)을 사용한다.
    @DisplayName("도서 정보를 주면 데이터베이스 book테이블에 저장된다.")
    void saveTest() {
        // GWT 패턴
        // given - 테스트를 위해 필요한 데이터
        Book givenBook = Book.builder()
                .title("디아블로4")
                .author("블리자드")
                .isbn("D004")
                .build();
        // when - 실제 테스트가 벌어지는 상황
        boolean flag = bookJdbcRepository.save(givenBook);

        // then - 테스트 결과 (단언)
        Assertions.assertTrue(flag);

    }
    @Test
    @DisplayName("도서의 제목과 저자명을 수정해야 한다.")
    void updateTest() {
        //given
        Book updateBook = Book.builder()
                .title("수정된책")
                .author("수정된 저자명")
                .id(6L)
                .build();
        //when

        boolean flag = bookJdbcRepository.updateTitleAndAuthor(updateBook);
        //then
        Assertions.assertFalse(!flag);
    }

    @Test
    @DisplayName("id를 주면 book테이블에서 해당 id를 가진 행이 삭제된다.")
    void deleteTest() {
        //given
        Long givenId = 5L;
        //when
        boolean flag = bookJdbcRepository.deleteById(givenId);
        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("전체조회를 하면 도서의 리스트가 반환된다.")
    void findAllTest() {
        //given

        //when
        List<Book> bookList = bookJdbcRepository.findAll();
        //then
        bookList.forEach(System.out::println);

        assertEquals(5, bookList.size()); // 예상 갯수와 기준
        assertNotNull(bookList.get(0));
        assertEquals("반지의제왕",bookList.get(0).getTitle());
    }

    @Test
    @DisplayName("id를 입력하면 id에 맞는 책을 단일조회")
    void findByIdTest() {
        //given
        Long givenId = 5L;
        //when
        Book foundBook = bookJdbcRepository.findById(givenId);
        System.out.println("foundBook = " + foundBook);

        //then
        assertNotNull(foundBook); // 이게 NULL이 아닌걸 확신함! 아니면 테스트 실패해라 라는 명령어

    }



}