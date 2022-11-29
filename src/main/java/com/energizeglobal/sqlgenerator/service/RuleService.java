package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.Profile;
import com.energizeglobal.sqlgenerator.domain.RuleEntity;
import com.energizeglobal.sqlgenerator.dto.RuleDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.mapper.RuleMapper;
import com.energizeglobal.sqlgenerator.repository.ProfileRepository;
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
@CacheConfig(cacheNames = "rule")
public class RuleService {

    private Boolean dbAction = false;
    private final RuleRepository ruleRepository;
    private final ProfileRepository profileRepository;
    private final RuleMapper ruleMapper;
    private final Mapping mapping;

    private String FILE_PATH = "src/main/resources/sql_scripts/";
    private String MAIN_FILE_NAME = "rule.sql";
    private String ROLLBACK_FILE_NAME = "rule_rollback.sql";
    private String mainPath = FILE_PATH + MAIN_FILE_NAME;
    private String rollbackPath = FILE_PATH + ROLLBACK_FILE_NAME;
    private Timestamp thisMomentTime = new Timestamp(System.currentTimeMillis());

    public RuleService(RuleRepository ruleRepository, RuleMapper ruleMapper, ProfileRepository profileRepository, Mapping mapping) {
        this.ruleRepository = ruleRepository;
        this.ruleMapper = ruleMapper;
        this.profileRepository = profileRepository;
        this.mapping = mapping;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<RuleDTO> getAllRules() {
        return mapping.mapList(ruleRepository.findAll(), RuleDTO.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<RuleDTO> getAllRules(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return new PageImpl<>(mapping.mapList(ruleRepository.findAll(paging), RuleDTO.class), paging, profileRepository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public RuleDTO findById(Long id) {
        RuleEntity ruleEntity = ruleRepository.getById(id);
        ruleEntity.setLastUpdateDate(thisMomentTime);
        return mapping.convertToDto(ruleEntity, RuleDTO.class);
    }

    @Transactional
    public String generateInsertSqlScript(RuleDTO ruleDto) {

        Profile profile = profileRepository.getById(ruleDto.getProfileId());
        RuleEntity ruleEntity = ruleMapper.dtoToEntity(ruleDto, profile);
        ruleEntity.setCreationDate(thisMomentTime);
        ruleEntity.setLastUpdateDate(thisMomentTime);

        String queryType = "INSERT INTO Rule  ( " +
                "createdBy, " +
                "creationDate, " +
                "description, " +
                "lastUpdateBy, " +
                "lastUpdateDate, " +
                "name, " +
                "updateState, " +
                "orderRule, " +
                "fk_id_profile )";

        String queryValue = " \n" +
                "VALUES (" + " '" +
                ruleDto.getCreatedBy() + "', '" +
                thisMomentTime + "', '" +
                ruleDto.getDescription() + "', '" +
                ruleDto.getLastUpdateBy() + "', '" +
                thisMomentTime + "', '" +
                ruleDto.getName() + "', '" +
                ruleDto.getUpdateState() + "', " +
                ruleDto.getOrderRule() + ", " +
                ruleDto.getProfileId() + ");";

        String sqlInsert = queryType + queryValue;

        pathGenerator(sqlInsert, mainPath);

        generateSqlScriptForInsertRollback(ruleEntity.getId());

        if (dbAction)
            ruleRepository.save(ruleEntity);

        return MAIN_FILE_NAME;
    }

    public String generateSqlScriptForInsertRollback(Long id) {

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM Rule WHERE id = " + id + ";\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        pathGenerator(deleteQuery, rollbackPath);

        return ROLLBACK_FILE_NAME;
    }

    @Transactional
    public String generateUpdateSqlScript(RuleDTO ruleDto) {
        RuleEntity oldRule = ruleRepository.getById(ruleDto.getId());
        RuleEntity newRule = mapping.convertToEntity(ruleDto, RuleEntity.class);
        ruleDto.setLastUpdateDate(thisMomentTime);
        newRule.setCreationDate(oldRule.getCreationDate());
        newRule.setLastUpdateDate(thisMomentTime);


        String dynamikSql = generateDynamikSQL(ruleDto, ruleDto);


        String queryUpdate = "UPDATE Rule SET " +
                "createdBy = '" + ruleDto.getCreatedBy() + "', " +
                "creationDate = '" + ruleDto.getCreationDate() + "', " +
                "description = '" + ruleDto.getDescription() + "', " +
                "lastUpdateBy = '" + ruleDto.getLastUpdateBy() + "', " +
                "lastUpdateDate = '" + thisMomentTime + "', " +
                "name = '" + ruleDto.getName() + "', " +
                "updateState = '" + ruleDto.getUpdateState() + "', " +
                "orderRule = " + ruleDto.getOrderRule() + " " +
                " WHERE id = " + ruleDto.getId() + ";";

        pathGenerator(dynamikSql, mainPath);

        RuleDTO oldRuleDTO = mapping.convertToDto(oldRule, RuleDTO.class);
        generateSqlScriptForUpdateRollback(oldRuleDTO, ruleDto);

        if (dbAction)
            ruleRepository.save(newRule);

        return MAIN_FILE_NAME;
    }

    public String generateSqlScriptForUpdateRollback(RuleDTO oldRule, RuleDTO newRule) {


        String dynamikSql = generateDynamikSQL(oldRule, newRule);

        String queryUpdate = "UPDATE Rule SET " +
                "createdBy = '" + oldRule.getCreatedBy() + "', " +
                "creationDate = '" + oldRule.getCreationDate() + "', " +
                "description = '" + oldRule.getDescription() + "', " +
                "lastUpdateBy = '" + oldRule.getLastUpdateBy() + "', " +
                "lastUpdateDate = '" + oldRule.getLastUpdateDate() + "', " +
                "name = '" + oldRule.getName() + "', " +
                "updateState = '" + oldRule.getUpdateState() + "', " +
                "orderRule = " + oldRule.getOrderRule() + " " +
                " WHERE id = " + oldRule.getId() + ";";

        pathGenerator(dynamikSql, rollbackPath);

        return ROLLBACK_FILE_NAME;
    }

    private String generateDynamikSQL(RuleDTO ruleDto, RuleDTO newDto) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE Rule SET ");
        if (newDto.getCreatedBy() != null && !newDto.getCreatedBy().isEmpty()) {
            if (ruleDto.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy = '").append(ruleDto.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getCreationDate() != null) {
            if (ruleDto.getCreationDate() == null) {
                sqlBuilder.append(prefix).append("creationDate = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate = '").append(ruleDto.getCreationDate()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getDescription() != null && !newDto.getDescription().isEmpty()) {
            if (ruleDto.getDescription() == null) {
                sqlBuilder.append(prefix).append("description = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description = '").append(ruleDto.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getLastUpdateBy() != null && !newDto.getLastUpdateBy().isEmpty()) {
            if (ruleDto.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy = '").append(ruleDto.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (thisMomentTime != null) {
            sqlBuilder.append(prefix).append("lastUpdateDate = '").append(thisMomentTime).append("'");
            prefix = ", ";
        }
        if (newDto.getName() != null && !newDto.getName().isEmpty()) {
            if (ruleDto.getName() == null) {
                sqlBuilder.append(prefix).append("name = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name = '").append(ruleDto.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getUpdateState() != null) {
            if (ruleDto.getName() == null) {
                sqlBuilder.append(prefix).append("updateState = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState = '").append(ruleDto.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getOrderRule() != null) {
            if (ruleDto.getOrderRule() == null) {
                sqlBuilder.append(prefix).append("orderRule = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("orderRule = ").append(ruleDto.getOrderRule()).append(" ");
            }
        }
        sqlBuilder.append(" WHERE id = ").append(ruleDto.getId()).append("; ");

        return sqlBuilder.toString();
    }

    @Transactional
    public String generateDeleteSqlScript(Long id) {

        RuleDTO rule = findById(id);
        Long profileId = rule.getProfileId();

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM Rule WHERE id = " + id + ";\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";
        pathGenerator(deleteQuery, mainPath);

        generateSqlScriptForDeleteRollback(rule, profileId);

        if (dbAction)
            ruleRepository.deleteById(id);

        return MAIN_FILE_NAME;
    }

    public String generateSqlScriptForDeleteRollback(RuleDTO rule, Long profileId) {

        String queryType = "INSERT INTO Rule  ( " +
                "createdBy, " +
                "creationDate, " +
                "description, " +
                "lastUpdateBy, " +
                "name, " +
                "updateState, " +
                "orderRule, " +
                "fk_id_profile  )";

        String queryValue = " \n" +
                " VALUES (" + " '" +
                rule.getCreatedBy() + "', '" +
                rule.getCreationDate() + "', '" +
                rule.getDescription() + "', '" +
                rule.getLastUpdateBy() + "', '" +
                rule.getName() + "', '" +
                rule.getUpdateState() + "', " +
                rule.getOrderRule() + ", " +
                profileId + ");";

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
