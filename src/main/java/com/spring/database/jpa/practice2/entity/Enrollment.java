//package com.spring.database.jpa.practice2.entity;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@ToString(exclude = { "student", "course"})
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//
//@Entity
//@Table(name = "enrollment")
//public class Enrollment {
//
//    @Id
//    @Column(name = "enroll_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "student_id")
//    private Student student;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "course_id")
//    private Course course;
//
//    @CreationTimestamp // INSERT 시 자동으로 생성되는 시간을 저장
//    @Column(name = "enroll_date", updatable = false)
//    private LocalDateTime enrollmentDate; // 상품 등록 시간
//
//    @Column(name = "progress_rate")
//    private Double progressRate;
//
//    @Column(name = "completed")
//    private Boolean completed;
//
//}
