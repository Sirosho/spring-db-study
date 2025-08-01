package com.spring.database.querydsl.entity;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.jpa.chap02.entity.Student;
import com.spring.database.jpa.chap02.repository.StudentRepository;
import com.spring.database.querydsl.dto.GroupAverageAge;
import com.spring.database.querydsl.entity.Group;
import com.spring.database.querydsl.entity.Idol;
import com.spring.database.querydsl.entity.QIdol;
import com.spring.database.querydsl.repository.GroupRepository;
import com.spring.database.querydsl.repository.IdolRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.spring.database.querydsl.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class QueryDslBasicTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    EntityManager em;  // 순수 JPA의 핵심객체

    @Autowired
    JdbcTemplate jdbcTemplate;  // JDBC의 핵심객체

    @Autowired
    JPAQueryFactory factory;

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void testInsertData() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");
        Group bts = new Group("방탄소년단");
        Group newjeans = new Group("newjeans");

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
    }


    @Test
    @DisplayName("순수 JPQL로 특정 이름의 아이돌 조회하기")
    void rawJpqlTest() {
        //given
        String jpql = """
                SELECT i
                FROM Idol i
                WHERE i.idolName = ?1
                """;

        //when
        Idol foundIdol = em.createQuery(jpql, Idol.class)
                .setParameter(1, "가을")
                .getSingleResult();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());

    }


    @Test
    @DisplayName("순수 SQL로 특정 이름의 아이돌 조회하기")
    void nativeSqlTest() {
        //given
        String sql = """
                SELECT *
                FROM tbl_idol
                WHERE idol_name = ?1
                """;
        //when
        Idol foundIdol = (Idol) em.createNativeQuery(sql, Idol.class)
                .setParameter(1, "리즈")
                .getSingleResult();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }

//
//    @Test
//    @DisplayName("JDBC로 특정이름의 아이돌 조회하기")
//    void jdbcTest() {
//        //given
//        String sql = """
//                SELECT *
//                FROM tbl_idol
//                WHERE idol_name = ?
//                """;
//        //when
//        Idol foundIdol = jdbcTemplate.queryForObject(sql,
//                (rs, n) -> new Idol(
//                        rs.getLong("idol_id")
//                        , rs.getString("idol_name")
//                        , rs.getInt("age")
//                        , null
//                ),
//                "김채원"
//        );
//
//        Group group = jdbcTemplate.queryForObject("""
//                        SELECT * FROM tbl_group WHERE group_id = ?
//                        """,
//                (rs, n) -> new Group(
//                        rs.getLong("group_id")
//                        , rs.getString("group_name")
//                        , null
//                ),
//                1
//        );
//
//        foundIdol.setGroup(group);
//
//        //then
//        System.out.println("\n\nfoundIdol = " + foundIdol);
//        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
//    }

    @Test
    @DisplayName("QueryDsl로 특정 이름의 아이돌 조회하기")
    void queryDslTest() {
        //given
//        JPAQueryFactory factory = new JPAQueryFactory(em);
        //when
        factory
                .selectFrom(idol)
                .where(idol.idolName.eq("가을"))
                .fetchOne();

        //then
    }

    @Test
    @DisplayName("이름 AND 나이로 아이돌 조회하기")
    void searchTest() {
        //given
        String name = "리즈";
        int age = 20;

        //when
        Idol foundIdol = factory.selectFrom(idol)
                .where(idol.idolName.eq(name)
                        .and(idol.age.eq(age))
                )
                .fetchOne();


        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }
