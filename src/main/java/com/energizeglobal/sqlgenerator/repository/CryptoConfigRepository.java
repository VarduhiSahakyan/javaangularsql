package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.CryptoConfigurationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoConfigRepository extends CrudRepository<CryptoConfigurationEntity, Long> {

    @Query(value = "SELECT coalesce(max(id), 0) FROM CryptoConfigurationEntity")
    Long getMaxId();

    CryptoConfigurationEntity getById(Long id);

    List<CryptoConfigurationEntity> findAll();

    List<CryptoConfigurationEntity> findAll(Pageable pageable);
}
