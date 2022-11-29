package com.energizeglobal.sqlgenerator.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CustomItem")
public class CustomItemEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String DTYPE;

    @Column(nullable = false)
    private String createdBy;
    @CreatedDate
    @Column(name = "creationDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column
    private String description;
    @Column
    private String lastUpdateBy;
    @CreatedDate
    @Column(name = "lastUpdateDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityData.UpdateState updateState;
    @Column(nullable = false)
    private String locale;
    @Column(nullable = false)
    private int ordinal;
    @Column(nullable = false)
    private String pageTypes;
    @Column(nullable = false)
    private String value;
    @Column
    private String backgroundColor;
    @Column
    private String borderStyle;
    @Column
    private String textColor;

    @ManyToOne()
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fk_id_customItemSet", referencedColumnName = "id")
    private CustomItemSetEntity customItemSet;


    public String getDTYPE() {
        return DTYPE;
    }

    public void setDTYPE(String DTYPE) {
        this.DTYPE = DTYPE;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityData.UpdateState getUpdateState() {
        return updateState;
    }

    public void setUpdateState(EntityData.UpdateState updateState) {
        this.updateState = updateState;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getPageTypes() {
        return pageTypes;
    }

    public void setPageTypes(String pageTypes) {
        this.pageTypes = pageTypes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public CustomItemSetEntity getCustomItemSet() {
        return customItemSet;
    }

    public void setCustomItemSet(CustomItemSetEntity customItemSet) {
        this.customItemSet = customItemSet;
    }
}
