package com.spring.database.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.entity.Group;
import com.spring.database.querydsl.entity.Idol;
import com.spring.database.querydsl.entity.QIdol;
import com.spring.database.querydsl.repository.GroupRepository;
import com.spring.database.querydsl.repository.IdolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;

import static com.spring.database.querydsl.entity.Idol.*;
import static com.spring.database.querydsl.entity.QIdol.*;

@SpringBootTest
public class QueryDslSortTest {
    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    JPAQueryFactory factory;


    @BeforeEach
    void setUp() {

        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, leSserafim);
        Idol idol3 = new Idol("가을", 22, ive);
        Idol idol4 = new Idol("리즈", 20, ive);
        Idol idol5 = new Idol("장원영", 20, ive);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);

    }



    @Test
    @DisplayName("기본 정렬 사용법")
    void sortTest() {
        //given
        List<Idol> idolList = factory.selectFrom(idol)
                .orderBy(idol.age.desc(),idol.idolName.asc()) // 콤마로 바로 2차정렬 붙이기도 가능
                        .fetch();
        //when
        idolList.forEach(System.out::println);
        //then
    }

    @Test
    @DisplayName("페이징 처리하기")
    void pagingTest() {
        //given
        int limit = 2; // 한페이지당 몇개씩 보여줄건지
        int pageNo= 3;
        int offset = (pageNo-1)* limit; // 어디서부터 보여줄건지(첫번째 데이터가 0번)

        //when
        List<Idol> idolList = factory.selectFrom(idol)
                .orderBy(idol.age.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
        //then
System.out.println("===========      result      =============");
        idolList.forEach(System.out::println);

    }




}
