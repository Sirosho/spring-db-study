package com.spring.database.jpa.practice.repository;

import com.spring.database.jpa.practice.entity.Episode;
import com.spring.database.jpa.practice.entity.Series;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SeriesRepositoryTest {

    @Autowired
    SeriesRepository seriesRepository;
    @Autowired
    EpisodeRepository episodeRepository;

    // 에피소드 정보 만들기


    @BeforeEach
            void insertBeforeTest() {}


    Episode e1 = Episode.builder()
            .episodeNumber(1)
            .title("1화")
            .duration(40)
            .viewCount(100)
            .build();
    Episode e2 = Episode.builder()
            .episodeNumber(2)
            .title("2화")
            .duration(38)
            .viewCount(1000)
            .build();
    Episode e3 = Episode.builder()
            .episodeNumber(3)
            .title("3화")
            .duration(35)
            .viewCount(700)
            .build();
    Episode e4 = Episode.builder()
            .episodeNumber(4)
            .title("4화")
            .duration(45)
            .viewCount(600)
            .build();


Series s1 = Series.builder()
        .title("야인시대")
        .genre("액션")
        .releaseYear("2001")
        .episodes(List.of(e1, e2, e3, e4))
        .build();






    // 시리즈 정보 만들기











}