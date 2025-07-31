//package com.spring.database.jpa.practice2.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//
//@Entity
//@Table(name = "course")
//public class Course {
//
//    @Id
//    @Column(name = "course_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "course_title")
//    private String title;
//
//    @Column(name = "instructor")
//    private String instructor;
//    @Column(name = "price")
//    private Double price;
//
//    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Enrollment> enrollments;
//}
