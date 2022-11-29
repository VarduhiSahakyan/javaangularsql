package com.energizeglobal.sqlgenerator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.security.Timestamp;
import java.util.List;

@Entity

@Table(name = "BinRange")
public class BinRangeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "BinRange_SubIssuer", joinColumns = @JoinColumn(name = "id_binRange", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_subIssuer", referencedColumnName = "id"))
    private List<SubIssuer> subIssuers;

    @ManyToOne()
    @JoinColumn(name = "fk_id_cryptoConfig")
    private CryptoConfigurationEntity cryptoConfigurationEntity;

    @Column(name = "activateState")
    private String activateState;
    @Column(name = "createdBy")
    private String createdBy;
    @Column(name = "creationDate")
    private Timestamp creationDate;
    @Column(name = "description")
    private String description;
    @Column(name = "lastUpdateBy")
    private String lastUpdateBy;
    @Column(name = "lastUpdateDate")
    private Timestamp lastUpdateDate;
    @Column(name = "name")
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityData.UpdateState updateState;
    @Column(name = "lowerBound")
    private Long lowerBound;
    @Column(name = "panLength")
    private int panLength;
    @Column(name = "upperBound")
    private Long upperBound;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivateState() {
        return activateState;
    }

    public void setActivateState(String activateState) {
        this.activateState = activateState;
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

    public EntityData.UpdateState getUpdateState() {
        return updateState;
    }

    public void setUpdateState(EntityData.UpdateState updateState) {
        this.updateState = updateState;
    }

    public Long getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Long lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getPanLength() {
        return panLength;
    }

    public void setPanLength(int panLength) {
        this.panLength = panLength;
    }

    public Long getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Long upperBound) {
        this.upperBound = upperBound;
    }

    public List<SubIssuer> getSubIssuers() {
        return subIssuers;
    }

    public void setSubIssuers(List<SubIssuer> subIssuers) {
        this.subIssuers = subIssuers;
    }

    public CryptoConfigurationEntity getCryptoConfigurationEntity() {
        return cryptoConfigurationEntity;
    }

    public void setCryptoConfigurationEntity(CryptoConfigurationEntity cryptoConfigurationEntity) {
        this.cryptoConfigurationEntity = cryptoConfigurationEntity;
    }


}
