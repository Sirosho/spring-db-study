package com.spring.database.querydsl.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.spring.database.querydsl.entity.QAlbum.*;
import static com.spring.database.querydsl.entity.QGroup.*;
import static com.spring.database.querydsl.entity.QIdol.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
@Rollback(false)
public class QueryDslSubqueryTest {
    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    JPAQueryFactory factory;

    @Autowired
    EntityManager em;


    @BeforeEach
    void setUp() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");
        Group bts = new Group("방탄소년단");
        Group newjeans = new Group("뉴진스");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);
        groupRepository.save(bts);
        groupRepository.save(newjeans);

        Idol idol1 = new Idol("김채원", 24, "여", leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, "여", leSserafim);
        Idol idol3 = new Idol("가을", 22, "여", ive);
        Idol idol4 = new Idol("리즈", 20, "여", ive);
        Idol idol5 = new Idol("장원영", 20, "여", ive);
        Idol idol6 = new Idol("안유진", 21, "여", ive);
        Idol idol7 = new Idol("카즈하", 21, "여", leSserafim);
        Idol idol8 = new Idol("RM", 29, "남", bts);
        Idol idol9 = new Idol("정국", 26, "남", bts);
        Idol idol10 = new Idol("해린", 18, "여", newjeans);
        Idol idol11 = new Idol("혜인", 16, "여", newjeans);
        Idol idol12 = new Idol("김종국", 48, "남", null);
        Idol idol13 = new Idol("아이유", 31, "여", null);


        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);
        idolRepository.save(idol6);
        idolRepository.save(idol7);
        idolRepository.save(idol8);
        idolRepository.save(idol9);
        idolRepository.save(idol10);
        idolRepository.save(idol11);
        idolRepository.save(idol12);
        idolRepository.save(idol13);


        Album album1 = new Album("MAP OF THE SOUL 7", 2020, bts);
        Album album2 = new Album("FEARLESS", 2022, leSserafim);
        Album album3 = new Album("UNFORGIVEN", 2023, bts);
        Album album4 = new Album("ELEVEN", 2021, ive);
        Album album5 = new Album("LOVE DIVE", 2022, ive);
        Album album6 = new Album("OMG", 2023, newjeans);
        Album album7 = new Album("AFTER LIKE", 2022, ive);

        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);
        albumRepository.save(album4);
        albumRepository.save(album5);
        albumRepository.save(album6);
        albumRepository.save(album7);

        em.flush();
        em.clear();

    }

    @Test
    @DisplayName("Inner Join 예제")
    void innerJoinTest() {
        //given

        //when
        // 명시적으로 아이돌과 그룹을 조인하고 싶다.
        System.out.println("===========================  DB 접근   ============================");
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                // innerJoin의 첫번째 파라미터는 from절에 쓴 엔터티의 연관관계객체
                // 두번째 파라미터는 실제로 조인할 엔터티
                .innerJoin(idol.group, group).fetchJoin() // fetchJoin으로 N+1 문제를 해결
                .fetch();

        //then
        System.out.println("===========      result      =============");
        for (Idol i : idolList) {
            System.out.println("i = " + i + i.getGroup());
        }
    }

    @Test
    @DisplayName("Left Outer Join 예제")
    void leftJoinTest() {
        //given
        System.out.println("===========================  DB 접근   ============================");
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                // innerJoin의 첫번째 파라미터는 from절에 쓴 엔터티의 연관관계객체
                // 두번째 파라미터는 실제로 조인할 엔터티
                .leftJoin(idol.group, group).fetchJoin() // fetchJoin으로 N+1 문제를 해결
                .fetch();

        //then
        System.out.println("===========      result      =============");
        for (Idol i : idolList) {
            System.out.println("i = " + i + ((group == null) ? "그룹없음" : i.getGroup()));
        }
        //when

        //then
    }
