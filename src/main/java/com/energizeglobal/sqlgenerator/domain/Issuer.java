package com.energizeglobal.sqlgenerator.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "Issuer")
public class Issuer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(unique = true)
    String code;

    @Column(name = "createdBy")
    String createdBy;

    @CreatedDate
    @Column(name = "creationDate", updatable = false)
    Timestamp creationDate;

    @Column
    String description;

    @Column(name = "lastUpdateBy")
    String lastUpdateBy;

    @CreatedDate
    @Column(name = "lastUpdateDate", updatable = false)
    private Timestamp lastUpdateDate;

    @Column
    String name;

    @Column(name = "updateState")
    String updateState;

    @Column
    String label;

    @Column(name = "availaibleAuthentMeans")
    String availaibleAuthentMeans;

    @OneToMany(mappedBy = "issuer", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<SubIssuer> subIssuers;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateState() {
        return updateState;
    }

    public void setUpdateState(String updateState) {
        this.updateState = updateState;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAvailaibleAuthentMeans() {
        return availaibleAuthentMeans;
    }

    public void setAvailaibleAuthentMeans(String availaibleAuthentMeans) {
        this.availaibleAuthentMeans = availaibleAuthentMeans;
    }

    public List<SubIssuer> getSubIssuers() {
        return subIssuers;
    }

    public void setSubIssuers(List<SubIssuer> subIssuers) {
        this.subIssuers = subIssuers;
    }
}
