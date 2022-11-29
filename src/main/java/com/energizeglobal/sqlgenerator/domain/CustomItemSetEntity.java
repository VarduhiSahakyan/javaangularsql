package com.energizeglobal.sqlgenerator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CustomItemSet")
public class CustomItemSetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
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
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    private EntityData.UpdateState updateState;
    @Column
    private String status;

    @ManyToOne()
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fk_id_subIssuer", referencedColumnName = "id")
    private SubIssuer subIssuer;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customItemSet")
//    @JsonIgnore
//    private List<CustomItemEntity> customItemEntities;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubIssuer getSubIssuer() {
        return subIssuer;
    }

    public void setSubIssuer(SubIssuer subIssuer) {
        this.subIssuer = subIssuer;
    }

//    public List<CustomItemEntity> getCustomItemEntities() {
//        return customItemEntities;
//    }
//
//    public void setCustomItemEntities(List<CustomItemEntity> customItemEntities) {
//        this.customItemEntities = customItemEntities;
//    }
}
