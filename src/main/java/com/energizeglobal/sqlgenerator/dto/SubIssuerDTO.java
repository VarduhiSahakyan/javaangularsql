package com.energizeglobal.sqlgenerator.dto;

import java.sql.Timestamp;

public class SubIssuerDTO {

    private Long id;
    String acsId;
    Integer authenticationTimeout;
    String backupLanguages;
    String createdBy;
    Timestamp creationDate;
    String description;
    String lastUpdateBy;
    Timestamp lastUpdateDate;
    Integer transactionTimeout;
    String updateState;
    Boolean automaticDeviceSelection;
    Boolean userChoiceAllowed;
    Boolean backupAllowed;
    Boolean defaultDeviceChoice;
    String defaultLanguage;
    String code;
    String codeSvi;
    String currencyCode;
    String name;
    String authentMeans;
    String label;
    Boolean personnalDataStorage;
    Boolean resetBackupsIfSuccess;
    Boolean resetChoicesIfSuccess;
    Boolean manageBackupsCombinedAmounts;
    Boolean manageChoicesCombinedAmounts;
    Boolean hubMaintenanceModeEnabled;
    Long  issuerId;
    Long cryptoConfigId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private IssuerDTO issuer;

    public IssuerDTO getIssuer() {
        return issuer;
    }

    public void setIssuer(IssuerDTO issuer) {
        this.issuer = issuer;
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

    public void setAuthenticationTimeout(Integer authenticationTimeout) { this.authenticationTimeout = authenticationTimeout; }

    public String getBackupLanguages() {
        return backupLanguages;
    }

    public void setBackupLanguages(String backupLanguages) {
        this.backupLanguages = backupLanguages;
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

    public Integer getTransactionTimeout() {
        return transactionTimeout;
    }

    public void setTransactionTimeout(Integer transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    public String getUpdateState() {
        return updateState;
    }

    public void setUpdateState(String updateState) {
        this.updateState = updateState;
    }

    public Boolean getAutomaticDeviceSelection() {
        return automaticDeviceSelection;
    }

    public void setAutomaticDeviceSelection(Boolean automaticDeviceSelection) { this.automaticDeviceSelection = automaticDeviceSelection; }

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

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthentMeans() {
        return authentMeans;
    }

    public void setAuthentMeans(String authentMeans) {
        this.authentMeans = authentMeans;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getPersonnalDataStorage() {
        return personnalDataStorage;
    }

    public void setPersonnalDataStorage(Boolean personnalDataStorage) { this.personnalDataStorage = personnalDataStorage; }

    public Boolean getResetBackupsIfSuccess() {
        return resetBackupsIfSuccess;
    }

    public void setResetBackupsIfSuccess(Boolean resetBackupsIfSuccess) { this.resetBackupsIfSuccess = resetBackupsIfSuccess; }

    public Boolean getResetChoicesIfSuccess() {
        return resetChoicesIfSuccess;
    }

    public void setResetChoicesIfSuccess(Boolean resetChoicesIfSuccess) { this.resetChoicesIfSuccess = resetChoicesIfSuccess; }

    public Boolean getManageBackupsCombinedAmounts() {
        return manageBackupsCombinedAmounts;
    }

    public void setManageBackupsCombinedAmounts(Boolean manageBackupsCombinedAmounts) { this.manageBackupsCombinedAmounts = manageBackupsCombinedAmounts; }

    public Boolean getManageChoicesCombinedAmounts() {
        return manageChoicesCombinedAmounts;
    }

    public void setManageChoicesCombinedAmounts(Boolean manageChoicesCombinedAmounts) { this.manageChoicesCombinedAmounts = manageChoicesCombinedAmounts; }

    public Boolean getHubMaintenanceModeEnabled() {
        return hubMaintenanceModeEnabled;
    }

    public void setHubMaintenanceModeEnabled(Boolean hubMaintenanceModeEnabled) { this.hubMaintenanceModeEnabled = hubMaintenanceModeEnabled; }

    public Long getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Long issuerId) {
        this.issuerId = issuerId;
    }

    public Long getCryptoConfigId() {
        return cryptoConfigId;
    }

    public void setCryptoConfigId(Long cryptoConfigId) {
        this.cryptoConfigId = cryptoConfigId;
    }
}
