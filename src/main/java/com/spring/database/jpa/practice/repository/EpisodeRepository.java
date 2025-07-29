package com.spring.database.jpa.practice.repository;

import com.spring.database.jpa.practice.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

}
