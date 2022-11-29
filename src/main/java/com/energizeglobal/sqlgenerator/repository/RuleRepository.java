package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.RuleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends CrudRepository<RuleEntity, Long> {

    RuleEntity getById(Long id);

    List<RuleEntity> findAll();

    List<RuleEntity> findAll(Pageable pageable);
}
