package com.energizeglobal.sqlgenerator.domain;



import javax.persistence.*;
import java.util.Date;

@Entity

@Table(name = "MerchantPivotList")
public class MerchantPivotListEntity {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date creationDate;

    @Column
    private String lastUpdateBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date lastUpdateDate;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private boolean display;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private boolean expertMode;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private boolean forceAuthent;

//    @ManyToOne
//    @JoinColumn(name = "fk_id_issuer", referencedColumnName = "id")
//    private Issuer issuer;

//    @ManyToOne
//    @JoinColumn(name = "fk_id_subIssuer", referencedColumnName = "id")
//    private SubIssuer subIssuer;


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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public boolean isForceAuthent() {
        return forceAuthent;
    }

    public void setForceAuthent(boolean forceAuthent) {
        this.forceAuthent = forceAuthent;
    }
}
