package com.spring.database.querydsl.repository;

import com.spring.database.querydsl.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository
        extends JpaRepository<Group, Long>, GroupRepositoryCustom {




}
