package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.AuthentMeansEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthentMeansRepository extends CrudRepository<AuthentMeansEntity, Long> {

    @Query(value = "SELECT coalesce(max(id), 0) FROM AuthentMeansEntity")
    Long getMaxId();

    AuthentMeansEntity getById(Long id);

    List<AuthentMeansEntity> findAll();

    List<AuthentMeansEntity> findAll(Pageable pageable);
}
