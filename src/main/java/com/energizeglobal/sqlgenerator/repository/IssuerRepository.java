package com.energizeglobal.sqlgenerator.repository;

import com.energizeglobal.sqlgenerator.domain.Issuer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuerRepository extends CrudRepository<Issuer, Long> {

    @Query("SELECT i FROM Issuer i WHERE i.code = ?1")
    Issuer getIssuerByCode(String code);

    List<Issuer> findAll(Pageable pageable);

    List<Issuer> findAll();

    Issuer getById(Long id);

}
