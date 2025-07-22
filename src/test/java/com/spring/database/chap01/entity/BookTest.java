package com.spring.database.chap01.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest   // 의존 객체를 테스트시에 주입받기위한 설정
class BookTest {

    @Autowired // 필드 주입
    private DataSource dataSource;

    @Test
    void insertTest() {
        // 책 한권의 데이터를 DB에 실제로 저장
        // DB를 연결
        try (Connection conn = dataSource.getConnection()) {
            // 1. 데이터베이스 소켓 연결 - DB에 인증정보를 주고 확인받는 작업

            // 2. SQL 작성
            String sql = """
                        INSERT INTO BOOKS
                            (title, author, isbn)
                        VALUES
                            (?, ?, ?)
                        """;

            // 3. SQL을 실행하는 객체를 가져와야 함.
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. 실행하기전에 ? 값들을 채우는 작업
            pstmt.setString(1, "사랑의 하츄핑");
            pstmt.setString(2, "캐치 티니핑");
            pstmt.setString(3, "B001");

            // 5. SQL을 실행
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*finally {
            // 데이터베이스 연결 해제
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }


    @Test
    void updateTest() {

        try(Connection conn = dataSource.getConnection()){

            String sql = """
                    UPDATE BOOKS
                    SET author = ?,isbn = ?
                    WHERE id = ?
                    """;
            // 3. SQL을 실행하는 객체를 가져와야 함.
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. 실행하기전에 ? 값들을 채우는 작업
            pstmt.setString(1, "오로라핑");
            pstmt.setString(2, "Z999");
            pstmt.setLong(3, 2L);

            pstmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    void deleteTest() {

        try(Connection conn = dataSource.getConnection()){

            String sql = """
                    DELETE FROM BOOKS
                    WHERE id = ?
                    """;
            // 3. SQL을 실행하는 객체를 가져와야 함.
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. 실행하기전에 ? 값들을 채우는 작업
            pstmt.setLong(1, 2);

            pstmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }


    // 전체조회
    @Test
    void findAllTest() {
        try(Connection conn = dataSource.getConnection()){

            String sql = """
                    SELECT * FROM BOOKS
                    WHERE title LIKE ?
                    """;
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // SQL 실행
            pstmt.setString(1, "%잼%");
            ResultSet rs = pstmt.executeQuery();// SELECT는 executeQuery 로 해야함
            //.next() 는 테이블을 가리키는 커서가
            // 한 행씩 넘어가게하는 메서드 사용전에는 커서가 0행에 위치해있다

            while(rs.next()){
                String title = rs.getString("title");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");

                System.out.println("title = " + title);
                System.out.println("author = " + author);
                System.out.println("isbn = " + isbn);
                System.out.println("========================");
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }





}