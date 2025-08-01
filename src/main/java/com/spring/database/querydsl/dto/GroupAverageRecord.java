package com.spring.database.querydsl.dto;


import com.querydsl.core.Tuple;
import com.spring.database.querydsl.entity.QIdol;
import lombok.Builder;

// record : 자동 보일러플레이트 생성 (getter, setter ...)
@Builder
public record GroupAverageRecord(
        String groupName,
        Double average
)
{
    public static GroupAverageAge from(Tuple tuple) {
        return GroupAverageAge.builder()
                .groupName(tuple.get(QIdol.idol.group.groupName))
                .averageAge(tuple.get(QIdol.idol.age.avg()))
                .build();
    }

}
