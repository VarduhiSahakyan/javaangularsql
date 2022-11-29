package com.energizeglobal.sqlgenerator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class RuleCondition {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String createdBy;

    @Column(name = "creationDate", nullable = false)
    private Timestamp creationDate;

    @Column
    private String description;

    @Column
    private String lastUpdateBy;

    @CreatedDate
    @Column
    private Timestamp lastUpdateDate;

    @Column
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityData.UpdateState updateState;

    @ManyToOne
    @JoinColumn(name = "fk_id_rule", referencedColumnName = "id")
    private RuleEntity rule;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCreatedBy() { return createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLastUpdateBy() { return lastUpdateBy; }

    public void setLastUpdateBy(String lastUpdateBy) { this.lastUpdateBy = lastUpdateBy; }

    public Timestamp getLastUpdateDate() {

        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public EntityData.UpdateState getUpdateState() { return updateState; }

    public void setUpdateState(EntityData.UpdateState updateState) { this.updateState = updateState; }

    public RuleEntity getRule() { return rule; }

    public void setRule(RuleEntity rule) { this.rule = rule; }
}
