package com.energizeglobal.sqlgenerator.dto;

public class ProfilePropertiesDTO {

    String id;
    String createdBy;
    String creationDate;
    String description;
    String name;
    String maxAttempts;
    String dataEntryFormat;
    String dataEntryAllowedPattern;

    public ProfilePropertiesDTO() {
    }

    public ProfilePropertiesDTO(String id, String createdBy, String creationDate, String description, String name, String maxAttempts, String dataEntryFormat, String dataEntryAllowedPattern) {
        this.id = id;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.description = description;
        this.name = name;
        this.maxAttempts = maxAttempts;
        this.dataEntryFormat = dataEntryFormat;
        this.dataEntryAllowedPattern = dataEntryAllowedPattern;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(String maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getDataEntryFormat() {
        return dataEntryFormat;
    }

    public void setDataEntryFormat(String dataEntryFormat) {
        this.dataEntryFormat = dataEntryFormat;
    }

    public String getDataEntryAllowedPattern() {
        return dataEntryAllowedPattern;
    }

    public void setDataEntryAllowedPattern(String dataEntryAllowedPattern) {
        this.dataEntryAllowedPattern = dataEntryAllowedPattern;
    }
}
