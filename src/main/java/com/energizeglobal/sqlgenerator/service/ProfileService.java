package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.Profile;
import com.energizeglobal.sqlgenerator.dto.ProfileDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.repository.AuthentMeansRepository;
import com.energizeglobal.sqlgenerator.repository.ProfileRepository;
import com.energizeglobal.sqlgenerator.repository.SubIssuerRepository;
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
@CacheConfig(cacheNames = "profile")
public class ProfileService {

    private final String FILE_PATH = "src/main/resources/sql_scripts/";
    private final String DATA_FILE_NAME = "profile.sql";
    private final String DATA_ROLLBACK_FILE_NAME = "profile_rollback.sql";

    private final Mapping profileMapper;
    private ProfileRepository repository;
    private AuthentMeansRepository authentMeansRepository;
    private SubIssuerRepository subIssuerRepository;
    private Timestamp thisMomentTime = new Timestamp(System.currentTimeMillis());

    public ProfileService(
            Mapping profileMapper, ProfileRepository repository,
            AuthentMeansRepository authentMeansRepository,
            SubIssuerRepository subIssuerRepository
    ) {
        this.profileMapper = profileMapper;
        this.repository = repository;
        this.authentMeansRepository = authentMeansRepository;
        this.subIssuerRepository = subIssuerRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<ProfileDTO> getAllProfiles(Integer page, Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        return new PageImpl<>(profileMapper.mapList(repository.findAll(paging), ProfileDTO.class), paging, repository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<ProfileDTO> getAllProfiles() {
        return (profileMapper.mapList(repository.findAll(), ProfileDTO.class));
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Profile findByProfileById(long id) {
        Profile profile = this.repository.getProfileById(id);
        return profile;
    }

    public String generateInsertSqlScript(ProfileDTO dto) {
        String Insert = "INSERT INTO Profile (createdBy, creationDate, description, name, updateState, maxAttempts," +
                "dataEntryFormat, dataEntryAllowedPattern, fk_id_authentMeans, fk_id_subIssuer) \n" +
                "VALUES ('" + dto.getCreatedBy() + "', '" + thisMomentTime + "','" + dto.getDescription() + "', '"
                + dto.getName() + "','" + dto.getUpdateState() + "','" + dto.getMaxAttempts() + "','"
                + dto.getDataEntryFormat() + "','" + dto.getDataEntryAllowedPattern() + "', " + dto.getAuthentMeansId() + ", "
                + dto.getSubIssuerId() + ");";

        generateSqlScriptForInsertRollback(dto.getId());
        String path = FILE_PATH + DATA_FILE_NAME;
        return this.storeQueryInFile(path, Insert);
    }

    public String generateInsertSqlScriptWith2(ProfileDTO dto) {
        String delete = "DELETE FROM Profile WHERE name ='" + dto.getName() + "';";
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return this.storeQueryInFile(path, delete);
    }

    public String generateEditSqlScript(ProfileDTO dto) {

        String sql = "UPDATE Profile SET " +
                "createdBy='" + dto.getCreatedBy() + "'," +
                "creationDate='" + dto.getCreationDate() + "'," +
                "description='" + dto.getDescription() + "'," +
                "lastUpdateBy='" + dto.getLastUpdateBy() + "'," +
                "name='" + dto.getName() + "'," +
                "updateState='" + dto.getUpdateState() + "', " +
                "maxAttempts='" + dto.getMaxAttempts() + "', " +
                "dataEntryFormat='" + dto.getDataEntryFormat() + "', " +
                "dataEntryAllowedPattern='" + dto.getDataEntryAllowedPattern() + "'" +
                " WHERE id=" + dto.getId() + " ;";
        String path = FILE_PATH + DATA_FILE_NAME;
        return this.storeQueryInFile(path, generateDynamicSqlScript(dto, dto));
    }

    public String generateEditSqlScriptWithRollback(ProfileDTO dto) {
        Profile profile = this.repository.getProfileById(dto.getId());


        String sql = "UPDATE Profile SET " +
                "createdBy='" + profile.getCreatedBy() + "'," +
                "creationDate='" + profile.getCreationDate() + "'," +
                "description='" + profile.getDescription() + "'," +
                "lastUpdateBy='" + profile.getLastUpdateBy() + "'," +
                "name='" + profile.getName() + "'," +
                "updateState='" + profile.getUpdateState() + "', " +
                "maxAttempts='" + profile.getMaxAttempts() + "', " +
                "dataEntryFormat='" + profile.getDataEntryFormat() + "', " +
                "dataEntryAllowedPattern='" + profile.getDataEntryAllowedPattern() + "' " +
                " WHERE id= " + dto.getId() + " ;";
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return this.storeQueryInFile(path, generateDynamicSqlScript(profileMapper.convertToDto(profile, ProfileDTO.class), dto));
    }

    public String generateDeleteSqlScript(long id) {
        String sql = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM Profile WHERE id = " + id + " ; \n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";
        String path = FILE_PATH + DATA_FILE_NAME;
        return this.storeQueryInFile(path, sql);
    }

    public String generateDeleteSqlScriptWithRollback(long id) {
        Profile profile = this.repository.getProfileById(id);
        String sql = "INSERT INTO Profile (createdBy, creationDate, description, lastUpdateBy ,name, " +
                "updateState, maxAttempts, dataEntryFormat, dataEntryAllowedPattern, " +
                "fk_id_authentMeans, fk_id_subIssuer)\n" +
                " VALUES ('" + profile.getCreatedBy() + "', '" + profile.getCreationDate() + "','"
                + profile.getDescription() + "', '" + profile.getLastUpdateBy() + "', '"
                + profile.getName() + "','" + profile.getUpdateState() + "','" + profile.getMaxAttempts() + "','"
                + profile.getDataEntryFormat() + "','" + profile.getDataEntryAllowedPattern() + "',"
                + profile.getAuthentMeans().getId() + "," + profile.getSubIssuer().getId() + ");";
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return this.storeQueryInFile(path, sql);
    }

    private String generateSqlScriptForInsertRollback(Long id) {

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM Profile WHERE id = " + id + ";\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return this.storeQueryInFile(path, deleteQuery);
    }


    private String generateDynamicSqlScript(ProfileDTO dto, ProfileDTO newDto) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE Profile SET ");
        if (newDto.getCreatedBy() != null && !newDto.getCreatedBy().isEmpty()) {
            if (dto.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy ='").append(dto.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getCreationDate() != null) {
            if (dto.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("creationDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate ='").append(dto.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getDescription() != null && !newDto.getDescription().isEmpty()) {
            if (dto.getDescription() == null) {
                sqlBuilder.append(prefix).append("description =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description ='").append(dto.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getLastUpdateBy() != null && !newDto.getLastUpdateBy().isEmpty()) {
            if (dto.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy ='").append(dto.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getName() != null && !newDto.getName().isEmpty()) {
            if (dto.getName() == null) {
                sqlBuilder.append(prefix).append("name =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name ='").append(dto.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getUpdateState() != null && !newDto.getUpdateState().isEmpty()) {
            if (dto.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("updateState =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState ='").append(dto.getUpdateState()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getMaxAttempts() > 0) {
            sqlBuilder.append(prefix).append("maxAttempts ='").append(dto.getMaxAttempts()).append("'");
            prefix = ", ";
        }
        if (newDto.getDataEntryFormat() != null && !newDto.getDataEntryFormat().isEmpty()) {
            if (dto.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("dataEntryFormat =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("dataEntryFormat ='").append(dto.getUpdateState()).append("'");
            }
            prefix = ", ";
        }
        if (newDto.getDataEntryAllowedPattern() != null && !newDto.getDataEntryAllowedPattern().isEmpty()) {
            if (dto.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("dataEntryAllowedPattern =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("dataEntryAllowedPattern ='").append(dto.getUpdateState()).append("' ");
            }
        }
        sqlBuilder.append("WHERE id = ").append(dto.getId()).append(" ;");

        return sqlBuilder.toString();
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
