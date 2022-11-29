package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.ProfileSet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileSetRepository extends CrudRepository<ProfileSet,Long> {

    @Query("SELECT p FROM ProfileSet p WHERE p.id=?1")
    ProfileSet getProfileSetById(Long id);

    ProfileSet getById(Long id);

    List<ProfileSet> findAll();

    List<ProfileSet> findAll(Pageable pageable);
}
