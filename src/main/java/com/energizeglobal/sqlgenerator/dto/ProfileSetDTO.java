package com.energizeglobal.sqlgenerator.dto;

import java.sql.Timestamp;

public class ProfileSetDTO {

    private Long id;
    private String createdBy;
    private Timestamp creationDate;
    private Timestamp LastUpdateDate;
    private String description;
    private String lastUpdateBy;
    private String name;
    private String updateState;
    private Long subIssuerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Timestamp getLastUpdateDate() { return LastUpdateDate; }

    public void setLastUpdateDate(Timestamp lastUpdateDate) { LastUpdateDate = lastUpdateDate; }

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

    public Long getSubIssuerId() {
        return subIssuerId;
    }

    public void setSubIssuerId(Long subIssuerId) {
        this.subIssuerId = subIssuerId;
    }

    @Override
    public String toString() {
        return "ProfileSetDTO{" +
                "createdBy='" + createdBy + '\'' +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                ", lastUpdateBy='" + lastUpdateBy + '\'' +
                ", name='" + name + '\'' +
                ", updateState='" + updateState + '\'' +
                ", subissuer=" + subIssuerId +
                '}';
    }
}
