package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.ImageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<ImageEntity, Long> {

    ImageEntity findByName(String name);

    List<ImageEntity> findAll(Pageable pageable);

    ImageEntity getById(Long id);
}
