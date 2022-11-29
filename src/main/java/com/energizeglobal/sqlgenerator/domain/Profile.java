package com.energizeglobal.sqlgenerator.domain;

//import com.energizeglobal.sqlgenerator.enums.UpdateState;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "createdBy", nullable = false)
    private String createdBy;

    @Column(name = "creationDate", nullable = false)
    private Timestamp creationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "lastUpdateBy")
    private String lastUpdateBy;

    @Column(name = "lastUpdateDate")
    private Timestamp lastUpdateDate;

    @Column(name = "name")
    private String name;

    @Column(name = "updateState", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityData.UpdateState updateState;

    @Column(name = "maxAttempts")
    private Integer maxAttempts;

    @Column(name = "dataEntryFormat")
    private String dataEntryFormat;

    @Column(name = "dataEntryAllowedPattern")
    private String dataEntryAllowedPattern;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fk_id_authentMeans", referencedColumnName = "id")
    private AuthentMeansEntity authentMeans;

    @ManyToOne()
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fk_id_subIssuer", referencedColumnName = "id")
    private SubIssuer subIssuer;

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

    public EntityData.UpdateState getUpdateState() { return updateState; }

    public void setUpdateState(EntityData.UpdateState updateState) { this.updateState = updateState; }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
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

    public AuthentMeansEntity getAuthentMeans() {
        return authentMeans;
    }

    public void setAuthentMeans(AuthentMeansEntity authentMeans) {
        this.authentMeans = authentMeans;
    }

    public SubIssuer getSubIssuer() {
        return subIssuer;
    }

    public void setSubIssuer(SubIssuer subIssuer) {
        this.subIssuer = subIssuer;
    }
}
