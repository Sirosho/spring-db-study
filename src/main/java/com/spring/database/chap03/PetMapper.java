package com.spring.database.chap03;

import com.spring.database.chap03.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// CRUD를 명세
@Mapper //
public interface PetMapper {


    // create
    boolean save(Pet pet);

    // read - single matching
    Pet findById(Long id);

    // read - multiple matching
    List<Pet> findAll();

    // update
    boolean update(Pet pet);

    // delete
    boolean deleteById(Long id);

    // read - count
    int petCount();

}
