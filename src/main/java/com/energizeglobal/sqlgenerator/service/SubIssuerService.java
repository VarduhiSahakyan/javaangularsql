package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.SubIssuer;
import com.energizeglobal.sqlgenerator.dto.SubIssuerDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.mapper.SubissuerMapper;
import com.energizeglobal.sqlgenerator.repository.SubIssuerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;


@Service
@CacheConfig(cacheNames = "subIssuer")
public class SubIssuerService {

    private final Logger log = LoggerFactory.getLogger(SubIssuerService.class);

    private String FILE_PATH = "src/main/resources/sql_scripts/";
    private String INSERT_FILE_NAME = "subissuer.sql";
    private String path = FILE_PATH + INSERT_FILE_NAME;

    private Boolean dbAction = false;
    private final Mapping subissuerMapper;
    private final SubIssuerRepository subIssuerRepository;
    private final SubissuerRollbackService rollbackService;
    private Timestamp thisMomentTime = new Timestamp(System.currentTimeMillis());

    public SubIssuerService(Mapping subissuerMapper, SubIssuerRepository subIssuerRepository,
                            SubissuerRollbackService rollbackService) {
        this.subissuerMapper = subissuerMapper;
        this.subIssuerRepository = subIssuerRepository;
        this.rollbackService = rollbackService;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<SubIssuerDTO> getAllSubIssuer(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return new PageImpl<>(subissuerMapper.mapList(subIssuerRepository.findAll(paging), SubIssuerDTO.class), paging, subIssuerRepository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<SubIssuerDTO> getAllSubIssuer() {
        return subissuerMapper.mapList(subIssuerRepository.findAll(), SubIssuerDTO.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public SubIssuerDTO findByCode(String code) {
        SubIssuer subIssuer = subIssuerRepository.findByCode(code);
        return SubissuerMapper.entityToDto(subIssuer);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public SubIssuer findSubissuerByCode(String code) {
        return subIssuerRepository.findByCode(code);
    }


    @Transactional
    public String generateInsertSqlScript(SubIssuerDTO dto) {

        SubIssuer subIssuer = SubissuerMapper.dtoToEntity(dto);
        subIssuer.setCreationDate(thisMomentTime);

        String queryType = "INSERT INTO SubIssuer  ( " +
                "acsId, " +
                "authenticationTimeOut, " +
                "backupLanguages, " +
                "createdBy, " +
                "creationDate, " +
                "description, " +
                "lastUpdateBy,\n " +
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
                "currencyCode, \n" +
                "name, " +
//                "freshnessPeriod, " +
                "label, " +
                "authentMeans, " +
                "personnalDataStorage, " +
                "resetBackupsIfSuccess, " +
                "resetChoicesIfSuccess, " +
                "manageBackupsCombinedAmounts, " +
                "manageChoicesCombinedAmounts, " +
                "hubMaintenanceModeEnabled, " +
//                "acs_URL1_VE_MC, " +
//                "acs_URL2_VE_MC, " +
//                "acs_URL1_VE_VISA, " +
//                "acs_URL2_VE_VISA, " +
//                "preferredAuthentMeans, " +
//                "issuerCountry, " +
//                "hubCallMode, " +
                "fk_id_issuer, " +
//                "maskParams, " +
//                "dateFormat, " +
//                "paChallengePublicUrl, " +
//                "verifyCardStatus, " +
//                "3DS2AdditionalInfo, " +
//                "resendOTPThreshold, " +
//                "resendSameOTP, " +
//                "combinedAuthenticationAllowed, " +
//                "displayLanguageSelectPage, " +
//                "trustedBeneficiariesAllowed, " +
//                "currencyFormat, " +
                "fk_id_cryptoConfig) " ;


        String queryValue = " VALUES \n('" +
                subIssuer.getAcsId() + "', " +
                subIssuer.getAuthenticationTimeout() + ", '" +
                subIssuer.getBackupLanguages() + "', '" +
                subIssuer.getCreatedBy() + "', '" +
                thisMomentTime + "', '" +
                subIssuer.getDescription() + "', " +
                subIssuer.getLastUpdateBy() + ",\n" +
                subIssuer.getLastUpdateDate() + ", " +
                subIssuer.getTransactionTimeout() + ", '" +
                subIssuer.getUpdateState() + "', " +
                subIssuer.getAutomaticDeviceSelection() + ", " +
                subIssuer.getUserChoiceAllowed() + ", " +
                subIssuer.getBackupAllowed() + ", " +
                subIssuer.getDefaultDeviceChoice() + ", '" +
                subIssuer.getDefaultLanguage() + "',\n'" +
                subIssuer.getCode() + "', '" +
                subIssuer.getCodeSvi() + "', '" +
                subIssuer.getCurrencyCode() + "', '" +
                subIssuer.getName() + "', '" +
                subIssuer.getLabel() + "', '" +
                subIssuer.getAuthentMeans() + "', " +
                subIssuer.getPersonnalDataStorage() + ",\n" +
                subIssuer.getResetBackupsIfSuccess() + ", " +
                subIssuer.getResetChoicesIfSuccess() + ", " +
                subIssuer.getManageBackupsCombinedAmounts() + ", " +
                subIssuer.getManageChoicesCombinedAmounts() + ", " +
                subIssuer.getHubMaintenanceModeEnabled() + ", " +
                dto.getIssuerId() + ", " +
                dto.getCryptoConfigId() + ");";

        String sqlInsert = queryType + queryValue;
        insertPathGenerator(sqlInsert);

        rollbackService.generateSqlScriptForInsertRollback(subIssuer.getCode());

        if (dbAction)
            subIssuerRepository.save(subIssuer);

        return INSERT_FILE_NAME;
    }

    @Transactional
    public String generateUpdateSqlScript(SubIssuerDTO subIssuerDto) {
        SubIssuer oldSubIssuer = subIssuerRepository.findByCode(subIssuerDto.getCode());
        SubIssuerDTO oldSubIssuerDTO = SubissuerMapper.entityToDto(oldSubIssuer);
        SubIssuer newSubIssuer = SubissuerMapper.dtoToEntity(subIssuerDto);
        newSubIssuer.setId(oldSubIssuer.getId());
        newSubIssuer.setCreationDate(oldSubIssuer.getCreationDate());
        newSubIssuer.setLastUpdateDate(thisMomentTime);

        String dynamicSqlScript = getDynamicSqlScript(subIssuerDto);


        String queryUpdate = "UPDATE SubIssuer SET " +
                "acsId = '" + subIssuerDto.getAcsId() + "', " +
                "authenticationTimeOut = " + subIssuerDto.getAuthenticationTimeout() + ", " +
                "backupLanguages = '" + subIssuerDto.getBackupLanguages() + "', " +
                "createdBy = '" + subIssuerDto.getCreatedBy() + "', \n" +
                "creationDate = '" + subIssuerDto.getCreationDate() + "', " +
                "description = '" + subIssuerDto.getDescription() + "', " +
                "lastUpdateBy = '" + subIssuerDto.getLastUpdateBy() + "', " +
                "lastUpdateDate = '" + thisMomentTime + "', " +
                "transactionTimeOut = " + subIssuerDto.getTransactionTimeout() + ", " +
                "updateState = '" + subIssuerDto.getUpdateState() + "',\n " +
                "automaticDeviceSelection = '" + subIssuerDto.getAutomaticDeviceSelection() + "', " +
                "userChoiceAllowed = '" + subIssuerDto.getUserChoiceAllowed() + "', " +
                "backupAllowed = '" + subIssuerDto.getBackupAllowed() + "',\n " +
                "defaultDeviceChoice = '" + subIssuerDto.getDefaultDeviceChoice() + "', " +
                "defaultLanguage = '" + subIssuerDto.getDefaultLanguage() + "', " +
                "codeSvi = '" + subIssuerDto.getCodeSvi() + "', " +
                "currencyCode = '" + subIssuerDto.getCurrencyCode() + "', " +
                "name = '" + subIssuerDto.getName() + "', " +
                "label = '" + subIssuerDto.getLabel() + "', \n" +
                "authentMeans = '" + subIssuerDto.getAuthentMeans() + "', " +
                "personnalDataStorage = " + subIssuerDto.getPersonnalDataStorage() + ", " +
                "resetBackupsIfSuccess = " + subIssuerDto.getResetBackupsIfSuccess() + ", " +
                "resetChoicesIfSuccess = " + subIssuerDto.getResetChoicesIfSuccess() + ", \n" +
                "manageBackupsCombinedAmounts = " + subIssuerDto.getManageBackupsCombinedAmounts() + ", " +
                "manageChoicesCombinedAmounts = " + subIssuerDto.getManageChoicesCombinedAmounts() + ", " +
                "hubMaintenanceModeEnabled = " + subIssuerDto.getHubMaintenanceModeEnabled() + " " +
                " WHERE code = " + subIssuerDto.getCode() + ";";

        insertPathGenerator(dynamicSqlScript);

        rollbackService.generateSqlScriptForUpdateRollback(oldSubIssuerDTO,subIssuerDto);
        if (dbAction)
            subIssuerRepository.save(newSubIssuer);

        return INSERT_FILE_NAME;
    }

    private String getDynamicSqlScript(SubIssuerDTO subIssuerDto) {
        String  prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE SubIssuer SET ");
        if (subIssuerDto.getAcsId() != null && !subIssuerDto.getAcsId().isEmpty()) {
            sqlBuilder.append(prefix).append("acsId = '").append(subIssuerDto.getAcsId()).append( "' ");
            prefix = ", ";
        }
        if (subIssuerDto.getAuthenticationTimeout() != null ) {
            sqlBuilder.append(prefix).append("authenticationTimeOut = ").append(subIssuerDto.getAuthenticationTimeout()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getBackupLanguages() != null && !subIssuerDto.getBackupLanguages().isEmpty() ) {
            sqlBuilder.append(prefix).append("backupLanguages = '").append(subIssuerDto.getBackupLanguages()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getBackupLanguages() != null && !subIssuerDto.getBackupLanguages().isEmpty() ) {
            sqlBuilder.append(prefix).append("createdBy = '").append(subIssuerDto.getCreatedBy()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getDescription() != null && !subIssuerDto.getDescription().isEmpty() ) {
            sqlBuilder.append(prefix).append("description = '").append(subIssuerDto.getDescription()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getLastUpdateBy() != null && !subIssuerDto.getLastUpdateBy().isEmpty() ) {
            sqlBuilder.append(prefix).append("lastUpdateBy = '").append(subIssuerDto.getLastUpdateBy()).append( "'");
            prefix = ", ";
        }
        if (thisMomentTime != null) {
            sqlBuilder.append(prefix).append("lastUpdateDate = '").append(thisMomentTime).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getTransactionTimeout() != null ) {
            sqlBuilder.append(prefix).append("transactionTimeOut = ").append(subIssuerDto.getTransactionTimeout()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getUpdateState() != null && !subIssuerDto.getUpdateState().isEmpty() ) {
            sqlBuilder.append(prefix).append("updateState = '").append(subIssuerDto.getUpdateState()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getAutomaticDeviceSelection() != null ) {
            sqlBuilder.append(prefix).append("automaticDeviceSelection = ").append(subIssuerDto.getAutomaticDeviceSelection()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getUserChoiceAllowed() != null ) {
            sqlBuilder.append(prefix).append("userChoiceAllowed = ").append(subIssuerDto.getUserChoiceAllowed()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getBackupAllowed() != null && !subIssuerDto.getBackupLanguages().isEmpty() ) {
            sqlBuilder.append(prefix).append("backupAllowed = ").append(subIssuerDto.getBackupAllowed()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getDefaultDeviceChoice() != null) {
            sqlBuilder.append(prefix).append("defaultDeviceChoice = ").append(subIssuerDto.getDefaultDeviceChoice()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getDefaultLanguage() != null && !subIssuerDto.getDefaultLanguage().isEmpty()) {
            sqlBuilder.append(prefix).append("defaultLanguage = '").append(subIssuerDto.getDefaultLanguage()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getCodeSvi() != null && !subIssuerDto.getCodeSvi().isEmpty()) {
            sqlBuilder.append(prefix).append("codeSvi = '").append(subIssuerDto.getCodeSvi()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getCurrencyCode() != null && !subIssuerDto.getCurrencyCode().isEmpty()) {
            sqlBuilder.append(prefix).append("currencyCode = '").append(subIssuerDto.getCurrencyCode()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getName() != null && !subIssuerDto.getName().isEmpty()) {
            sqlBuilder.append(prefix).append("name = '").append(subIssuerDto.getName()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getLabel() != null && !subIssuerDto.getLabel().isEmpty()) {
            sqlBuilder.append(prefix).append("label = '").append(subIssuerDto.getLabel()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getAuthentMeans() != null && !subIssuerDto.getAuthentMeans().isEmpty()) {
            sqlBuilder.append(prefix).append("authentMeans = '").append(subIssuerDto.getAuthentMeans()).append( "'");
            prefix = ", ";
        }
        if (subIssuerDto.getPersonnalDataStorage() != null ) {
            sqlBuilder.append(prefix).append("personnalDataStorage = ").append(subIssuerDto.getPersonnalDataStorage()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getResetChoicesIfSuccess() != null) {
            sqlBuilder.append(prefix).append("resetBackupsIfSuccess = ").append(subIssuerDto.getResetBackupsIfSuccess()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getResetChoicesIfSuccess() != null) {
            sqlBuilder.append(prefix).append("resetChoicesIfSuccess = ").append(subIssuerDto.getResetChoicesIfSuccess()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getManageBackupsCombinedAmounts() != null ) {
            sqlBuilder.append(prefix).append("manageBackupsCombinedAmounts = ").append(subIssuerDto.getManageBackupsCombinedAmounts()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getManageChoicesCombinedAmounts() != null ) {
            sqlBuilder.append(prefix).append("manageChoicesCombinedAmounts = ").append(subIssuerDto.getManageBackupsCombinedAmounts()).append( " ");
            prefix = ", ";
        }
        if (subIssuerDto.getHubMaintenanceModeEnabled() != null ) {
            sqlBuilder.append(prefix).append("hubMaintenanceModeEnabled = ").append(subIssuerDto.getHubMaintenanceModeEnabled()).append( " ");
        }
        sqlBuilder.append(" WHERE code = '").append(subIssuerDto.getCode()).append( "'; ");

        return sqlBuilder.toString();
    }

    @Transactional
    public String generateDeleteSqlScript(String code) {

        SubIssuer oldSubIssuer = findSubissuerByCode(code);

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM SubIssuer WHERE code = '" + code + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";
        insertPathGenerator(deleteQuery);

        rollbackService.generateSqlScriptForDeleteRollback(oldSubIssuer,  oldSubIssuer.getIssuer().getId(), oldSubIssuer.getCryptoConfigEntity().getId());

        if (dbAction)
            subIssuerRepository.deleteByCode(code);

        return INSERT_FILE_NAME;
    }

    private void insertPathGenerator(String sql) {

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
                    bufferedWriter.write(sql);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException: " + e.getMessage());
        }
    }

    public Resource downloadFile(String filename) {
        try {
            Path file = Paths.get(FILE_PATH).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