@Test
@DisplayName("그룹별 평균 나이 계산")
void averageAgeTest() {
    //given

    //when
    List<Tuple> fetch = factory
            .select(group.groupName, idol.age.avg())
            .from(idol)
            .innerJoin(idol.group, group)
            .groupBy(group.groupName)
            .fetch();
    //then
    for (Tuple tuple : fetch) {
        String s = tuple.get(group.groupName);
        Double avg = tuple.get(idol.age.avg());
        System.out.printf("%s 그룹의 평균나이 : %.2f\n", s, avg);
    }

}

@Test
@DisplayName("2022년에 발매된 앨범이 있는 아이돌의 정보(아이돌명, 그룹명, 앨범명, 발매년도) 조회")
void albumTest () {
    /*
    *   SELECT  I.idol_name,
    *           G.group_name,
    *           A.album_name,
    *           A.release_year
    *   FROM tbl_idol I
    *   JOIN tbl_group G
    *   On I.group_id = G.group_id
    *   Join tbl_album A
    *   ON G.group_id = A.group_id
    *   Where A.release_year = 2022
    *
    * */


    //given
    int year = 2022;
    //when
    List<Tuple> idolList = factory
            // 첫 타겟 idol -> 아 idol이라는 엔터티가 있는거구나
            .select(idol,album)
            .from(idol)
            // 아 idol은 알고있는데 idol에 있는 group이 Qgroup이랑 같은거구나
            .innerJoin(idol.group, group) // 뒤의 id는 생략
            // 아 group은 알고 있는데 album은 뭐임?
            // 야 group에 있는 albums가 album이야
            .innerJoin(group.albums, album)
            .where(album.releaseYear.eq(year))
            .fetch();

    //then
    for (Tuple tuple : idolList) {
        Idol idol1 = tuple.get(idol);
        Album album1 = tuple.get(album);

        System.out.printf("\n # 아이돌명: %s, 그룹명: %s, 앨범명: %s, 발매년도: %d년 \n\n",
        idol1.getIdolName(), idol1.getGroup().getGroupName(), album1.getAlbumName(), album1.getReleaseYear());
    }

}

