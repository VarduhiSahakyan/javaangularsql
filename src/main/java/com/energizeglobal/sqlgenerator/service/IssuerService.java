package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.Issuer;
import com.energizeglobal.sqlgenerator.dto.IssuerDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.repository.IssuerRepository;
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
@CacheConfig(cacheNames = "issuer")
public class IssuerService {

    private final String FILE_PATH = "src/main/resources/sql_scripts/";
    private final String DATA_FILE_NAME = "issuer.sql";
    private final String DATA_ROLLBACK_FILE_NAME = "issuer_rollback.sql";
    private Timestamp thisMomentTime = new Timestamp(System.currentTimeMillis());
    private final IssuerRepository issuerRepository;
    private final  Mapping issuerMapping;

    public IssuerService(IssuerRepository issuerRepository, Mapping issuerMapping) {
        this.issuerRepository = issuerRepository;
        this.issuerMapping = issuerMapping;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<IssuerDTO> getAllIssuer(Integer page, Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        return new PageImpl<>(issuerMapping.mapList(issuerRepository.findAll(paging), IssuerDTO.class), paging, issuerRepository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<IssuerDTO> getAllIssuers() {
        return issuerMapping.mapList(issuerRepository.findAll(), IssuerDTO.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public IssuerDTO findByIssuerByCode(String code) {
        return issuerMapping.convertToDto(issuerRepository.getIssuerByCode(code), IssuerDTO.class);
    }

    public String generateInsertSqlScript(IssuerDTO dto) {
        String Insert = "INSERT INTO Issuer (code, createdBy,creationDate, name, updateState,label, availaibleAuthentMeans)\n " +
                "VALUES ('" + dto.getCode() + "', '" + dto.getCreatedBy() + "','" + thisMomentTime + "','" + dto.getName() + "','" + dto.getUpdateState() + "','" + dto.getLabel() + "','" + dto.getAvailaibleAuthentMeans() + "');";
        String path = FILE_PATH + DATA_FILE_NAME;
        return this.storeQueryInFile(path, Insert);
    }

    public String generateInsertSqlScriptWithRollback(IssuerDTO dto) {
        String delete = "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM Issuer WHERE code = '" + dto.getCode() + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return this.storeQueryInFile(path, delete);
    }

    public String generateEditSqlScript(IssuerDTO dto) {
        String sqlScript = getGenerateDynamicSqlScript(dto,dto);


        String path = FILE_PATH + DATA_FILE_NAME;
        return this.storeQueryInFile(path, sqlScript);
    }

    private String getGenerateDynamicSqlScript(IssuerDTO dto,IssuerDTO newIssuerDto) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE Issuer SET ");
        if (newIssuerDto.getCreatedBy() != null && !newIssuerDto.getCreatedBy().isEmpty()) {
            sqlBuilder.append(prefix).append("createdBy = '").append(dto.getCreatedBy()).append( "'");
            prefix = ", ";
        }
        if (newIssuerDto.getCreationDate() != null) {
            sqlBuilder.append(prefix).append("creationDate = '").append(dto.getCreationDate()).append( "'");
            prefix = ", ";
        }
        if (thisMomentTime != null ) {
            sqlBuilder.append(prefix).append("lastUpdateDate = '").append(thisMomentTime).append("'");
            prefix = ", ";
        }
        if (newIssuerDto.getName() != null && !newIssuerDto.getName().isEmpty()) {
            sqlBuilder.append(prefix).append("name = '").append(dto.getName()).append( "'");
            prefix = ", ";
        }
        if (newIssuerDto.getUpdateState() != null && !newIssuerDto.getUpdateState().isEmpty()) {
            sqlBuilder.append(prefix).append("updateState = '").append(dto.getUpdateState()).append( "'");
            prefix = ", ";
        }
        if (newIssuerDto.getLabel() != null && !newIssuerDto.getLabel().isEmpty()) {
            sqlBuilder.append(prefix).append("label = '").append(dto.getLabel()).append( "'");
            prefix = ", ";
        }
        if (newIssuerDto.getAvailaibleAuthentMeans() != null && !newIssuerDto.getAvailaibleAuthentMeans().isEmpty()) {
            sqlBuilder.append(prefix).append("availaibleAuthentMeans = '").append(dto.getAvailaibleAuthentMeans()).append( "'");
            prefix = ", ";
        }
        if (newIssuerDto.getDescription() != null && !newIssuerDto.getDescription().isEmpty()) {
            if(dto.getDescription() == null) {
                sqlBuilder.append(prefix).append("description = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description = '").append(dto.getDescription()).append("' ");
            }
        }
        sqlBuilder.append("WHERE code = '").append(dto.getCode()).append("';");
        return sqlBuilder.toString();
    }

    public String generateEditSqlScriptWithRollback(IssuerDTO issuerDTO) {
        Issuer issuer = this.issuerRepository.getIssuerByCode(issuerDTO.getCode());
        String sql = getGenerateDynamicSqlScript(issuerMapping.convertToDto(issuer,IssuerDTO.class),issuerDTO);
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return this.storeQueryInFile(path, sql);
    }

    public String generateDeleteSqlScript(String issuerCode) {

        String sql = "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM Issuer WHERE code = '" + issuerCode + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        String path = FILE_PATH + DATA_FILE_NAME;
        return this.storeQueryInFile(path, sql);
    }

    public String generateDeleteSqlScriptWithRollback(String code) {
        Issuer issuer = this.issuerRepository.getIssuerByCode(code);
        String sql = "INSERT INTO Issuer (code, createdBy,creationDate, name, updateState,label,availaibleAuthentMeans) " +
                "VALUES ('" + issuer.getCode() + "', '" + issuer.getCreatedBy() + "','" + issuer.getCreationDate() + "','" + issuer.getName() + "','" + issuer.getUpdateState() + "','" + issuer.getLabel() + "','" + issuer.getAvailaibleAuthentMeans() + "');";
        String path = FILE_PATH + DATA_ROLLBACK_FILE_NAME;
        return this.storeQueryInFile(path, sql);
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
                dbName = System.getProperty("line.separator") + dbName;
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
