package com.spring.database.jpa.practice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = { "series" })
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "episode")
public class Episode {
    @Id
    @Column(name="episode_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeId;

    @Column(name="episode_number",nullable = false)
    private Integer episodeNumber;

    @Column(name="episode_title",length=20)
    private String title;

    @Column(name="duration")
    private Integer duration;

    @Column(name="view_count")
    private Integer viewCount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;

}
