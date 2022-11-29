package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.SubIssuer;
import com.energizeglobal.sqlgenerator.dto.SubIssuerDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;

@Service
public class SubissuerRollbackService {

    String FILE_PATH = "src/main/resources/sql_scripts/";
    String ROLLBACK_FILE_NAME = "subissuer_rollback.sql";
    String path = FILE_PATH + ROLLBACK_FILE_NAME;


    public String generateSqlScriptForDeleteRollback(SubIssuer subIssuer, Long issuerId, Long cryptoId) {


        String queryType = "INSERT INTO SubIssuer  ( " +
                "acsId, " +
                "authenticationTimeOut, " +
                "backupLanguages, " +
                "createdBy, " +
                "creationDate, " +
                "description, " +
                "lastUpdateBy,\n" +
                "lastUpdateDate, " +
                "transactionTimeOut, " +
                "updateState, " +
                "automaticDeviceSelection, " +
                "userChoiceAllowed, " +
                "backupAllowed, " +
                "defaultDeviceChoice, " +
                "defaultLanguage, " +
                "code, " +
                "codeSvi, " +
                "currencyCode, " +
                "name, " +
                "label, " +
                "authentMeans, " +
                "personnalDataStorage, " +
                "resetBackupsIfSuccess," +
                "resetChoicesIfSuccess, " +
                "manageBackupsCombinedAmounts, " +
                "manageChoicesCombinedAmounts, " +
                "hubMaintenanceModeEnabled, " +
                "fk_id_issuer, " +
                "fk_id_cryptoConfig )";

        String queryValue = "  VALUES ('" +
                subIssuer.getAcsId() + "', " +
                subIssuer.getAuthenticationTimeout() + ", '" +
                subIssuer.getBackupLanguages() + "', '" +
                subIssuer.getCreatedBy() + "', '" +
                subIssuer.getCreationDate() + "', '" +
                subIssuer.getDescription() + "', '" +
                subIssuer.getLastUpdateBy() + "',\n'" +
                subIssuer.getLastUpdateDate() + "', " +
                subIssuer.getTransactionTimeout() + ", '" +
                subIssuer.getUpdateState() + "', " +
                subIssuer.getAutomaticDeviceSelection() + ", " +
                subIssuer.getUserChoiceAllowed() + ", " +
                subIssuer.getBackupAllowed() + ", " +
                subIssuer.getDefaultDeviceChoice() + ", '" +
                subIssuer.getDefaultLanguage() + "', '" +
                subIssuer.getCode() + "', '" +
                subIssuer.getCodeSvi() + "', '" +
                subIssuer.getCurrencyCode() + "', '" +
                subIssuer.getName() + "', '" +
                subIssuer.getLabel() + "', '" +
                subIssuer.getAuthentMeans() + "', " +
                subIssuer.getPersonnalDataStorage() + ", " +
                subIssuer.getResetBackupsIfSuccess() + ", " +
                subIssuer.getResetChoicesIfSuccess() + ", " +
                subIssuer.getManageBackupsCombinedAmounts() + ", " +
                subIssuer.getManageChoicesCombinedAmounts() + ", " +
                subIssuer.getHubMaintenanceModeEnabled() + ", " +
                issuerId + ", " +
                cryptoId + ");";

        String sqlInsert = queryType + queryValue;

        pathGenerator(sqlInsert);

        return ROLLBACK_FILE_NAME;
    }

