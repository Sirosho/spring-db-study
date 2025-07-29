package com.spring.database.jpa.practice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.processing.Pattern;

import java.util.List;


@Getter
@Setter
@ToString(exclude = { "episodes" })
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="series_id")
    private Long id;

    @Column(name="title",length=30,nullable = false)
    private String title;
    @Column(name="genre",length=20)
    private String genre;
    @Column(name="director")
    private String director;

    @Column(name="releaseYear",length=4)
    private String releaseYear;

    @OneToMany(mappedBy = "series")
    private List<Episode> episodes;




}