//        idol.idolName.eq("리즈") // idolName = '리즈'
//        idol.idolName.ne("리즈") // idolName != '리즈'
//        idol.idolName.eq("리즈").not() // idolName != '리즈'
//        idol.idolName.isNotNull() //이름이 is not null
//        idol.age.in(10, 20) // age in (10,20)
//        idol.age.notIn(10, 20) // age not in (10, 20)
//        idol.age.between(10,30) //between 10, 30
//        idol.age.goe(30) // age >= 30
//        idol.age.gt(30) // age > 30
//        idol.age.loe(30) // age <= 30
//        idol.age.lt(30) // age < 30
//        idol.idolName.like("_김%")  // like _김%
//        idol.idolName.contains("김") // like %김%
//        idol.idolName.startsWith("김") // like 김%
//        idol.idolName.endsWith("김") // like %김


    @Test
    @DisplayName("다양한 조회결과 반환하기")
    void fetchTest() {
        //given
        List<Idol> idolList = factory
                .selectFrom(idol)
                .fetch();
        System.out.println("============     fetch     ============");

        //when
        idolList.forEach(System.out::println);
        // fetchFirst() : 다중행 조회에서 첫번째 행을 반환
        Idol fetchedFirst = factory
                .selectFrom(idol)
                .where(idol.age.loe(22))
                .fetchFirst();
        System.out.println("\n \n ============     fetchFirst     ============");


        System.out.println("fetchedFirst = " + fetchedFirst);
// 단일행 조회시 NPE에 취약하기 때문에 Optional을 사용하고 싶을 때는??

        Optional<Idol> fetchOne = Optional.ofNullable(factory
                .selectFrom(idol)
                .where(idol.idolName.eq("김채원"))
                .fetchOne());

        fetchOne.orElseThrow(() -> new RuntimeException("아이돌이 없습니다."));
    }


    @Test
    @DisplayName("나이가 24세 이상인 아이돌 조회")
    void testAgeGoe() {
        // given
        int ageThreshold = 24;

        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.age.goe(ageThreshold))
                .fetch();

        // then
        assertFalse(result.isEmpty());

        for (Idol idol : result) {
            System.out.println("\n\nIdol: " + idol);
            assertTrue(idol.getAge() >= ageThreshold);
        }
    }

    @Test
    @DisplayName("이름에 '김'이 포함된 아이돌 조회")
    void testNameContains() {
        // given
        String substring = "김";

        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.idolName.contains(substring))
                .fetch();

        // then
        assertFalse(result.isEmpty());
        for (Idol idol : result) {
            System.out.println("Idol: " + idol);
            assertTrue(idol.getIdolName().contains(substring));
        }
    }

    @Test
    @DisplayName("나이가 20세에서 25세 사이인 아이돌 조회")
    void testAgeBetween() {
        // given
        int ageStart = 20;
        int ageEnd = 25;


        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.age.between(ageStart, ageEnd))
                .fetch();

        // then
        assertFalse(result.isEmpty());
        for (Idol idol : result) {
            System.out.println("Idol: " + idol);
            assertTrue(idol.getAge() >= ageStart && idol.getAge() <= ageEnd);
        }
    }


    @Test
    @DisplayName("르세라핌 그룹에 속한 아이돌 조회")
    void testGroupEquals() {
        // given
        String groupName = "르세라핌";

        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.group.groupName.eq(groupName))
                .fetch();

        // then
        assertFalse(result.isEmpty());
        for (Idol idol : result) {
            System.out.println("Idol: " + idol);
            assertEquals(groupName, idol.getGroup().getGroupName());
        }
    }


    @Test
    @DisplayName("데이터 잘라서 가져오기")
    void pagingTest() {
        //given

        //when

        //then
    }