    public String generateSqlScriptForUpdateRollback(SubIssuerDTO oldSubIssuer, SubIssuerDTO newSubIssuer) {


        String dynamicSqlScript = getDynamicSqlScript(oldSubIssuer, newSubIssuer);


        String queryUpdate = "UPDATE SubIssuer SET " +
                "acsId = '" + oldSubIssuer.getAcsId() + "', " +
                "authenticationTimeOut = " + oldSubIssuer.getAuthenticationTimeout() + ", " +
                "backupLanguages = '" + oldSubIssuer.getBackupLanguages() + "', " +
                "createdBy = '" + oldSubIssuer.getCreatedBy() + "', \n" +
                "creationDate = '" + oldSubIssuer.getCreationDate() + "', " +
                "description = '" + oldSubIssuer.getDescription() + "', " +
                "lastUpdateBy = '" + oldSubIssuer.getLastUpdateBy() + "',\n " +
                "lastUpdateDate = '" + oldSubIssuer.getLastUpdateDate() + "', " +
                "transactionTimeOut = " + oldSubIssuer.getTransactionTimeout() + ", " +
                "updateState = '" + oldSubIssuer.getUpdateState() + "',\n " +
                "automaticDeviceSelection = '" + oldSubIssuer.getAutomaticDeviceSelection() + "', " +
                "userChoiceAllowed = '" + oldSubIssuer.getUserChoiceAllowed() + "', " +
                "backupAllowed = '" + oldSubIssuer.getBackupAllowed() + "',\n " +
                "defaultDeviceChoice = '" + oldSubIssuer.getDefaultDeviceChoice() + "', " +
                "defaultLanguage = '" + oldSubIssuer.getDefaultLanguage() + "', " +
                "codeSvi = '" + oldSubIssuer.getCodeSvi() + "', " +
                "currencyCode = '" + oldSubIssuer.getCurrencyCode() + "',\n " +
                "name = '" + oldSubIssuer.getName() + "', " +
                "label = '" + oldSubIssuer.getLabel() + "', " +
                "authentMeans = '" + oldSubIssuer.getAuthentMeans() + "',\n " +
                "personnalDataStorage = " + oldSubIssuer.getPersonnalDataStorage() + ", " +
                "resetBackupsIfSuccess = " + oldSubIssuer.getResetBackupsIfSuccess() + ", " +
                "resetChoicesIfSuccess = " + oldSubIssuer.getResetChoicesIfSuccess() + ",\n " +
                "manageBackupsCombinedAmounts = " + oldSubIssuer.getManageBackupsCombinedAmounts() + ", " +
                "manageChoicesCombinedAmounts = " + oldSubIssuer.getManageChoicesCombinedAmounts() + ", " +
                "hubMaintenanceModeEnabled = " + oldSubIssuer.getHubMaintenanceModeEnabled() + " " +
                " WHERE code = " + oldSubIssuer.getCode() + ";";

        pathGenerator(dynamicSqlScript);

        return ROLLBACK_FILE_NAME;
    }

