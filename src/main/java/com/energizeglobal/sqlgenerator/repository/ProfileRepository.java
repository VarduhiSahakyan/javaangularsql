package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile,Long> {

    List<Profile> findAll();

    @Query("SELECT p FROM Profile p WHERE p.id = ?1")
    Profile getProfileById(long id);

    @Query("SELECT p FROM Profile p WHERE p.name = ?1")
    Profile getProfileByName(String name);

    List<Profile> findAll(Pageable pageable);

    Profile getById(Long id);

}
