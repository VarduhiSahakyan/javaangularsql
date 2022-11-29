package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.AuthentMeansEntity;
import com.energizeglobal.sqlgenerator.dto.AuthentMeansDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.repository.AuthentMeansRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@CacheConfig(cacheNames = "authentMeans")
public class AuthentMeansService {

    public Logger logger = LoggerFactory.getLogger(AuthentMeansService.class);
    static boolean activeDB = false;
    private final String INSERT_SQL_FILE_NAME = "authentmean.sql";
    private final String ROLLBACK_SQL_FILE_NAME = "authentmean_rollback.sql";
    private final Mapping mappingAuthentMean;
    private final AuthentMeansRepository authentMeansRepository;
    private final GenerateSqlScriptService generateSqlScriptService;
    private final DownloadFileService downloadFileService;
    private Timestamp thisMomentTime = new Timestamp(System.currentTimeMillis());

    public AuthentMeansService(Mapping mappingAuthentMean,
                               AuthentMeansRepository authentMeansRepository,
                               GenerateSqlScriptService generateSqlScriptService,
                               DownloadFileService downloadFileService) {
        this.mappingAuthentMean = mappingAuthentMean;
        this.authentMeansRepository = authentMeansRepository;
        this.generateSqlScriptService = generateSqlScriptService;
        this.downloadFileService = downloadFileService;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<AuthentMeansDTO> getAllAuthentMeans() {
        return mappingAuthentMean.mapList(authentMeansRepository.findAll(), AuthentMeansDTO.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<AuthentMeansDTO> getPagedAuthentMeans(Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return new PageImpl<>(mappingAuthentMean.mapList(authentMeansRepository.findAll(pageable), AuthentMeansDTO.class), pageable, authentMeansRepository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public AuthentMeansDTO getByIdAuthentMean(Long id) {
        return mappingAuthentMean.convertToDto(authentMeansRepository.getById(id), AuthentMeansDTO.class);
    }

    @Transactional
    public String deleteAuthentMeanById(Long id) {
        AuthentMeansEntity authentMeansEntity = authentMeansRepository.getById(id);

        String deleteQuery = "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM AuthentMeans WHERE id = '" + id + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        String rollbackQuery = String.format("INSERT INTO `AuthentMeans` (createdBy, creationDate, description, lastUpdateBy, " +
                        "lastUpdateDate, name, updateState) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                authentMeansEntity.getCreatedBy(),
                authentMeansEntity.getCreationDate(),
                authentMeansEntity.getDescription(),
                authentMeansEntity.getLastUpdateBy(),
                authentMeansEntity.getLastUpdateDate(),
                authentMeansEntity.getName(),
                authentMeansEntity.getUpdateState());

        generateSqlScriptService.insertSqlScript(deleteQuery, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(rollbackQuery, ROLLBACK_SQL_FILE_NAME);
        if (activeDB) {
            authentMeansRepository.deleteById(id);
        }
        return INSERT_SQL_FILE_NAME;
    }

    @Transactional
    public String updateAuthentMean(AuthentMeansDTO authentMeansDTO) {

        AuthentMeansEntity authentMeansEntity = mappingAuthentMean.convertToEntity(authentMeansDTO, AuthentMeansEntity.class);
        authentMeansEntity.setLastUpdateDate(thisMomentTime);
        authentMeansEntity.setCreationDate(authentMeansDTO.getCreationDate());
        String updateQuery = String.format("UPDATE `AuthentMeans` SET createdBy='%s', creationDate='%s', description='%s'," +
                        " lastUpdateBy='%s', lastUpdateDate='%s', name='%s', updateState='%s' WHERE id='%s';",
                authentMeansEntity.getCreatedBy(),
                authentMeansEntity.getCreationDate(),
                authentMeansEntity.getDescription(),
                authentMeansEntity.getLastUpdateBy(),
                thisMomentTime,
                authentMeansEntity.getName(),
                authentMeansEntity.getUpdateState(),
                authentMeansEntity.getId());

        AuthentMeansEntity authentMeansEntityRollback = authentMeansRepository.getById(authentMeansDTO.getId());
        String rollbackUpdateQuery = String.format("UPDATE `AuthentMeans` SET createdBy='%s', creationDate='%s', description='%s'," +
                        " lastUpdateBy='%s', lastUpdateDate='%s', name='%s', updateState='%s' WHERE id='%s';",
                authentMeansEntityRollback.getCreatedBy(),
                authentMeansEntityRollback.getCreationDate(),
                authentMeansEntityRollback.getDescription(),
                authentMeansEntityRollback.getLastUpdateBy(),
                authentMeansEntityRollback.getLastUpdateDate(),
                authentMeansEntityRollback.getName(),
                authentMeansEntityRollback.getUpdateState(),
                authentMeansEntityRollback.getId());



        generateSqlScriptService.insertSqlScript(generateDynamicSqlScript(authentMeansDTO,authentMeansDTO), INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(generateDynamicSqlScript(mappingAuthentMean.convertToDto(authentMeansEntityRollback,AuthentMeansDTO.class),authentMeansDTO), ROLLBACK_SQL_FILE_NAME);
        if (activeDB) {
            authentMeansRepository.save(authentMeansEntity);
        }
        return INSERT_SQL_FILE_NAME;
    }

    private String generateDynamicSqlScript(AuthentMeansDTO authentMeansDTO,AuthentMeansDTO newAuthentMeansDTO){
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE AuthentMeans SET ");
        if (newAuthentMeansDTO.getCreatedBy() != null && !newAuthentMeansDTO.getCreatedBy().isEmpty()) {
            if(authentMeansDTO.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy ='").append(authentMeansDTO.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newAuthentMeansDTO.getCreationDate() != null) {
            if(authentMeansDTO.getCreationDate() == null) {
                sqlBuilder.append(prefix).append("creationDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate ='").append(authentMeansDTO.getCreationDate()).append("'");
            }
            prefix = " ,";
        }
        if (newAuthentMeansDTO.getDescription() != null && !newAuthentMeansDTO.getDescription().isEmpty()) {
            if(authentMeansDTO.getDescription() == null) {
                sqlBuilder.append(prefix).append("description =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description ='").append(authentMeansDTO.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newAuthentMeansDTO.getLastUpdateBy() != null && !newAuthentMeansDTO.getLastUpdateBy().isEmpty()) {
            if(authentMeansDTO.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy ='").append(authentMeansDTO.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
         if (thisMomentTime != null ) {
            sqlBuilder.append(prefix).append("lastUpdateDate ='").append(thisMomentTime).append("'");
            prefix = ", ";
        }
        if (newAuthentMeansDTO.getName() != null && !newAuthentMeansDTO.getName().isEmpty() ) {
            if(authentMeansDTO.getName() == null) {
                sqlBuilder.append(prefix).append("name =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name ='").append(authentMeansDTO.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newAuthentMeansDTO.getUpdateState() != null) {
            if(authentMeansDTO.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("updateState =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState ='").append(authentMeansDTO.getUpdateState()).append("' ");
            }
        }
        sqlBuilder.append("WHERE id = ").append(authentMeansDTO.getId()).append("; ");
        return sqlBuilder.toString();

    }

    @Transactional
    public String saveAuthentMean(AuthentMeansDTO authentMeansDTO) {

        Long lastId = authentMeansRepository.getMaxId() + 1;
        AuthentMeansEntity authentMeansEntity = mappingAuthentMean
                .convertToEntity(authentMeansDTO, AuthentMeansEntity.class);
        if (authentMeansEntity.getCreationDate() == null) {
            authentMeansEntity.setCreationDate(thisMomentTime);
        }
        if (authentMeansEntity.getId() == null) {
            authentMeansEntity.setId(lastId);
        }

        String insertQuery = String.format("INSERT INTO `AuthentMeans` (createdBy, creationDate, description, lastUpdateBy, " +
                        "lastUpdateDate, name, updateState) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                authentMeansEntity.getCreatedBy(),
                thisMomentTime,
                authentMeansEntity.getDescription(),
                authentMeansEntity.getLastUpdateBy(),
                thisMomentTime,
                authentMeansEntity.getName(),
                authentMeansEntity.getUpdateState());


        String deleteQuery = "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM AuthentMeans WHERE id = '" + authentMeansEntity.getId() + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        generateSqlScriptService.insertSqlScript(insertQuery, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(deleteQuery, ROLLBACK_SQL_FILE_NAME);

        if (activeDB) {
            authentMeansRepository.save(authentMeansEntity);
        }
        return INSERT_SQL_FILE_NAME;
    }

    public Resource getDownloadFile(String FILE_NAME) {
        String FILE_PATH = "src/main/resources/sql_scripts/";
        return downloadFileService.downloadFile(FILE_NAME, FILE_PATH);
    }
}
