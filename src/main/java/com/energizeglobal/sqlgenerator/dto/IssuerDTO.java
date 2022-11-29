package com.energizeglobal.sqlgenerator.dto;

import java.sql.Timestamp;

public class IssuerDTO {

    private Long id;
    private String code;
    private String name;
    private String label;
    private String description;
    private String createdBy;
    private Timestamp creationDate;
    private String updateState;
    private String lastUpdateBy;
    private String lastUpdateDate;
    private String availaibleAuthentMeans;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdateState() {
        return updateState;
    }

    public void setUpdateState(String updateState) {
        this.updateState = updateState;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getAvailaibleAuthentMeans() { return availaibleAuthentMeans; }

    public void setAvailaibleAuthentMeans(String availaibleAuthentMeans) { this.availaibleAuthentMeans = availaibleAuthentMeans; }
}