@Test
@DisplayName("특정 아이돌그룹의 평균 나이보다 많은 아이돌정보 조회")
void subQueryTest1() {
    //given
    String groupName = "르세라핌";
    //when
    List<Idol> idolList = factory
            .selectFrom(idol)
            .where(idol.age.gt(
                    JPAExpressions
                            .select(idol.age.avg())
                            .from(idol)
                            .where(idol.group.groupName.eq(groupName))
            ))
            .fetch();
    //then
    idolList.forEach(System.out::println);

}


    @Test
    @DisplayName("그룹별로 가장 최근의 발매된 앨범 정보 조회")
    void subqueryTest2() {
        /*

            SELECT G.group_name, A.album_name, A.release_year
            FROM tbl_group G
            INNER JOIN tbl_album A
            ON G.group_id = A.group_id
            WHERE A.album_id IN (
                    SELECT S.album_id
                    FROM tbl_album S
                    WHERE S.group_id = A.group_id
                        AND s.release_year = (
                            SELECT MAX(release_year)
                            FROM tbl_album
                            WHERE S.group_id = A.group_id
                        )
            )

         */
        //given
        QAlbum albumA = new QAlbum("albumA");
        QAlbum albumS = new QAlbum("albumS");

        //when
        List<Tuple> idolTuples = factory
                .select(group.groupName, albumA.albumName, albumA.releaseYear)
                .from(group)
                .innerJoin(group.albums, albumA)
                .where(albumA.id.in(
                        JPAExpressions
                                .select(albumS.id)
                                .from(albumS)
                                .where(
                                        albumS.group.id.eq(albumA.group.id)
                                                .and(
                                                        albumS.releaseYear.eq(
                                                                JPAExpressions
                                                                        .select(albumS.releaseYear.max())
                                                                        .from(albumS)
                                                                        .where(albumS.group.id.eq(albumA.group.id))
                                                        )
                                                )
                                )
                ))
                .fetch();

        //then
        for (Tuple tuple : idolTuples) {
            String groupName = tuple.get(group.groupName);
            String albumName = tuple.get(albumA.albumName);
            Integer releaseYear = tuple.get(albumA.releaseYear);

            System.out.println("\nGroup: " + groupName
                    + ", Album: " + albumName
                    + ", Release Year: " + releaseYear);
        }
    }

    @Test
    @DisplayName("특정 연도에 발매된 앨범 수가 2개 이상인 그룹 조회")
    void testFindGroupsWithMultipleAlbumsInYear() {

        int targetYear = 2022;

        QAlbum subAlbum = new QAlbum("subAlbum");

        // 서브쿼리: 각 그룹별로 특정 연도에 발매된 앨범 수를 계산
        JPQLQuery<Long> subQuery = JPAExpressions
                .select(subAlbum.group.id)
                .from(subAlbum)
                .where(subAlbum.releaseYear.eq(targetYear))
                .groupBy(subAlbum.group.id)
                .having(subAlbum.count().goe(2L));

        // 메인쿼리: 서브쿼리의 결과와 일치하는 그룹 조회
        List<Group> result = factory
                .selectFrom(group)
                .where(group.id.in(subQuery))
                .fetch();

        assertFalse(result.isEmpty());
        for (Group g : result) {
            System.out.println("\nGroup: " + g.getGroupName());
        }
    }

    @Test
    @DisplayName("그룹이 존재하지 않는 아이돌 조회")
    void testFindIdolsWithoutGroup() {

        // 서브쿼리: 아이돌이 특정 그룹에 속하는지 확인
        JPQLQuery<Long> subQuery = JPAExpressions
                .select(group.id)
                .from(group)
                .where(group.id.eq(idol.group.id));

        // 메인쿼리: 서브쿼리 결과가 존재하지 않는 아이돌 조회
        List<Idol> result = factory
                .selectFrom(idol)
                .where(subQuery.notExists())
                .fetch();

        assertFalse(result.isEmpty());
        for (Idol i : result) {
            System.out.println("\nIdol: " + i.getIdolName());
        }
    }

    @Test
    @DisplayName("아이브의 평균 나이보다 나이가 많은 여자 아이돌 조회")
    void testFindMaleIdolsOlderThanGroupAverageAge() {
        String groupName = "아이브";

        JPQLQuery<Double> subQuery = JPAExpressions
                .select(idol.age.avg())
                .from(idol)
                .where(idol.group.groupName.eq(groupName));

        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.age.gt(subQuery).and(idol.gender.eq("여")))
                .fetch();

        assertFalse(result.isEmpty());
        for (Idol i : result) {
            System.out.println("\nIdol: " + i.getIdolName() + ", Age: " + i.getAge());
        }
    }


    @Test
    @DisplayName("특정 연도에 발매된 앨범이 없는 그룹 조회")
    void testFindGroupsWithoutAlbumsInYear() {
        int targetYear = 2023;

//        JPQLQuery<Long> subQuery = JPAExpressions
//                .select(album.group.id)
//                .from(album)
//                .where(album.releaseYear.eq(targetYear));
//
//        List<Group> result = factory
//                .selectFrom(group)
//                .where(group.id.notIn(subQuery))
//                .fetch();

        JPQLQuery<Long> subQuery = JPAExpressions
                .select(album.group.id)
                .from(album)
                .where(album.releaseYear.eq(targetYear)
                        .and(album.group.id.eq(group.id)));

        List<Group> result = factory
                .selectFrom(group)
                .where(subQuery.notExists())
                .fetch();


        assertFalse(result.isEmpty());
        for (Group g : result) {
            System.out.println("Group: " + g.getGroupName());
        }
    }



}
