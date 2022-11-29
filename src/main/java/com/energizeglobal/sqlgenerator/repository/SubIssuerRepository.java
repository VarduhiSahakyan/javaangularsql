package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.SubIssuer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubIssuerRepository extends CrudRepository<SubIssuer, Long> {

    SubIssuer findByCode(String code);

    void deleteByCode(String code);

    List<SubIssuer> findAll(Pageable pageable);

    List<SubIssuer> findAll();

    SubIssuer getById(Long id);

}