    private String getDynamicSqlScript(SubIssuerDTO oldSubIssuer, SubIssuerDTO newSubIssuer) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE SubIssuer SET ");
        if (newSubIssuer.getAcsId() != null && !newSubIssuer.getAcsId().isEmpty()) {
            if (oldSubIssuer.getAcsId() == null) {
                sqlBuilder.append(prefix).append("acsId = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("acsId = '").append(oldSubIssuer.getAcsId()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getAuthenticationTimeout() != null) {
            if (oldSubIssuer.getAuthenticationTimeout() == null) {
                sqlBuilder.append(prefix).append("authenticationTimeOut = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("authenticationTimeOut = ").append(oldSubIssuer.getAuthenticationTimeout()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getBackupLanguages() != null && !newSubIssuer.getBackupLanguages().isEmpty()) {
            if (oldSubIssuer.getBackupLanguages() == null) {
                sqlBuilder.append(prefix).append("backupLanguages = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("backupLanguages = '").append(oldSubIssuer.getBackupLanguages()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getCreatedBy() != null && !newSubIssuer.getCreatedBy().isEmpty()) {
            if (oldSubIssuer.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy = '").append(oldSubIssuer.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getDescription() != null && !newSubIssuer.getDescription().isEmpty()) {
            if (oldSubIssuer.getDescription() == null) {
                sqlBuilder.append(prefix).append("description = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description = '").append(oldSubIssuer.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getLastUpdateBy() != null && !newSubIssuer.getLastUpdateBy().isEmpty()) {
            if (oldSubIssuer.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy = '").append(oldSubIssuer.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getLastUpdateDate() != null) {
            if (oldSubIssuer.getLastUpdateDate() == null) {
                sqlBuilder.append(prefix).append("lastUpdateDate = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateDate = '").append(oldSubIssuer.getLastUpdateDate()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getTransactionTimeout() != null) {
            if (oldSubIssuer.getTransactionTimeout() == null) {
                sqlBuilder.append(prefix).append("transactionTimeOut = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("transactionTimeOut = ").append(oldSubIssuer.getTransactionTimeout()).append(" ");
            }
            prefix = ", ";
        }

        if (newSubIssuer.getUpdateState() != null && !newSubIssuer.getUpdateState().isEmpty()) {
            if (oldSubIssuer.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("updateState = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState = '").append(oldSubIssuer.getUpdateState()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getAutomaticDeviceSelection() != null) {
            if (oldSubIssuer.getAutomaticDeviceSelection() == null) {
                sqlBuilder.append(prefix).append("automaticDeviceSelection = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("automaticDeviceSelection = ").append(oldSubIssuer.getAutomaticDeviceSelection()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getUserChoiceAllowed() != null) {
            if (oldSubIssuer.getUserChoiceAllowed() == null) {
                sqlBuilder.append(prefix).append("userChoiceAllowed = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("userChoiceAllowed = ").append(oldSubIssuer.getUserChoiceAllowed()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getBackupAllowed() != null && !newSubIssuer.getBackupLanguages().isEmpty()) {
            if (oldSubIssuer.getBackupAllowed() == null) {
                sqlBuilder.append(prefix).append("backupAllowed = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("backupAllowed = ").append(oldSubIssuer.getBackupAllowed()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getDefaultDeviceChoice() != null) {
            if (oldSubIssuer.getDefaultDeviceChoice() == null) {
                sqlBuilder.append(prefix).append("defaultDeviceChoice = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("defaultDeviceChoice = ").append(oldSubIssuer.getDefaultDeviceChoice()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getDefaultLanguage() != null && !newSubIssuer.getDefaultLanguage().isEmpty()) {
            if (oldSubIssuer.getDefaultLanguage() == null) {
                sqlBuilder.append(prefix).append("defaultLanguage = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("defaultLanguage = '").append(oldSubIssuer.getDefaultLanguage()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getCodeSvi() != null && !newSubIssuer.getCodeSvi().isEmpty()) {
            if (oldSubIssuer.getCodeSvi() == null) {
                sqlBuilder.append(prefix).append("codeSvi = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("codeSvi = '").append(oldSubIssuer.getCodeSvi()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getCurrencyCode() != null && !newSubIssuer.getCurrencyCode().isEmpty()) {
            if (oldSubIssuer.getCurrencyCode() == null) {
                sqlBuilder.append(prefix).append("currencyCode = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("currencyCode = '").append(oldSubIssuer.getCurrencyCode()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getName() != null && !newSubIssuer.getName().isEmpty()) {
            if (oldSubIssuer.getName() == null) {
                sqlBuilder.append(prefix).append("name = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name = '").append(oldSubIssuer.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getLabel() != null && !newSubIssuer.getLabel().isEmpty()) {
            if (oldSubIssuer.getLabel() == null) {
                sqlBuilder.append(prefix).append("label = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("label = '").append(oldSubIssuer.getLabel()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getAuthentMeans() != null && !newSubIssuer.getAuthentMeans().isEmpty()) {
            if (oldSubIssuer.getAuthentMeans() == null) {
                sqlBuilder.append(prefix).append("authentMeans = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("authentMeans ='").append(oldSubIssuer.getAuthentMeans()).append("'");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getPersonnalDataStorage() != null) {
            if (oldSubIssuer.getPersonnalDataStorage() == null) {
                sqlBuilder.append(prefix).append("personnalDataStorage = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("personnalDataStorage = ").append(oldSubIssuer.getPersonnalDataStorage()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getResetChoicesIfSuccess() != null) {
            if (oldSubIssuer.getResetChoicesIfSuccess() == null) {
                sqlBuilder.append(prefix).append("resetBackupsIfSuccess = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("resetBackupsIfSuccess = ").append(oldSubIssuer.getResetBackupsIfSuccess()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getResetChoicesIfSuccess() != null) {
            if (oldSubIssuer.getResetChoicesIfSuccess() == null) {
                sqlBuilder.append(prefix).append("resetChoicesIfSuccess = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("resetChoicesIfSuccess = ").append(oldSubIssuer.getResetChoicesIfSuccess()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getManageBackupsCombinedAmounts() != null) {
            if (oldSubIssuer.getManageBackupsCombinedAmounts() == null) {
                sqlBuilder.append(prefix).append("manageBackupsCombinedAmounts = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("manageBackupsCombinedAmounts = ").append(oldSubIssuer.getManageBackupsCombinedAmounts()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getManageChoicesCombinedAmounts() != null) {
            if (oldSubIssuer.getManageBackupsCombinedAmounts() == null) {
                sqlBuilder.append(prefix).append("manageChoicesCombinedAmounts = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("manageChoicesCombinedAmounts = ").append(oldSubIssuer.getManageBackupsCombinedAmounts()).append(" ");
            }
            prefix = ", ";
        }
        if (newSubIssuer.getHubMaintenanceModeEnabled() != null) {
            if (oldSubIssuer.getHubMaintenanceModeEnabled() == null) {
                sqlBuilder.append(prefix).append("hubMaintenanceModeEnabled = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("hubMaintenanceModeEnabled = ").append(oldSubIssuer.getHubMaintenanceModeEnabled()).append(" ");
            }
        }
        sqlBuilder.append(" WHERE code = '").append(oldSubIssuer.getCode()).append("'; ");
        return sqlBuilder.toString();
    }

    public String generateSqlScriptForInsertRollback(String code) {

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM SubIssuer WHERE code = '" + code + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        pathGenerator(deleteQuery);

        return ROLLBACK_FILE_NAME;
    }

    private void pathGenerator(String sql) {

        String dbName = "USE `U5G_ACS_BO`; \n";
        Path newFilePath = Paths.get(path);
        try {
            if (Files.exists(newFilePath)) {
                sql = System.getProperty("line.separator") + sql;
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(newFilePath, UTF_8, APPEND)) {
                    bufferedWriter.write(sql);
                }
            } else {
                Path fileDirectory = Paths.get(FILE_PATH);
                Files.createDirectories(fileDirectory);
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(newFilePath, UTF_8)) {
                    bufferedWriter.write(dbName + sql);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
