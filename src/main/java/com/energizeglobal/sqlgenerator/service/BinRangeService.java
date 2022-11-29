package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.BinRangeEntity;
import com.energizeglobal.sqlgenerator.dto.BinRangeDto;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.repository.BinRangeRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "binRange")
public class BinRangeService {

    static boolean activeDB = false;

    private final String INSERT_SQL_FILE_NAME = "binRange.sql";

    private final String ROLLBACK_SQL_FILE_NAME = "binRange_rollback.sql";

    private final Mapping mappingCryptoConfig;

    private final BinRangeRepository binRangeRepository;

    private final GenerateSqlScriptService generateSqlScriptService;

    private final DownloadFileService downloadFileService;

    public BinRangeService(Mapping mappingCryptoConfig,
                           BinRangeRepository binRangeRepository,
                           GenerateSqlScriptService generateSqlScriptService,
                           DownloadFileService downloadFileService) {
        this.mappingCryptoConfig = mappingCryptoConfig;
        this.binRangeRepository = binRangeRepository;
        this.generateSqlScriptService = generateSqlScriptService;
        this.downloadFileService = downloadFileService;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<BinRangeDto> findAllBinRages() {

        List<BinRangeEntity> binRangeEntities = binRangeRepository.findAll();

        return mappingCryptoConfig.mapList(binRangeEntities, BinRangeDto.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public BinRangeDto getById(Long id) {

        BinRangeEntity binRangeEntity = binRangeRepository.getById(id);

        return mappingCryptoConfig.convertToDto(binRangeEntity, BinRangeDto.class);
    }

    @Transactional
    public String deleteById(Long id) {

        BinRangeEntity binRangeEntity = binRangeRepository.getById(id);

        String deleteQuery =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM CryptoConfig WHERE id = " + id + ";\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";

        String rollbackQuery = String.format("INSERT INTO `BinRange` (`activateState`, `createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`,\n" +
                        "\t\t\t\t`name`, `updateState`, `immediateActivation`, `lowerBound`, `panLength`,\n" +
                        "\t\t\t\t`sharedBinRange`, `updateDSDate`, `upperBound`, `toExport`,\n" +
                        "\t\t\t\t`) VALUES('%s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s');",
                binRangeEntity.getActivateState(), "NOW()",binRangeEntity.getCreationDate(),binRangeEntity.getDescription(),binRangeEntity.getLastUpdateBy(),binRangeEntity.getLastUpdateDate(),
                binRangeEntity.getName(),binRangeEntity.getUpdateState(),true,binRangeEntity.getLowerBound(),binRangeEntity.getPanLength(),
                false,"NULL",binRangeEntity.getUpperBound(),false );


        generateSqlScriptService.insertSqlScript(deleteQuery, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(rollbackQuery, ROLLBACK_SQL_FILE_NAME);
        if (activeDB)
            binRangeRepository.deleteById(id);
        return INSERT_SQL_FILE_NAME;
    }

    @Transactional
    public String saveBinRange(BinRangeDto binRangeDto) {

        BinRangeEntity binRangeEntity = mappingCryptoConfig.convertToEntity(binRangeDto, BinRangeEntity.class);

        String queryType = String.format("INSERT INTO `BinRange` (`activateState`, `createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`,\n" +
                        "\t\t\t\t`name`, `updateState`, `immediateActivation`, `lowerBound`, `panLength`,\n" +
                        "\t\t\t\t`sharedBinRange`, `updateDSDate`, `upperBound`, `toExport`, `fk_id_profileSet`, `fk_id_network`,\n" +
                        "\t\t\t\t`) VALUES('%s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s' , %s', '%s', '%s' , '%s', '%s', '%s', '%s');",
                binRangeDto.getActiveState(), binRangeDto.getCreatedBy(),binRangeDto.getCreationDate(),binRangeDto.getDescription(),binRangeDto.getLastUpdateBy(),binRangeDto.getLastUpdateDate(),
                binRangeDto.getName(),binRangeDto.getUpdateState(),binRangeDto.getImmediateActivation(),binRangeDto.getLowerBound(),binRangeDto.getPanLength(),
                binRangeDto.getSharedBinRange(),binRangeDto.getUpperBound(),binRangeDto.getToExport(),binRangeDto.getProfileset(),binRangeDto.getNetwork() );

        String queryTypeRollback =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM BinRange WHERE description = '" + binRangeDto.getDescription() + "';\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";


        binRangeRepository.save(binRangeEntity);

        generateSqlScriptService.insertSqlScript(queryType, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(queryTypeRollback, ROLLBACK_SQL_FILE_NAME);



        String deleteQuery =
                "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM BinRange WHERE id = " + binRangeDto.getId() + ";\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";

        if (activeDB)
            generateSqlScriptService.insertSqlScript(deleteQuery, ROLLBACK_SQL_FILE_NAME);

        return INSERT_SQL_FILE_NAME;
    }


    @Transactional
    public String updateBinRange(BinRangeDto dto) {

        BinRangeEntity binRangeEntity = mappingCryptoConfig.convertToEntity(dto, BinRangeEntity.class);
        binRangeEntity.setId(dto.getId());
        BinRangeEntity rollbackBinRangeEntity = binRangeRepository.getById(dto.getId());


        String updateSql = generateDynamicSqlUpdateScript(dto,dto);



        String updateQuery = String.format("UPDATE `BinRange` SET `activateState` = '%s', `createdBy` = '%s',`creationDate` = '%s', `description` = '%s' , " +
                        "`lastUpdateBy` = '%s', `lastUpdateDate` = '%s', `name` = '%s', `lowerBound` = '%s', `panLength` = '%s', `upperBound` = '%s' WHERE id = '%s';",
                binRangeEntity.getActivateState(),
                binRangeEntity.getCreatedBy(),
                binRangeEntity.getCreationDate(),
                binRangeEntity.getDescription(),
                binRangeEntity.getLastUpdateBy(),
                binRangeEntity.getLastUpdateDate(),
                binRangeEntity.getName(),
                binRangeEntity.getLowerBound(),
                binRangeEntity.getPanLength(),
                binRangeEntity.getUpperBound(),
                binRangeEntity.getId());

        String updateRollbackSql = generateDynamicSqlUpdateScript(mappingCryptoConfig.convertToDto(rollbackBinRangeEntity,BinRangeDto.class),dto);

        String rollbackQuery = String.format("UPDATE `BinRange` SET `activateState` = '%s', `createdBy` = '%s',`creationDate` = '%s', `description` = '%s' , " +
                        "`lastUpdateBy` = '%s', `lastUpdateDate` = '%s', `name` = '%s', `lowerBound` = '%s', `panLength` = '%s', `upperBound` = '%s' WHERE id = '%s';",
                binRangeEntity.getActivateState(),
                binRangeEntity.getCreatedBy(),
                binRangeEntity.getCreationDate(),
                binRangeEntity.getDescription(),
                binRangeEntity.getLastUpdateBy(),
                binRangeEntity.getLastUpdateDate(),
                binRangeEntity.getName(),
                binRangeEntity.getLowerBound(),
                binRangeEntity.getPanLength(),
                binRangeEntity.getUpperBound(),
                binRangeEntity.getId());

        generateSqlScriptService.insertSqlScript(updateSql, INSERT_SQL_FILE_NAME);

        generateSqlScriptService.insertSqlScript(updateRollbackSql, ROLLBACK_SQL_FILE_NAME);

        if (activeDB)
            binRangeRepository.save(binRangeEntity);

        return INSERT_SQL_FILE_NAME;
    }

    private String generateDynamicSqlUpdateScript(BinRangeDto binRangeDto,BinRangeDto newBinRangeDto) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();



        sqlBuilder.append("UPDATE BinRange SET ");
        if (newBinRangeDto.getActiveState() != null && !newBinRangeDto.getActiveState().isEmpty()) {
            if(binRangeDto.getActiveState() == null) {
                sqlBuilder.append(prefix).append("activateState =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("activateState ='").append(binRangeDto.getActiveState()).append("'");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getCreatedBy() != null && ! newBinRangeDto.getCreatedBy().isEmpty()) {
            if(binRangeDto.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy ='").append(binRangeDto.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getCreationDate() != null ) {
            if(binRangeDto.getCreationDate() == null) {
                sqlBuilder.append(prefix).append("creationDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate ='").append(binRangeDto.getCreationDate()).append("' ");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getDescription() != null && !newBinRangeDto.getDescription().isEmpty()) {
            if(binRangeDto.getDescription() == null) {
                sqlBuilder.append(prefix).append("description =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description ='").append(binRangeDto.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getLastUpdateBy() != null && !newBinRangeDto.getLastUpdateBy().isEmpty()) {
            if(binRangeDto.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy ='").append(binRangeDto.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getLastUpdateDate() != null) {
            if(binRangeDto.getLastUpdateDate() == null) {
                sqlBuilder.append(prefix).append("lastUpdateDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateDate ='").append(binRangeDto.getLastUpdateDate()).append("'");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getName() != null && !newBinRangeDto.getName().isEmpty()) {
            if(binRangeDto.getName() == null) {
                sqlBuilder.append(prefix).append("name =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name ='").append(binRangeDto.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getLowerBound() != null && !newBinRangeDto.getLowerBound().isEmpty()) {
            if(binRangeDto.getLowerBound() == null) {
                sqlBuilder.append(prefix).append("lowerBound =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lowerBound ='").append(binRangeDto.getLowerBound()).append("'");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getPanLength() != null && !newBinRangeDto.getPanLength().isEmpty()) {
            if(binRangeDto.getPanLength() == null) {
                sqlBuilder.append(prefix).append("panLength =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("panLength ='").append(binRangeDto.getPanLength()).append("'");
            }
            prefix = ", ";
        }
        if (newBinRangeDto.getUpperBound() != null && !newBinRangeDto.getUpperBound().isEmpty()) {
            if(binRangeDto.getUpperBound() == null) {
                sqlBuilder.append(prefix).append("upperBound =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("upperBound ='").append(binRangeDto.getUpperBound()).append("' ");
            }
        }
        sqlBuilder.append("WHERE id = ").append(binRangeDto.getId()).append( "; ");
        return sqlBuilder.toString();
    }


    public Resource getDownloadFile(String FILE_NAME) {
        String FILE_PATH = "src/main/resources/sql_scripts/";
        return downloadFileService.downloadFile(FILE_NAME, FILE_PATH);
    }

}
