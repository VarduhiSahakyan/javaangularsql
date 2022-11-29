package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.CustomItemEntity;
import com.energizeglobal.sqlgenerator.dto.CustomItemDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;

import com.energizeglobal.sqlgenerator.repository.CustomItemRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@CacheConfig(cacheNames = "customItem")
public class CustomItemService {

    static boolean activeDB = false;

    private final String INSERT_SQL_FILE_NAME = "customItem.sql";

    private final String ROLLBACK_SQL_FILE_NAME = "customItem_rollback.sql";

    private final Mapping mappingCryptoConfig;

    private final CustomItemRepository customItemRepository;

    private final GenerateSqlScriptService generateSqlScriptService;

    private final DownloadFileService downloadFileService;

    public CustomItemService(Mapping mappingCryptoConfig,
                             CustomItemRepository customItemRepository,
                             GenerateSqlScriptService generateSqlScriptService,
                             DownloadFileService downloadFileService) {
        this.mappingCryptoConfig = mappingCryptoConfig;
        this.customItemRepository = customItemRepository;
        this.generateSqlScriptService = generateSqlScriptService;
        this.downloadFileService = downloadFileService;
    }


    @Transactional(readOnly = true)
    @Cacheable
    public Page<CustomItemDTO> findAllCustomItems(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return new PageImpl<>(mappingCryptoConfig.mapList(customItemRepository.findAll(paging), CustomItemDTO.class), paging, customItemRepository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public CustomItemDTO getById(Long id) {

        CustomItemEntity costItemEntity = customItemRepository.getCustomItemById(id);

        return mappingCryptoConfig.convertToDto(costItemEntity, CustomItemDTO.class);
    }

    @Transactional
    public String deleteById(Long id) {

        CustomItemEntity customItemEntity = customItemRepository.getById(id);

        String deleteQuery =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM CryptoConfig WHERE id = " + id + ";\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";


        String rollbackQuery = String.format("INSERT INTO `CustomItem` (`DTYPE`, `createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`,\n" +
                        "\t\t\t\t`name`, `updateState`, `locale`, `ordinal`, `pageTypes`,\n" +
                        "\t\t\t\t`value`, `fk_id_network`, `fk_id_image`, `fk_id_customItemSet`,\n" +
                        "\t\t\t\t`) VALUES('%s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s');",
                customItemEntity.getDTYPE(), "NOW()", customItemEntity.getCreationDate(), customItemEntity.getDescription(),
                customItemEntity.getLastUpdateBy(), customItemEntity.getLastUpdateDate(),
                customItemEntity.getName(), customItemEntity.getUpdateState(), customItemEntity.getLocale(), customItemEntity.getOrdinal(), customItemEntity.getPageTypes(),
                customItemEntity.getValue(), "NETWORK", "IMAGE", "CUSTOM_ITEM_SET");


        generateSqlScriptService.insertSqlScript(deleteQuery, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(rollbackQuery, ROLLBACK_SQL_FILE_NAME);
        if (activeDB)
            customItemRepository.deleteById(id);
        return INSERT_SQL_FILE_NAME;
    }

    @Transactional
    public String saveCustomItem(CustomItemDTO customItemDTO) {

        CustomItemEntity customItemEntity = mappingCryptoConfig.convertToEntity(customItemDTO, CustomItemEntity.class);

        String queryType = String.format("INSERT INTO `CustomItem` (`DTYPE`, `createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`,\n" +
                        "\t\t\t\t`name`, `updateState`, `locale`, `ordinal`, `pageTypes`,\n" +
                        "\t\t\t\t`value`, `fk_id_network`, `fk_id_image`, `fk_id_customItemSet`,\n" +
                        "\t\t\t\t`) VALUES('%s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s');",
                customItemEntity.getDTYPE(), "NOW()", customItemEntity.getCreationDate(), customItemEntity.getDescription(),
                customItemEntity.getLastUpdateBy(), customItemEntity.getLastUpdateDate(),
                customItemEntity.getName(), customItemEntity.getUpdateState(), customItemEntity.getLocale(), customItemEntity.getOrdinal(), customItemEntity.getPageTypes(),
                customItemEntity.getValue(), "NETWORK", "IMAGE", "CUSTOM_ITEM_SET");

        String queryTypeRollback =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM CustomItem WHERE description = '" + customItemDTO.getDescription() + "';\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";


        customItemRepository.save(customItemEntity);

        generateSqlScriptService.insertSqlScript(queryType, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(queryTypeRollback, ROLLBACK_SQL_FILE_NAME);


        String deleteQuery =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
//                        "DELETE FROM BinRange WHERE id = " + binRangeDto.getId() + ";\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";

        if (activeDB)
            generateSqlScriptService.insertSqlScript(deleteQuery, ROLLBACK_SQL_FILE_NAME);

        return INSERT_SQL_FILE_NAME;
    }


    @Transactional
    public String updateCustomItem(CustomItemDTO customItemDTO) {

        CustomItemEntity customItemEntity = mappingCryptoConfig.convertToEntity(customItemDTO, CustomItemEntity.class);
        customItemEntity.setId(customItemDTO.getId());
        CustomItemEntity rollbackCustomItemEntity = customItemRepository.getCustomItemById(customItemDTO.getId());
        customItemDTO.setCustomItemSet(rollbackCustomItemEntity.getCustomItemSet());

        String updateSql = generateDynamicSqlUpdateScript(customItemDTO, customItemDTO,rollbackCustomItemEntity.getPageTypes(),rollbackCustomItemEntity.getOrdinal());


        String updateRollbackSql = generateDynamicSqlUpdateScript(mappingCryptoConfig.convertToDto(rollbackCustomItemEntity, CustomItemDTO.class), customItemDTO,customItemDTO.getPageTypes(),customItemDTO.getOrdinal());

        generateSqlScriptService.insertSqlScript(updateSql, INSERT_SQL_FILE_NAME);

        generateSqlScriptService.insertSqlScript(updateRollbackSql, ROLLBACK_SQL_FILE_NAME);

        if (activeDB)
            customItemRepository.save(customItemEntity);

        return INSERT_SQL_FILE_NAME;
    }

    private String generateDynamicSqlUpdateScript(CustomItemDTO customItemDTO, CustomItemDTO newCustomItemDTO,String pageType, int ordinal) {
        String prefix = "";
        String subIssuerId = "SET @subissuerId = (SELECT id from SubIssuer WHERE code = " + newCustomItemDTO.getCustomItemSet().getSubIssuer().getCode() + "); \n\n";
        String customItemSetId = "SET @customItemSetID = (SELECT id FROM CustomItemSet WHERE name = '" + newCustomItemDTO.getCustomItemSet().getName() + "' and fk_id_subIssuer = @subissuerId); \n\n";
        StringBuilder sqlBuilder = new StringBuilder();


        sqlBuilder.append(subIssuerId);
        sqlBuilder.append(customItemSetId);
        sqlBuilder.append("UPDATE CustomItem SET ");
        if (newCustomItemDTO.getDTYPE() != null && !newCustomItemDTO.getDTYPE().isEmpty()) {
            if (customItemDTO.getDTYPE() == null) {
                sqlBuilder.append(prefix).append("DTYPE =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("DTYPE ='").append(customItemDTO.getDTYPE()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getCreatedBy() != null && !newCustomItemDTO.getCreatedBy().isEmpty()) {
            if (customItemDTO.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy ='").append(customItemDTO.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getCreationDate() != null) {
            if (customItemDTO.getCreationDate() == null) {
                sqlBuilder.append(prefix).append("creationDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate ='").append(customItemDTO.getCreationDate()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getDescription() != null && !newCustomItemDTO.getDescription().isEmpty()) {
            if (customItemDTO.getDescription() == null) {
                sqlBuilder.append(prefix).append("description =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description ='").append(customItemDTO.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getLastUpdateBy() != null && !newCustomItemDTO.getLastUpdateBy().isEmpty()) {
            if (customItemDTO.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy ='").append(customItemDTO.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getLastUpdateDate() != null) {
            if (customItemDTO.getLastUpdateDate() == null) {
                sqlBuilder.append(prefix).append("lastUpdateDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateDate ='").append(customItemDTO.getLastUpdateDate()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getName() != null && !newCustomItemDTO.getName().isEmpty()) {
            if (customItemDTO.getName() == null) {
                sqlBuilder.append(prefix).append("name =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name ='").append(customItemDTO.getName()).append("'");
            }
            prefix = ",";
        }
        if (newCustomItemDTO.getUpdateState() != null) {
            if (customItemDTO.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("updateState =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState ='").append(customItemDTO.getUpdateState()).append("' ");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getLocale() != null && !newCustomItemDTO.getLocale().isEmpty()) {
            if (customItemDTO.getLocale() == null) {
                sqlBuilder.append(prefix).append("locale =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("locale ='").append(customItemDTO.getLocale()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getOrdinal() != -1) {
            sqlBuilder.append(prefix).append("ordinal ='").append(customItemDTO.getOrdinal()).append("'");
            prefix = ", ";
        }
        if (newCustomItemDTO.getPageTypes() != null && !newCustomItemDTO.getPageTypes().isEmpty()) {
            if (customItemDTO.getPageTypes() == null) {
                sqlBuilder.append(prefix).append("pageTypes =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("pageTypes ='").append(customItemDTO.getPageTypes()).append("'");
            }
            prefix = ", ";
        }
        if (newCustomItemDTO.getValue() != null && !newCustomItemDTO.getValue().isEmpty()) {
            if (customItemDTO.getValue() == null) {
                sqlBuilder.append(prefix).append("value =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("value ='").append(customItemDTO.getValue()).append("' ");
            }
        }

        StringBuilder orderBy = new StringBuilder();
        orderBy.append("fk_id_customItemSet = ").append("@customItemSetID").append(" AND pageTypes = '")
                .append(pageType).append("' AND ordinal = ").append(ordinal);

        sqlBuilder.append("WHERE ").append(orderBy.toString()).append("; ");
//
//        "\t\t\t\t`name`, `updateState`, `locale`, `ordinal`, `pageTypes`,\n" +
//                "\t\t\t\t`value`, `fk_id_network`, `fk_id_image`, `fk_id_customItemSet`,
        return sqlBuilder.toString();
    }


    public Resource getDownloadFile(String FILE_NAME) {
        String FILE_PATH = "src/main/resources/sql_scripts/";
        return downloadFileService.downloadFile(FILE_NAME, FILE_PATH);
    }
}
