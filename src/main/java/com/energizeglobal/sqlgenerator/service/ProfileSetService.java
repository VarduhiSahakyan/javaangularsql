package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.ProfileSet;
import com.energizeglobal.sqlgenerator.dto.ProfileSetDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.repository.ProfileSetRepository;
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
@CacheConfig(cacheNames = "profileSet")
public class ProfileSetService {

    private String FILE_PATH = "src/main/resources/sql_scripts/";
    private String DATA_FILE_NAME = "profileSet.sql";
    private String DATA_ROLLBACK_FILE_NAME = "profileSet_rollback.sql";
    private Timestamp thisMomentTime = new Timestamp(System.currentTimeMillis());
    private final Mapping profileSetMapping;

    private ProfileSetRepository profileSetRepository;

    public ProfileSetService(Mapping profileSetMapping, ProfileSetRepository profileSetRepository) {
        this.profileSetMapping = profileSetMapping;
        this.profileSetRepository = profileSetRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<ProfileSetDTO> getAllProfileSets(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return new PageImpl<>(profileSetMapping.mapList(profileSetRepository.findAll(paging), ProfileSetDTO.class), paging, profileSetRepository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<ProfileSetDTO> getAllProfileSets() {
        return (profileSetMapping.mapList(profileSetRepository.findAll(), ProfileSetDTO.class));
    }

    @Transactional(readOnly = true)
    @Cacheable
    public ProfileSetDTO getProfileSet(Long id) {
        return profileSetMapping.convertToDto(profileSetRepository.getById(id), ProfileSetDTO.class);
    }

    public String generateInsertSqlScript(ProfileSetDTO dto) {
        String Insert = "INSERT INTO ProfileSet (createdBy, creationDate, description, " +
                "name, updateState, fk_id_subIssuer)\n" +
                " VALUES ('" + dto.getCreatedBy() + "', '" + thisMomentTime + "', '"
                + dto.getDescription() + "', '" + dto.getName() + "','" + dto.getUpdateState() + "', "
                + dto.getSubIssuerId() + ");";
        String path = FILE_PATH + DATA_FILE_NAME;
        return storeQueryInFile(path, Insert);
    }

    public String generateInsertSqlScriptWithRollback(ProfileSetDTO dto) {
        String delete = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM ProfileSet WHERE name ='" + dto.getName() + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return storeQueryInFile(path, delete);
    }

    public String generateEditSqlScript(ProfileSetDTO dto) {
        String sql = "UPDATE ProfileSet SET " +
                "createdBy='" + dto.getCreatedBy() + "', " +
                "creationDate='" + dto.getCreationDate() + "', " +
                "description='" + dto.getDescription() + "', " +
                "lastUpdateBy='" + dto.getLastUpdateBy() + "' ," +
                "lastUpdateDate='" + thisMomentTime + "' ," +
                "name='" + dto.getName() + "', " +
                "updateState='" + dto.getUpdateState() + "' " +
                " WHERE id= " + dto.getId() + ";";
        String path = FILE_PATH + DATA_FILE_NAME;
        return storeQueryInFile(path, generateDynamicSqlScript(dto, dto));
    }

    public String generateEditSqlScriptWithRollback(ProfileSetDTO dto) {
        ProfileSet profileSet = profileSetRepository.getProfileSetById(dto.getId());
        String sql = "UPDATE ProfileSet SET " +
                "createdBy='" + profileSet.getCreatedBy() + "', " +
                "creationDate='" + profileSet.getCreationDate() + "', " +
                "description='" + profileSet.getDescription() + "', " +
                "lastUpdateBy='" + profileSet.getLastUpdateBy() + "' ," +
                "lastUpdateDate='" + profileSet.getLastUpdateDate() + "' ," +
                "name='" + profileSet.getName() + "', " +
                "updateState='" + profileSet.getUpdateState() + "' " +
                "WHERE id= " + dto.getId() + ";";
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return storeQueryInFile(path, generateDynamicSqlScript(profileSetMapping.convertToDto(profileSet, ProfileSetDTO.class), dto));
    }

    public String generateDeleteSqlScript(Long id) {
        String sql = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM ProfileSet WHERE id = " + id + ";\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";
        String path = FILE_PATH + DATA_FILE_NAME;
        return this.storeQueryInFile(path, sql);
    }

    public String generateDeleteSqlScriptWithRollback(Long id) {
        ProfileSet profileSet = profileSetRepository.getProfileSetById(id);
        String sql = "INSERT INTO ProfileSet (createdBy, creationDate, de" +
                "scription, name, " +
                "updateState, fk_id_subIssuer) \n" +
                "VALUES ('" + profileSet.getCreatedBy() + "', '"
                + profileSet.getCreationDate() + "', '"
                + profileSet.getDescription() + "', '"
                + profileSet.getName() + "','"
                + profileSet.getUpdateState() + "',"
                + profileSet.getSubIssuer().getId() + ");";
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return storeQueryInFile(path, sql);
    }

    private String storeQueryInFile(String path, String sql) {

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
        return DATA_FILE_NAME;
    }

    private String generateDynamicSqlScript(ProfileSetDTO profileSetDTO, ProfileSetDTO newProfileSetDTO) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE ProfileSet SET ");
        if (newProfileSetDTO.getCreatedBy() != null && !newProfileSetDTO.getCreatedBy().isEmpty()) {
            if (profileSetDTO.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy ='").append(profileSetDTO.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newProfileSetDTO.getCreationDate() != null) {
            if (profileSetDTO.getCreationDate() == null) {
                sqlBuilder.append(prefix).append("creationDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate ='").append(profileSetDTO.getCreationDate()).append("'");
            }
            prefix = ", ";
        }
        if (newProfileSetDTO.getDescription() != null && !newProfileSetDTO.getDescription().isEmpty()) {
            if (profileSetDTO.getDescription() == null) {
                sqlBuilder.append(prefix).append("description =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description ='").append(profileSetDTO.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newProfileSetDTO.getLastUpdateBy() != null && !newProfileSetDTO.getLastUpdateBy().isEmpty()) {
            if (profileSetDTO.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy ='").append(profileSetDTO.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (newProfileSetDTO.getLastUpdateDate() != null) {
            if (profileSetDTO.getLastUpdateDate() == null) {
                sqlBuilder.append(prefix).append("lastUpdateDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateDate ='").append(profileSetDTO.getLastUpdateDate()).append("'");
            }
            prefix = ", ";
        }
        if (newProfileSetDTO.getName() != null && !newProfileSetDTO.getName().isEmpty()) {
            if (profileSetDTO.getName() == null) {
                sqlBuilder.append(prefix).append("name =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name ='").append(profileSetDTO.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newProfileSetDTO.getUpdateState() != null && !newProfileSetDTO.getUpdateState().isEmpty()) {
            if (profileSetDTO.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("updateState =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState ='").append(profileSetDTO.getUpdateState()).append("'");
            }
        }
        sqlBuilder.append(" WHERE id = ").append(profileSetDTO.getId()).append("; ");

        return sqlBuilder.toString();
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
