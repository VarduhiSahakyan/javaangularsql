package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.RuleCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleConditionRepository extends CrudRepository<RuleCondition, Long> {

    List<RuleCondition> findAll();

    List<RuleCondition> findAll(Pageable pageable);

    RuleCondition getById(Long id);

}