// // when
//    @Test
//    @DisplayName("tupletest")
//    void tupleTest() {
//        //given
//
//    List<Tuple> idolList = factory
//        //when
//         .selectFrom(idol.idolName,idol.age)
//         .fetch();
//
//        //then
//    }

    @Test
    @DisplayName("전체 그룹화 하기 - GROUP BY 없이 집계함수를 사용하는 것")
    void groupByTest() {
        //given

        //when
        Integer sumAge = factory
                .select(idol.age.sum()) // SUM(age)
                .from(idol)
                .fetchOne();
        //then
        System.out.println("sumAge = " + sumAge);
    }

    @Test
    @DisplayName("그룹별 인원수 세기")
    void groupByCountTest() {

        /*
         *
         *    SELECT G.group_name, COUNT(*) AS 인원수
         * 	 FROM tbl_idol
         *    INNER JOIN tbl_group G
         *    ON I.group_id = G.group_id
         *    Group By I.group_id
         *
         *
         * */

        //given

        //when
        List<Tuple> idolGroupCounts = factory
                .select(idol.group.groupName, idol.count())
                .from(idol)
                .groupBy(idol.group.id)
                .fetch();
        //then
        for (Tuple idolGroupCount : idolGroupCounts) {
            String groupName = idolGroupCount.get(idol.group.groupName);
            Long count = idolGroupCount.get(idol.count());
            System.out.println("groupName = " + groupName + ", count = " + count);
        }
    }


    @Test
    @DisplayName("성별별 아이돌 인워수 세기")
    void groupByGenderTest() {

        /*
        *   SELECT gender,count(*)
            FROM tbl_idol
            group by gender
            ;
        *
        *
        */


        //given
        List<Tuple> fetch = factory
                .select(idol.gender, idol.count())
                .from(idol)
                .groupBy(idol.gender)
                .fetch();
        //when
        fetch.forEach(t -> {
            String gender = t.get(idol.gender);
            Long count = t.get(idol.count());
            System.out.printf("[ %s ]: %d \n ,", gender, count);
        });
        //then
    }

    @Test
    @DisplayName("아이돌조회")
    void findGroupByAvgAge() {
        //given
        List<Tuple> fetch = factory
                .select(idol.age.avg(), idol.group.groupName)
                .from(idol)
                .groupBy(idol.group)
                .having(idol.age.avg().between(20, 25))
                .fetch();

        //when
        for (Tuple tuple : fetch) {
            Double avgAge = tuple.get(idol.age.avg());
            String groupName = tuple.get(idol.group.groupName);
            System.out.println("avgAge = " + avgAge + ", groupName = " + groupName);
        }
        //then
    }
    @Test
    @DisplayName("Tuple대신 DTO를 사용해서 조회데이터 매핑하기 ver.1")
    void groupAvgDtoTest1() {
        //given

        //when
        List<GroupAverageAge> results = factory
                .select(idol.group.groupName, idol.age.avg())
                .from(idol)
                .groupBy(idol.group)
                .having(idol.age.avg().between(20, 25))
                .fetch()
                .stream()
                .map(tuple -> new GroupAverageAge(tuple))
                .collect(Collectors.toUnmodifiableList());
        //then
        for (GroupAverageAge result : results) {
            String groupName = result.getGroupName();
            Double averageAge = result.getAverageAge();
            System.out.printf("그룹명: %s, 평균나이: %.2f세\n", groupName, averageAge);
        }
    }

@Test
@DisplayName("Tuple대신 DTO를 사용해서 조회데이터 매핑하기 ver.2")
void groupAvgDtoTest2() {
    //given
    List<GroupAverageAge> results = factory
            .select(
                    Projections.constructor(
                            // 사용할 DTO를 명시 allArgsConstructor가 있어야함
                            GroupAverageAge.class,
                            idol.group.groupName,
                            idol.age.avg()
                    )
            )
            .from(idol)
            .groupBy(idol.group)
            .having(idol.age.avg().between(20, 25))
            .fetch();
    //when
    for (GroupAverageAge result : results) {
        String groupName = result.getGroupName();
        Double averageAge = result.getAverageAge();
        System.out.printf("그룹명: %s, 평균나이: %.2f세\n", groupName, averageAge);
    }

    //then
}
    @Test
    @DisplayName("연령대별 아이돌 수 조회")
    void testAgeGroupBy() {
        // given
        // 연령대를 기준으로 그룹화하고, 각 연령대에 속한 아이돌 수를 조회
        NumberExpression<Integer> ageGroup = new CaseBuilder()
                .when(idol.age.between(10, 19)).then(10)
                .when(idol.age.between(20, 29)).then(20)
                .when(idol.age.between(30, 39)).then(30)
                .otherwise(0);

        // when
        List<Tuple> result = factory
                .select(ageGroup, idol.count())
                .from(idol)
                .groupBy(ageGroup)
                .having(idol.count().goe(2))
                .fetch();

        // then
        assertFalse(result.isEmpty());
        for (Tuple tuple : result) {
            int ageGroupValue = tuple.get(ageGroup);
            long count = tuple.get(idol.count());

            System.out.println("\n\nAge Group: " + ageGroupValue + "대, Count: " + count);
        }
    }

}