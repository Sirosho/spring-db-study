package com.spring.database.jpa.practice.repository;

import com.spring.database.jpa.practice.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository <Series, Long> {

}
