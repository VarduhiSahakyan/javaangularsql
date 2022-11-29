package com.energizeglobal.sqlgenerator.dto;

import com.energizeglobal.sqlgenerator.domain.EntityData;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class BinRangeDto {

    private Long id;
    private String createdBy;
    private Timestamp creationDate;
    private String description;
    private String lastUpdateBy;
    private Timestamp lastUpdateDate;
    private String activeState;
    private String name;
    private EntityData.UpdateState updateState;
    private String immediateActivation;
    private String lowerBound;
    private String panLength;
    private String sharedBinRange;
    private String upperBound;
    private String network;
    private String profileset;
    private String toExport;

    public BinRangeDto() {
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

    public String getActiveState() {
        return activeState;
    }

    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImmediateActivation() {
        return immediateActivation;
    }

    public void setImmediateActivation(String immediateActivation) {
        this.immediateActivation = immediateActivation;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getPanLength() {
        return panLength;
    }

    public void setPanLength(String panLength) {
        this.panLength = panLength;
    }

    public String getSharedBinRange() {
        return sharedBinRange;
    }

    public void setSharedBinRange(String sharedBinRange) {
        this.sharedBinRange = sharedBinRange;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getProfileset() {
        return profileset;
    }

    public void setProfileset(String profileset) {
        this.profileset = profileset;
    }

    public String getToExport() {
        return toExport;
    }

    public void setToExport(String toExport) {
        this.toExport = toExport;
    }

    public EntityData.UpdateState getUpdateState() {
        return updateState;
    }

    public void setUpdateState(EntityData.UpdateState updateState) {
        this.updateState = updateState;
    }
}

