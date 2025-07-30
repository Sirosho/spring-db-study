package com.spring.database.jpa.chap02.repository;


import com.spring.database.jpa.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaRepository의 제네릭에는 첫번째 엔터티, 두번째 ID 타입
public interface StudentRepository  {


    // Query Method: 메서드에 특별한 이름규칙을 사용해서 SQL을 생성
    List<Student>  findByName(String name); // findby(필드명) 필드명과 동일하게 지어줘야함


    //WHERE CITY = ? AND MAJOR = ?
    List <Student>findByCityAndMajor(String city, String major);


    //WHERE MAJOR LIKE '%공학%'
    List <Student> findByMajorContaining(String major);


    //WHERE MAJOR LIKE '공학%'
    List <Student> findByMajorStartingWith(String major);

    //WHERE MAJOR LIKE '%공학'
    List <Student> findByMajorEndingWith(String major);

    //WHERE age <= ?
//    List <Student> findByAgeLessThanEqual(int age);

    // JPQL 사용하기
    // 도시 이름으로 학생 조회하기
    //별칭을 정하고 자바 객체처럼 호출
    // 파라미터 갯수에 맞춰 where절에 ?n 숫자 입력
    @Query(value="SELECT s FROM Student s WHERE s.city = ?1")
    List<Student>getStudentByCity(String city);

    // 순수 SQL 사용하기
    // 도시 AND 이름으로 학생 조회하기
    @Query(value = """
                    SELECT *
                    FROM tbl_student
                    WHERE city = ?2
                        AND stu_name = ?1
                  """, nativeQuery = true)
    List<Student> getStudents(String name, String city);

}
