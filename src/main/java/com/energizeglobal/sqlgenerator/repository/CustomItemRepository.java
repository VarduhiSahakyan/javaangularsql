package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.CustomItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomItemRepository extends CrudRepository<CustomItemEntity, Long> {

    @Query("SELECT ce FROM CustomItemEntity ce WHERE ce.id=?1")
    CustomItemEntity getCustomItemById(Long id);

    List<CustomItemEntity> findAll(Pageable pageable);

    CustomItemEntity getById(Long id);
}
