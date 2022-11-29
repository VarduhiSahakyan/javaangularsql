package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.RuleCondition;
import com.energizeglobal.sqlgenerator.domain.RuleEntity;
import com.energizeglobal.sqlgenerator.dto.RuleConditionDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.mapper.RuleConditionMapper;
import com.energizeglobal.sqlgenerator.repository.RuleConditionRepository;
import com.energizeglobal.sqlgenerator.repository.RuleRepository;
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
@CacheConfig(cacheNames = "ruleCondition")
public class RuleConditionService {

    private Boolean dbConnection = false;
    private final RuleConditionRepository repository;
    private final RuleRepository ruleRepository;
    private final Mapping mapper;
    private String MAIN_FILE_NAME = "ruleCondition.sql";
    private String FILE_PATH = "src/main/resources/sql_scripts/";
    private String ROLLBACK_FILE_NAME = "ruleCondition_rollback.sql";
    private String mainPath = FILE_PATH + MAIN_FILE_NAME;
    private String rollbackPath = FILE_PATH + ROLLBACK_FILE_NAME;
    private Timestamp thisMomentTime = new Timestamp(System.currentTimeMillis());

    public RuleConditionService(RuleConditionRepository repository, RuleRepository ruleRepository, Mapping mapper) {
        this.repository = repository;
        this.ruleRepository = ruleRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public RuleConditionDTO findById(Long id) {
        RuleCondition condition = repository.getById(id);
        return mapper.convertToDto(condition, RuleConditionDTO.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<RuleConditionDTO> getAllConditions() {
        return mapper.mapList(repository.findAll(), RuleConditionDTO.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<RuleConditionDTO>getAllConditions(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
    return new PageImpl<>(mapper.mapList(repository.findAll(paging), RuleConditionDTO.class), paging, repository.count());
    }

    @Transactional
    public String generateInsertSqlScript(RuleConditionDTO ruleConditionDto) {
        RuleEntity rule = ruleRepository.getById(ruleConditionDto.getRuleId());

        RuleCondition ruleCondition = RuleConditionMapper.dtoToEntity(ruleConditionDto, rule);
        ruleCondition.setCreationDate(thisMomentTime);
        ruleCondition.setLastUpdateDate(thisMomentTime);
        String queryType = "INSERT INTO RuleCondition  ( " +
                "createdBy, " +
                "creationDate, " +
                "description, " +
                "lastUpdateBy, " +
                "lastUpdateDate, " +
                "name, " +
                "updateState, " +
                "fk_id_rule )";

        String queryValue = " \n" +
                "VALUES (" + " '" +
                ruleConditionDto.getCreatedBy() + "', '" +
                thisMomentTime + "', '" +
                ruleConditionDto.getDescription() + "', '" +
                ruleConditionDto.getLastUpdateBy() + "', '" +
                thisMomentTime + "', '" +
                ruleConditionDto.getName() + "', '" +
                ruleConditionDto.getUpdateState() + "', " +
                ruleCondition.getRule().getId() + ");";

        String sqlInsert = queryType + queryValue;
        pathGenerator(sqlInsert, mainPath);

        generateSqlScriptForInsertRollback(ruleCondition.getId());

        if (dbConnection)
            repository.save(ruleCondition);

        return MAIN_FILE_NAME;
    }

    private String generateSqlScriptForInsertRollback(Long id) {

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM RuleCondition WHERE id = " + id + ";\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        pathGenerator(deleteQuery, rollbackPath);

        return ROLLBACK_FILE_NAME;
    }

    @Transactional
    public String generateUpdateSqlScript(RuleConditionDTO ruleConditionDto) {
        RuleCondition oldRuleCondition = repository.getById(ruleConditionDto.getId());
        RuleCondition newRuleCondition = mapper.convertToEntity(ruleConditionDto, RuleCondition.class);
        newRuleCondition.setCreationDate(ruleConditionDto.getCreationDate());
        newRuleCondition.setLastUpdateDate(thisMomentTime);
        ruleConditionDto.setLastUpdateDate(thisMomentTime);

        String queryUpdate = "UPDATE RuleCondition SET " +
                "createdBy = '" + ruleConditionDto.getCreatedBy() + "', " +
                "creationDate = '" + ruleConditionDto.getCreationDate() + "', " +
                "description = '" + ruleConditionDto.getDescription() + "', " +
                "lastUpdateBy = '" + ruleConditionDto.getLastUpdateBy() + "', " +
                "lastUpdateDate = '" + thisMomentTime + "', " +
                "name = '" + ruleConditionDto.getName() + "', " +
                "updateState = '" + ruleConditionDto.getUpdateState() + "' " +
                " WHERE id = " + ruleConditionDto.getId() + ";";

        pathGenerator(generateDynamikSQL(ruleConditionDto, ruleConditionDto), mainPath);


        generateSqlScriptForUpdateRollback(mapper.convertToDto(oldRuleCondition, RuleConditionDTO.class), ruleConditionDto);

        if (dbConnection)
            repository.save(newRuleCondition);

        return MAIN_FILE_NAME;
    }

    private String generateSqlScriptForUpdateRollback(RuleConditionDTO oldRuleCondition, RuleConditionDTO newRuleCondition) {
        String queryUpdate = "UPDATE RuleCondition SET " +
                "createdBy = '" + oldRuleCondition.getCreatedBy() + "', " +
                "creationDate = '" + oldRuleCondition.getCreationDate() + "', " +
                "description = '" + oldRuleCondition.getDescription() + "', " +
                "lastUpdateBy = '" + oldRuleCondition.getLastUpdateBy() + "', " +
                "lastUpdateDate = '" + thisMomentTime + "', " +
                "name = '" + oldRuleCondition.getName() + "', " +
                "updateState = '" + oldRuleCondition.getUpdateState() + "' " +
                " WHERE id = " + oldRuleCondition.getId() + ";";

        pathGenerator(generateDynamikSQL(oldRuleCondition, newRuleCondition), rollbackPath);

        return ROLLBACK_FILE_NAME;
    }

    private String generateDynamikSQL(RuleConditionDTO ruleConditionDTO, RuleConditionDTO newRuleConditionDTO) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE RuleCondition SET ");
        if (newRuleConditionDTO.getCreatedBy() != null && !newRuleConditionDTO.getCreatedBy().isEmpty()) {
            if (ruleConditionDTO.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy ='").append(ruleConditionDTO.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newRuleConditionDTO.getCreationDate() != null) {
            if (ruleConditionDTO.getCreationDate() == null) {
                sqlBuilder.append(prefix).append("creationDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate ='").append(ruleConditionDTO.getCreationDate()).append("'");
            }
            prefix = ", ";
        }
        if (newRuleConditionDTO.getDescription() != null && !newRuleConditionDTO.getDescription().isEmpty()) {
            if (ruleConditionDTO.getDescription() == null) {
                sqlBuilder.append(prefix).append("description =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description ='").append(ruleConditionDTO.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newRuleConditionDTO.getLastUpdateBy() != null && !newRuleConditionDTO.getLastUpdateBy().isEmpty()) {
            if (ruleConditionDTO.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy ='").append(ruleConditionDTO.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (thisMomentTime != null) {
            sqlBuilder.append(prefix).append("lastUpdateDate = '").append(thisMomentTime).append("'");
            prefix = ", ";
        }
        if (newRuleConditionDTO.getName() != null && !newRuleConditionDTO.getName().isEmpty()) {
            if (ruleConditionDTO.getName() == null) {
                sqlBuilder.append(prefix).append("name =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name ='").append(ruleConditionDTO.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newRuleConditionDTO.getUpdateState() != null) {
            if (ruleConditionDTO.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("updateState =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState ='").append(ruleConditionDTO.getUpdateState()).append("' ");
            }
        }
        sqlBuilder.append("WHERE id = ").append(ruleConditionDTO.getId()).append("; ");

        return sqlBuilder.toString();
    }

    @Transactional
    public String generateDeleteSqlScript(Long id, Long ruleId) {

        RuleConditionDTO ruleCondition = findById(id);

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM RuleCondition WHERE id = " + id + ";\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        pathGenerator(deleteQuery, mainPath);

        generateSqlScriptForDeleteRollback(ruleCondition, ruleId);

        if (dbConnection)
            repository.deleteById(id);

        return MAIN_FILE_NAME;
    }

    private String generateSqlScriptForDeleteRollback(RuleConditionDTO ruleConditionDto, Long ruleId) {

        String queryType = "INSERT INTO RuleCondition ( " +
                "createdBy, " +
                "creationDate, " +
                "description, " +
                "lastUpdateBy, " +
                "lastUpdateDate, " +
                "name, " +
                "updateState, " +
                "fk_id_rule )";

        String queryValue = " \n" +
                " VALUES (" + " '" +
                ruleConditionDto.getCreatedBy() + "', '" +
                ruleConditionDto.getCreationDate() + "', '" +
                ruleConditionDto.getDescription() + "', '" +
                ruleConditionDto.getLastUpdateBy() + "', '" +
                ruleConditionDto.getLastUpdateDate() + "', '" +
                ruleConditionDto.getName() + "', '" +
                ruleConditionDto.getUpdateState() + "', " +
                ruleId + ");";

        String sqlInsert = queryType + queryValue;

        pathGenerator(sqlInsert, rollbackPath);

        return ROLLBACK_FILE_NAME;
    }


    private void pathGenerator(String sql, String path) {

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
