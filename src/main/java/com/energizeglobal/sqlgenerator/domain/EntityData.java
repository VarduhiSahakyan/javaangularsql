package com.energizeglobal.sqlgenerator.domain;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class EntityData {

    @Column
    private String name;

    @Column
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UpdateState updateState;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date lastUpdateDate;

    @Column
    private String lastUpdateBy;

    public enum UpdateState {
        PUSHED_TO_CONFIG,
        WAITING_FOR_PUSH,
        WAITING_VALIDATION
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate != null ? (Date) creationDate.clone() : null;
    }

    public void setCreationDate(Date creationDate) {
        if (creationDate != null) {
            this.creationDate = (Date) creationDate.clone();
        } else {
            this.creationDate = null;
        }
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public UpdateState getUpdateState() {
        return updateState;
    }

    public void setUpdateState(UpdateState updateState) {
        this.updateState = updateState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate != null ? (Date) lastUpdateDate.clone() : null;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        if (lastUpdateDate != null) {
            this.lastUpdateDate = (Date) lastUpdateDate.clone();
        } else {
            this.lastUpdateDate = null;
        }
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}