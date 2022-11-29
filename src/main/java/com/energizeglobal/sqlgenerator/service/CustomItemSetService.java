package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.CustomItemSetEntity;
import com.energizeglobal.sqlgenerator.dto.CustomItemSetDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.repository.CustomItemSetRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "customItemSet")
public class CustomItemSetService {

    static boolean activeDB = false;

    private final String INSERT_SQL_FILE_NAME = "binRange.sql";

    private final String ROLLBACK_SQL_FILE_NAME = "binRange_rollback.sql";

    private final Mapping mappingCryptoConfig;

    private final CustomItemSetRepository customItemSetRepository;

    private final GenerateSqlScriptService generateSqlScriptService;

    private final DownloadFileService downloadFileService;

    public CustomItemSetService(Mapping mappingCryptoConfig,
                           CustomItemSetRepository customItemSetRepository,
                           GenerateSqlScriptService generateSqlScriptService,
                           DownloadFileService downloadFileService) {
        this.mappingCryptoConfig = mappingCryptoConfig;
        this.customItemSetRepository = customItemSetRepository;
        this.generateSqlScriptService = generateSqlScriptService;
        this.downloadFileService = downloadFileService;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<CustomItemSetDTO> findAllCustomItemSets() {

        List<CustomItemSetEntity> customItemSetEntities = customItemSetRepository.findAll();

        return mappingCryptoConfig.mapList(customItemSetEntities, CustomItemSetDTO.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public CustomItemSetDTO getById(Long id) {

        CustomItemSetEntity customItemSetEntity = customItemSetRepository.getById(id);

        return mappingCryptoConfig.convertToDto(customItemSetEntity, CustomItemSetDTO.class);
    }

    @Transactional
    public String deleteById(Long id) {

        CustomItemSetEntity customItemSetEntity = customItemSetRepository.getById(id);

        String deleteQuery =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM CryptoConfig WHERE id = " + id + ";\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";

        String rollbackQuery = String.format("INSERT INTO `CustomItemSet` (`createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`,\n" +
                        "\t\t\t\t`updateState`, `status`, `versionNumber`, `validationDate`, `deploymentDate`,\n" +
                        "\t\t\t\t`fk_id_subIssuer`,\n" +
                        "\t\t\t\t`) VALUES('%s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s' , %s', '%s');",
                customItemSetEntity.getCreatedBy(), customItemSetEntity.getCreationDate(),customItemSetEntity.getDescription(),customItemSetEntity.getLastUpdateBy(),
                customItemSetEntity.getLastUpdateDate(),customItemSetEntity.getName(),
                customItemSetEntity.getUpdateState(),customItemSetEntity.getStatus(),1,"NULL","NULL", customItemSetEntity.getSubIssuer().getId());


        generateSqlScriptService.insertSqlScript(deleteQuery, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(rollbackQuery, ROLLBACK_SQL_FILE_NAME);
        if (activeDB)
            customItemSetRepository.deleteById(id);
        return INSERT_SQL_FILE_NAME;
    }

    @Transactional
    public String saveCustomItemSet(CustomItemSetDTO customItemSetDTO) {

        CustomItemSetEntity customItemSetEntity = mappingCryptoConfig.convertToEntity(customItemSetDTO, CustomItemSetEntity.class);

        String queryType = String.format("INSERT INTO `CustomItemSet` (`createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`,\n" +
                        "\t\t\t\t`updateState`, `status`, `versionNumber`, `validationDate`, `deploymentDate`,\n" +
                        "\t\t\t\t`fk_id_subIssuer`,\n" +
                        "\t\t\t\t`) VALUES('%s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s' , %s', '%s');",
                customItemSetDTO.getCreatedBy(), customItemSetDTO.getCreationDate(),customItemSetDTO.getDescription(),customItemSetDTO.getLastUpdateBy(),
                customItemSetDTO.getLastUpdateDate(),customItemSetDTO.getName(),
                customItemSetDTO.getUpdateState(),customItemSetDTO.getStatus(),1,"NULL","NULL", customItemSetDTO.getSubIssuer().getId());

        String queryTypeRollback =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM BinRange WHERE description = '" + customItemSetDTO.getDescription() + "';\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";


        customItemSetRepository.save(customItemSetEntity);

        generateSqlScriptService.insertSqlScript(queryType, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(queryTypeRollback, ROLLBACK_SQL_FILE_NAME);



        String deleteQuery =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM BinRange WHERE id = " + customItemSetDTO.getId() + ";\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";

        if (activeDB)
            generateSqlScriptService.insertSqlScript(deleteQuery, ROLLBACK_SQL_FILE_NAME);

        return INSERT_SQL_FILE_NAME;
    }


    @Transactional
    public String updateCustomItemSet(CustomItemSetDTO customItemSetDTO) {

        CustomItemSetEntity customItemSetEntity = mappingCryptoConfig.convertToEntity(customItemSetDTO, CustomItemSetEntity.class);
        customItemSetEntity.setId(customItemSetDTO.getId());
        CustomItemSetEntity rollbackItemSetEntity = customItemSetRepository.getById(customItemSetDTO.getId());


        String updateSql = generateDynamicSqlUpdateScript(customItemSetDTO,customItemSetDTO);





        String updateRollbackSql = generateDynamicSqlUpdateScript(mappingCryptoConfig.convertToDto(customItemSetEntity,CustomItemSetDTO.class),customItemSetDTO);

        generateSqlScriptService.insertSqlScript(updateSql, INSERT_SQL_FILE_NAME);

        generateSqlScriptService.insertSqlScript(updateRollbackSql, ROLLBACK_SQL_FILE_NAME);

        if (activeDB)
            customItemSetRepository.save(customItemSetEntity);

        return INSERT_SQL_FILE_NAME;
    }

    private String generateDynamicSqlUpdateScript(CustomItemSetDTO customItemSetDTO,CustomItemSetDTO newCustomItemSetDTO) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();
        String subIssuerId = "SET @subissuerId = (SELECT id from SubIssuer WHERE code = " + newCustomItemSetDTO.getSubIssuer().getCode() + "); \n\n";




        sqlBuilder.append(subIssuerId);
        sqlBuilder.append("UPDATE CustomItemSet SET ");
        if (newCustomItemSetDTO.getCreatedBy() != null && ! newCustomItemSetDTO.getCreatedBy().isEmpty()) {
            if(customItemSetDTO.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy ='").append(customItemSetDTO.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemSetDTO.getCreationDate() != null ) {
            if(customItemSetDTO.getCreationDate() == null) {
                sqlBuilder.append(prefix).append("creationDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate ='").append(customItemSetDTO.getCreationDate()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemSetDTO.getDescription() != null && !newCustomItemSetDTO.getDescription().isEmpty()) {
            if(customItemSetDTO.getDescription() == null) {
                sqlBuilder.append(prefix).append("description =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description ='").append(customItemSetDTO.getDescription()).append("'");
            }
            prefix = ",";
        }
        if (newCustomItemSetDTO.getLastUpdateBy() != null && !newCustomItemSetDTO.getLastUpdateBy().isEmpty()) {
            if(customItemSetDTO.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy ='").append(customItemSetDTO.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemSetDTO.getLastUpdateDate() != null) {
            if(customItemSetDTO.getLastUpdateDate() == null) {
                sqlBuilder.append(prefix).append("lastUpdateDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateDate ='").append(customItemSetDTO.getLastUpdateDate()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemSetDTO.getName() != null && !newCustomItemSetDTO.getName().isEmpty()) {
            if(customItemSetDTO.getName() == null) {
                sqlBuilder.append(prefix).append("name =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name ='").append(customItemSetDTO.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemSetDTO.getUpdateState() != null ) {
            if(customItemSetDTO.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("updateState =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState ='").append(customItemSetDTO.getUpdateState()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemSetDTO.getStatus() != null && !newCustomItemSetDTO.getStatus().isEmpty()) {
            if(customItemSetDTO.getStatus() == null) {
                sqlBuilder.append(prefix).append("status =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("status ='").append(customItemSetDTO.getStatus()).append("' ");
            }
        }

        sqlBuilder.append(" WHERE fk_id_subIssuer = ").append("subissuerId").append( "; ");
        return sqlBuilder.toString();
    }


    public Resource getDownloadFile(String FILE_NAME) {
        String FILE_PATH = "src/main/resources/sql_scripts/";
        return downloadFileService.downloadFile(FILE_NAME, FILE_PATH);
    }

}
