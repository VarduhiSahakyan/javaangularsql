package com.energizeglobal.sqlgenerator.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "SubIssuer")
public class SubIssuer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String acsId;

    @Column
    private Integer authenticationTimeout;

    @Column
    private String backupLanguages;

    @Column(name = "code", unique = true)
    private String code;

    @Column
    private String codeSvi;

    @Column
    private String currencyCode;

    @Column
    private String createdBy;

    @Column(nullable = false)
    private Timestamp creationDate;

    @Column
    private String description;

    @Column
    private String lastUpdateBy;

    @Column(nullable = false)
    private Timestamp lastUpdateDate;

    @Column(name = "name")
    private String name;

    @Column
    private String updateState;

    @Column
    private String defaultLanguage;

//    @Column
//    private int freshnessPeriod;

    @Column(nullable = false)
    private String label;

    @Column(columnDefinition = "BIT", length = 1)
    private Boolean manageBackupsCombinedAmounts;

    @Column(columnDefinition = "BIT", length = 1)
    private Boolean manageChoicesCombinedAmounts;

    @Column(columnDefinition = "BIT", length = 1)
    private Boolean personnalDataStorage;

    @Column(columnDefinition = "BIT", length = 1)
    private Boolean resetBackupsIfSuccess;

    @Column(columnDefinition = "BIT", length = 1)
    private Boolean resetChoicesIfSuccess;

    @Column
    private Integer transactionTimeout;

    @ManyToOne
    @JoinColumn(name = "fk_id_issuer")
    private Issuer issuer;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subIssuer")
//    @JsonIgnore
//    private List<CustomItemSetEntity> customItemSetEntities;

    @Column
    private Boolean automaticDeviceSelection;

    @Column
    private Boolean userChoiceAllowed;

    @Column
    private Boolean backupAllowed;

    @Column
    private Boolean defaultDeviceChoice;

    @ManyToOne
    @JoinColumn(name = "fk_id_cryptoConfig")
    private CryptoConfigurationEntity cryptoConfigEntity;

    @Column(name = "authentMeans")
    private String authentMeans;

    @Column(columnDefinition = "BIT", length = 1)
    private Boolean hubMaintenanceModeEnabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcsId() {
        return acsId;
    }

    public void setAcsId(String acsId) {
        this.acsId = acsId;
    }

    public Integer getAuthenticationTimeout() {
        return authenticationTimeout;
    }

    public void setAuthenticationTimeout(Integer authenticationTimeout) {
        this.authenticationTimeout = authenticationTimeout;
    }

    public String getBackupLanguages() {
        return backupLanguages;
    }

    public void setBackupLanguages(String backupLanguages) {
        this.backupLanguages = backupLanguages;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeSvi() {
        return codeSvi;
    }

    public void setCodeSvi(String codeSvi) {
        this.codeSvi = codeSvi;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getManageBackupsCombinedAmounts() {
        return manageBackupsCombinedAmounts;
    }

    public void setManageBackupsCombinedAmounts(Boolean manageBackupsCombinedAmounts) {
        this.manageBackupsCombinedAmounts = manageBackupsCombinedAmounts;
    }

    public Boolean getManageChoicesCombinedAmounts() {
        return manageChoicesCombinedAmounts;
    }

    public void setManageChoicesCombinedAmounts(Boolean manageChoicesCombinedAmounts) {
        this.manageChoicesCombinedAmounts = manageChoicesCombinedAmounts;
    }

    public Boolean getPersonnalDataStorage() {
        return personnalDataStorage;
    }

    public void setPersonnalDataStorage(Boolean personnalDataStorage) {
        this.personnalDataStorage = personnalDataStorage;
    }

    public Boolean getResetBackupsIfSuccess() {
        return resetBackupsIfSuccess;
    }

    public void setResetBackupsIfSuccess(Boolean resetBackupsIfSuccess) {
        this.resetBackupsIfSuccess = resetBackupsIfSuccess;
    }

    public Boolean getResetChoicesIfSuccess() {
        return resetChoicesIfSuccess;
    }

    public void setResetChoicesIfSuccess(Boolean resetChoicesIfSuccess) {
        this.resetChoicesIfSuccess = resetChoicesIfSuccess;
    }

    public Integer getTransactionTimeout() {
        return transactionTimeout;
    }

    public void setTransactionTimeout(Integer transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public Boolean getAutomaticDeviceSelection() {
        return automaticDeviceSelection;
    }

    public void setAutomaticDeviceSelection(Boolean automaticDeviceSelection) {
        this.automaticDeviceSelection = automaticDeviceSelection;
    }

    public Boolean getUserChoiceAllowed() {
        return userChoiceAllowed;
    }

    public void setUserChoiceAllowed(Boolean userChoiceAllowed) {
        this.userChoiceAllowed = userChoiceAllowed;
    }

    public Boolean getBackupAllowed() {
        return backupAllowed;
    }

    public void setBackupAllowed(Boolean backupAllowed) {
        this.backupAllowed = backupAllowed;
    }

    public Boolean getDefaultDeviceChoice() {
        return defaultDeviceChoice;
    }

    public void setDefaultDeviceChoice(Boolean defaultDeviceChoice) {
        this.defaultDeviceChoice = defaultDeviceChoice;
    }

    public CryptoConfigurationEntity getCryptoConfigEntity() {
        return cryptoConfigEntity;
    }

    public void setCryptoConfigEntity(CryptoConfigurationEntity cryptoConfigEntity) {
        this.cryptoConfigEntity = cryptoConfigEntity;
    }

    public String getAuthentMeans() {
        return authentMeans;
    }

    public void setAuthentMeans(String authentMeans) {
        this.authentMeans = authentMeans;
    }

    public Boolean getHubMaintenanceModeEnabled() {
        return hubMaintenanceModeEnabled;
    }

    public void setHubMaintenanceModeEnabled(Boolean hubMaintenanceModeEnabled) {
        this.hubMaintenanceModeEnabled = hubMaintenanceModeEnabled;
    }

//    public List<CustomItemSetEntity> getCustomItemSetEntities() {
//        return customItemSetEntities;
//    }
//
//    public void setCustomItemSetEntities(List<CustomItemSetEntity> customItemSetEntities) {
//        this.customItemSetEntities = customItemSetEntities;
//    }
}
