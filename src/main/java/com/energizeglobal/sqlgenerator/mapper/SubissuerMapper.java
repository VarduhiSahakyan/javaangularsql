package com.energizeglobal.sqlgenerator.mapper;

import com.energizeglobal.sqlgenerator.domain.CryptoConfigurationEntity;
import com.energizeglobal.sqlgenerator.domain.Issuer;
import com.energizeglobal.sqlgenerator.domain.SubIssuer;
import com.energizeglobal.sqlgenerator.dto.SubIssuerDTO;

public class SubissuerMapper {

    public static SubIssuer dtoToEntity(SubIssuerDTO subIssuerDto) {
        SubIssuer subIssuer = new SubIssuer();
        subIssuer.setAcsId(subIssuerDto.getAcsId());
        subIssuer.setAuthenticationTimeout(subIssuerDto.getAuthenticationTimeout());
        subIssuer.setBackupLanguages(subIssuerDto.getBackupLanguages());
        subIssuer.setCreatedBy(subIssuerDto.getCreatedBy());
        subIssuer.setCreationDate(subIssuerDto.getCreationDate());
        subIssuer.setDescription(subIssuerDto.getDescription());
        subIssuer.setLastUpdateBy(subIssuerDto.getLastUpdateBy());
        subIssuer.setLastUpdateDate(subIssuerDto.getLastUpdateDate());
        subIssuer.setTransactionTimeout(subIssuerDto.getTransactionTimeout());
        subIssuer.setUpdateState(subIssuerDto.getUpdateState());
        subIssuer.setAutomaticDeviceSelection(subIssuerDto.getAutomaticDeviceSelection());
        subIssuer.setUserChoiceAllowed(subIssuerDto.getUserChoiceAllowed());
        subIssuer.setDefaultDeviceChoice(subIssuerDto.getDefaultDeviceChoice());
        subIssuer.setDefaultLanguage(subIssuerDto.getDefaultLanguage());
        subIssuer.setCode(subIssuerDto.getCode());
        subIssuer.setCodeSvi(subIssuerDto.getCodeSvi());
        subIssuer.setCurrencyCode(subIssuerDto.getCurrencyCode());
        subIssuer.setName(subIssuerDto.getName());
        subIssuer.setLabel(subIssuerDto.getLabel());
        subIssuer.setAuthentMeans(subIssuerDto.getAuthentMeans());
        subIssuer.setPersonnalDataStorage(subIssuerDto.getPersonnalDataStorage());
        subIssuer.setResetBackupsIfSuccess(subIssuerDto.getResetBackupsIfSuccess());
        subIssuer.setResetChoicesIfSuccess(subIssuerDto.getResetChoicesIfSuccess());
        subIssuer.setManageBackupsCombinedAmounts(subIssuerDto.getManageBackupsCombinedAmounts());
        subIssuer.setManageChoicesCombinedAmounts(subIssuerDto.getManageChoicesCombinedAmounts());
        subIssuer.setHubMaintenanceModeEnabled(subIssuerDto.getHubMaintenanceModeEnabled());

        return subIssuer;
    }


    public static SubIssuerDTO entityToDto(SubIssuer subIssuer) {
        SubIssuerDTO subIssuerDto = new SubIssuerDTO();
        subIssuerDto.setAcsId(subIssuer.getAcsId());
        subIssuerDto.setAuthenticationTimeout(subIssuer.getAuthenticationTimeout());
        subIssuerDto.setBackupLanguages(subIssuer.getBackupLanguages());
        subIssuerDto.setCreatedBy(subIssuer.getCreatedBy());
        subIssuerDto.setCreationDate(subIssuer.getCreationDate());
        subIssuerDto.setDescription(subIssuer.getDescription());
        subIssuerDto.setLastUpdateBy(subIssuer.getLastUpdateBy());
        subIssuerDto.setLastUpdateDate(subIssuer.getLastUpdateDate());
        subIssuerDto.setTransactionTimeout(subIssuer.getTransactionTimeout());
        subIssuerDto.setUpdateState(subIssuer.getUpdateState());
        subIssuerDto.setAutomaticDeviceSelection(subIssuer.getAutomaticDeviceSelection());
        subIssuerDto.setUserChoiceAllowed(subIssuer.getUserChoiceAllowed());
        subIssuerDto.setBackupAllowed(subIssuer.getBackupAllowed());
        subIssuerDto.setDefaultDeviceChoice(subIssuer.getDefaultDeviceChoice());
        subIssuerDto.setDefaultLanguage(subIssuer.getDefaultLanguage());
        subIssuerDto.setCode(subIssuer.getCode());
        subIssuerDto.setCodeSvi(subIssuer.getCodeSvi());
        subIssuerDto.setCurrencyCode(subIssuer.getCurrencyCode());
        subIssuerDto.setName(subIssuer.getName());
        subIssuerDto.setLabel(subIssuer.getLabel());
        subIssuerDto.setAuthentMeans(subIssuer.getAuthentMeans());
        subIssuerDto.setPersonnalDataStorage(subIssuer.getPersonnalDataStorage());
        subIssuerDto.setResetBackupsIfSuccess(subIssuer.getResetBackupsIfSuccess());
        subIssuerDto.setResetChoicesIfSuccess(subIssuer.getResetChoicesIfSuccess());
        subIssuerDto.setManageBackupsCombinedAmounts(subIssuer.getManageBackupsCombinedAmounts());
        subIssuerDto.setManageChoicesCombinedAmounts(subIssuer.getManageChoicesCombinedAmounts());
        subIssuerDto.setHubMaintenanceModeEnabled(subIssuer.getHubMaintenanceModeEnabled());

        return subIssuerDto;
    }

    public static SubIssuer dtoToEntity(SubIssuerDTO subIssuerDto, Issuer issuer, CryptoConfigurationEntity crypto) {

        SubIssuer subIssuer = new SubIssuer();
        dtoToEntity(subIssuerDto);
        subIssuer.setIssuer(issuer);
        //subIssuer.setCryptoConfigEntity(crypto);

        return subIssuer;
    }
}
