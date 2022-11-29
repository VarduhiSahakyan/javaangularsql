package com.energizeglobal.sqlgenerator.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


public class SubIssuerPropDTO {


    String acsId;
    String authenticationTimeOut;
    String backupLanguages;
    String code;
    String codeSvi;
    String currencyCode;
    String createdBy;
    String creationDate;
    String description;
    String lastUpdateBy;
    String lastUpdateDate;
    String name;
    String updateState;
    String defaultLanguage;
    String freshnessPeriod;
    String label;
    String manageBackupsCombinedAmounts;
    String manageChoicesCombinedAmounts;
    String personnalDataStorage;
    String resetBackupsIfSuccess;
    String resetChoicesIfSuccess;
    String transactionTimeOut;
    String acs_URL1_VE_MC;
    String acs_URL2_VE_MC;
    String acs_URL1_VE_VISA;
    String acs_URL2_VE_VISA;
    String automaticDeviceSelection;
    String userChoiceAllowed;
    String backupAllowed;
    String defaultDeviceChoice;
    String preferredAuthentMeans;
    String issuerCountry;
    String hubCallMode;
    String fk_id_issuer;
    String maskParams;
    String dateFormat;
    String paChallengePublicUrl;
    String verifyCardStatus;
    String DS2AdditionalInfo;
    String resendOTPThreshold;
    String resendSameOTP;
    String combinedAuthenticationAllowed;
    String displayLanguageSelectPage;
    String trustedBeneficiariesAllowed;
    String authentMeans;
    String fk_id_cryptoConfig;
    String currencyFormat;

    public String getAcsId() {
        return acsId;
    }

    public void setAcsId(String acsId) {
        this.acsId = acsId;
    }

    public String getAuthenticationTimeOut() {
        return authenticationTimeOut;
    }

