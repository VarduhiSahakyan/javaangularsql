package com.energizeglobal.sqlgenerator.dto;

import java.sql.Timestamp;

public class ProfileDTO {

    private Long id;
    private String createdBy;
    private Timestamp creationDate;
    private String description;
    private String lastUpdateBy;
    private Timestamp lastUpdateDate;
    private String name;
    private String updateState;
    private int maxAttempts;
    private String dataEntryFormat;
    private String dataEntryAllowedPattern;
    private Long authentMeansId;
    private Long subIssuerId;
    private AuthentMeansDTO authentMeans;
    private SubIssuerDTO subIssuer;
    private IssuerDTO issuer;

    public void setAuthentMeansId(Long authentMeansId) {
        this.authentMeansId = authentMeansId;
    }

    public void setSubIssuerId(Long subIssuerId) {
        this.subIssuerId = subIssuerId;
    }

    public AuthentMeansDTO getAuthentMeans() {
        return authentMeans;
    }

    public void setAuthentMeans(AuthentMeansDTO authentMeans) {
        this.authentMeans = authentMeans;
    }

    public SubIssuerDTO getSubIssuer() {
        return subIssuer;
    }

    public void setSubIssuer(SubIssuerDTO subIssuer) {
        this.subIssuer = subIssuer;
    }

    public IssuerDTO getIssuer() {
        return issuer;
    }

    public void setIssuer(IssuerDTO issuer) {
        this.issuer = issuer;
    }

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

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
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

    public Long getAuthentMeansId() { return authentMeansId; }

    public Long getSubIssuerId() { return subIssuerId; }
}
