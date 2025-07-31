//package com.spring.database.jpa.practice2.entity;
//
//import com.spring.database.jpa.practice2.repository.CourseRepository;
//import com.spring.database.jpa.practice2.repository.EnrollmentRepository;
//import com.spring.database.jpa.practice2.repository.StudentRepository;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@Rollback(false)
//class EnrollmentTest {
//
//    @Autowired
//    EnrollmentRepository enrollmentRepository;
//
//    @Autowired
//    StudentRepository studentRepository;
//
//    @Autowired
//    CourseRepository courseRepository;
//
//    @Autowired
//    EntityManager em;
//
//    private Student s1;
//    private Student s2;
//    private Course c1;
//    private Course c2;
//    private Course c3;
//
//    @BeforeEach
//    void setUp() {
//
//        Student s1 = Student.builder()
//                .name("호빵맨")
//                .email("asds1212@naver.com")
//                .build();
//
//        Student s2 = Student.builder()
//                .name("세균맨")
//                .email("saegyun8888)naver.com")
//                .build();
//
//        Course c1 = Course.builder()
//                .title("자바기초")
//                .instructor("백승현")
//                .price(100000.0)
//                .build();
//        Course c2 = Course.builder()
//                .title("인터넷사용법")
//                .instructor("박정호")
//                .price(200000.0)
//                .build();
//        Course c3 = Course.builder()
//                .title("종이접기")
//                .instructor("종이맨")
//                .price(300000.0)
//                .build();
//
//        courseRepository.saveAllAndFlush(List.of(c1, c2, c3));
//        studentRepository.saveAllAndFlush(List.of(s1, s2));
//
//    }
//
//    @Test
//    @DisplayName("수강신청 테스트")
//    void saveEnrollmentTest() {
//        //given
//        Enrollment enrollment = Enrollment.builder()
//                .student(s1)
//                .course(c1)
//                .progressRate(0.0)
//                .completed(false)
//                .build();
//
//        //when
//        enrollmentRepository.save(enrollment);
//        //then
//
//        List<Enrollment> enrollments = enrollmentRepository.findAll();
//        enrollments.forEach(d-> System.out.println("enrollments = " + enrollments) );
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}