    public void setAuthenticationTimeOut(String authenticationTimeOut) {
        this.authenticationTimeOut = authenticationTimeOut;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
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

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
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

    public String getFreshnessPeriod() {
        return freshnessPeriod;
    }

    public void setFreshnessPeriod(String freshnessPeriod) {
        this.freshnessPeriod = freshnessPeriod;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getManageBackupsCombinedAmounts() {
        return manageBackupsCombinedAmounts;
    }

    public void setManageBackupsCombinedAmounts(String manageBackupsCombinedAmounts) {
        this.manageBackupsCombinedAmounts = manageBackupsCombinedAmounts;
    }

    public String getManageChoicesCombinedAmounts() {
        return manageChoicesCombinedAmounts;
    }

    public void setManageChoicesCombinedAmounts(String manageChoicesCombinedAmounts) {
        this.manageChoicesCombinedAmounts = manageChoicesCombinedAmounts;
    }

    public String getPersonnalDataStorage() {
        return personnalDataStorage;
    }

    public void setPersonnalDataStorage(String personnalDataStorage) {
        this.personnalDataStorage = personnalDataStorage;
    }

    public String getResetBackupsIfSuccess() {
        return resetBackupsIfSuccess;
    }

    public void setResetBackupsIfSuccess(String resetBackupsIfSuccess) {
        this.resetBackupsIfSuccess = resetBackupsIfSuccess;
    }

    public String getResetChoicesIfSuccess() {
        return resetChoicesIfSuccess;
    }

    public void setResetChoicesIfSuccess(String resetChoicesIfSuccess) {
        this.resetChoicesIfSuccess = resetChoicesIfSuccess;
    }

    public String getTransactionTimeOut() {
        return transactionTimeOut;
    }

    public void setTransactionTimeOut(String transactionTimeOut) {
        this.transactionTimeOut = transactionTimeOut;
    }

    public String getAcs_URL1_VE_MC() {
        return acs_URL1_VE_MC;
    }

    public void setAcs_URL1_VE_MC(String acs_URL1_VE_MC) {
        this.acs_URL1_VE_MC = acs_URL1_VE_MC;
    }

    public String getAcs_URL2_VE_MC() {
        return acs_URL2_VE_MC;
    }

    public void setAcs_URL2_VE_MC(String acs_URL2_VE_MC) {
        this.acs_URL2_VE_MC = acs_URL2_VE_MC;
    }

    public String getAcs_URL1_VE_VISA() {
        return acs_URL1_VE_VISA;
    }

    public void setAcs_URL1_VE_VISA(String acs_URL1_VE_VISA) {
        this.acs_URL1_VE_VISA = acs_URL1_VE_VISA;
    }

    public String getAcs_URL2_VE_VISA() {
        return acs_URL2_VE_VISA;
    }

    public void setAcs_URL2_VE_VISA(String acs_URL2_VE_VISA) {
        this.acs_URL2_VE_VISA = acs_URL2_VE_VISA;
    }

    public String getAutomaticDeviceSelection() {
        return automaticDeviceSelection;
    }

    public void setAutomaticDeviceSelection(String automaticDeviceSelection) {
        this.automaticDeviceSelection = automaticDeviceSelection;
    }

    public String getUserChoiceAllowed() {
        return userChoiceAllowed;
    }

    public void setUserChoiceAllowed(String userChoiceAllowed) {
        this.userChoiceAllowed = userChoiceAllowed;
    }

    public String getBackupAllowed() {
        return backupAllowed;
    }

    public void setBackupAllowed(String backupAllowed) {
        this.backupAllowed = backupAllowed;
    }

    public String getDefaultDeviceChoice() {
        return defaultDeviceChoice;
    }

    public void setDefaultDeviceChoice(String defaultDeviceChoice) {
        this.defaultDeviceChoice = defaultDeviceChoice;
    }

    public String getPreferredAuthentMeans() {
        return preferredAuthentMeans;
    }

    public void setPreferredAuthentMeans(String preferredAuthentMeans) {
        this.preferredAuthentMeans = preferredAuthentMeans;
    }

    public String getIssuerCountry() {
        return issuerCountry;
    }

    public void setIssuerCountry(String issuerCountry) {
        this.issuerCountry = issuerCountry;
    }

    public String getHubCallMode() {
        return hubCallMode;
    }

    public void setHubCallMode(String hubCallMode) {
        this.hubCallMode = hubCallMode;
    }

    public String getFk_id_issuer() {
        return fk_id_issuer;
    }

    public void setFk_id_issuer(String fk_id_issuer) {
        this.fk_id_issuer = fk_id_issuer;
    }

    public String getMaskParams() {
        return maskParams;
    }

    public void setMaskParams(String maskParams) {
        this.maskParams = maskParams;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getPaChallengePublicUrl() {
        return paChallengePublicUrl;
    }

    public void setPaChallengePublicUrl(String paChallengePublicUrl) {
        this.paChallengePublicUrl = paChallengePublicUrl;
    }

    public String getVerifyCardStatus() {
        return verifyCardStatus;
    }

    public void setVerifyCardStatus(String verifyCardStatus) {
        this.verifyCardStatus = verifyCardStatus;
    }

    public String getDS2AdditionalInfo() {
        return DS2AdditionalInfo;
    }

    public void setDS2AdditionalInfo(String DS2AdditionalInfo) {
        this.DS2AdditionalInfo = DS2AdditionalInfo;
    }

    public String getResendOTPThreshold() {
        return resendOTPThreshold;
    }

    public void setResendOTPThreshold(String resendOTPThreshold) {
        this.resendOTPThreshold = resendOTPThreshold;
    }

    public String getResendSameOTP() {
        return resendSameOTP;
    }

    public void setResendSameOTP(String resendSameOTP) {
        this.resendSameOTP = resendSameOTP;
    }

    public String getCombinedAuthenticationAllowed() {
        return combinedAuthenticationAllowed;
    }

    public void setCombinedAuthenticationAllowed(String combinedAuthenticationAllowed) {
        this.combinedAuthenticationAllowed = combinedAuthenticationAllowed;
    }

    public String getDisplayLanguageSelectPage() {
        return displayLanguageSelectPage;
    }

    public void setDisplayLanguageSelectPage(String displayLanguageSelectPage) {
        this.displayLanguageSelectPage = displayLanguageSelectPage;
    }

    public String getTrustedBeneficiariesAllowed() {
        return trustedBeneficiariesAllowed;
    }

    public void setTrustedBeneficiariesAllowed(String trustedBeneficiariesAllowed) {
        this.trustedBeneficiariesAllowed = trustedBeneficiariesAllowed;
    }

    public String getAuthentMeans() {
        return authentMeans;
    }

    public void setAuthentMeans(String authentMeans) {
        this.authentMeans = authentMeans;
    }

    public String getFk_id_cryptoConfig() {
        return fk_id_cryptoConfig;
    }

    public void setFk_id_cryptoConfig(String fk_id_cryptoConfig) {
        this.fk_id_cryptoConfig = fk_id_cryptoConfig;
    }

    public String getCurrencyFormat() {
        return currencyFormat;
    }

    public void setCurrencyFormat(String currencyFormat) {
        this.currencyFormat = currencyFormat;
    }


}
