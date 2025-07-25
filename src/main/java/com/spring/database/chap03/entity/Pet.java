package com.spring.database.chap03.entity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    private Long id;
    private String petName;
    private int petAge;
    private boolean injection;

}
