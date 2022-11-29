package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.ImageEntity;
import com.energizeglobal.sqlgenerator.dto.ImageDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.repository.ImageRepository;
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
@CacheConfig(cacheNames = "image")
public class ImageService {

    private Boolean dbAction = false;
    private final ImageRepository imageRepository;
    private final ImageRollbackService imageRollbackService;
    private final Mapping imageMapper;

    private String FILE_PATH = "src/main/resources/sql_scripts/";
    private String INSERT_FILE_NAME = "image.sql";
    private String path = FILE_PATH + INSERT_FILE_NAME;
    private Timestamp thisMomentTime = new Timestamp(System.currentTimeMillis());


    public ImageService(ImageRepository imageRepository, ImageRollbackService imageRollbackService, Mapping imageMapper) {
        this.imageRepository = imageRepository;
        this.imageRollbackService = imageRollbackService;
        this.imageMapper = imageMapper;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<ImageDTO> getAllImages(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return new PageImpl<>(imageMapper.mapList(imageRepository.findAll(paging),ImageDTO.class), paging, imageRepository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public ImageDTO findById(Long id) {
        ImageEntity imageEntity = imageRepository.getById(id);
        return imageMapper.convertToDto(imageEntity, ImageDTO.class);
    }

    @Transactional(readOnly = true)
    public ImageDTO findByName(String name) {
        ImageEntity imageEntity = imageRepository.findByName(name);
        return imageMapper.convertToDto(imageEntity, ImageDTO.class);
    }

    @Transactional
    public String generateInsertSqlScript(ImageDTO imageDto) {
        ImageEntity image = imageMapper.convertToEntity(imageDto, ImageEntity.class);
        image.setCreationDate(thisMomentTime);

        String queryType = "INSERT INTO Image  ( " +
                "createdBy, " +
                "creationDate, " +
                "description, " +
                "lastUpdateBy, " +
                "name, " +
                "updateState, " +
                "binaryData, " +
                "relativePath )";

        String queryValue = "  VALUES (" + " '" +
                image.getCreatedBy() + "', '" +
                thisMomentTime + "', '" +
                image.getDescription() + "', '" +
                image.getLastUpdateBy() + "', '" +
                image.getName() + "', '" +
                image.getUpdateState() + "', '" +
                image.getBinaryData() + "', '" +
                image.getRelativePath() + "');";

        String sqlInsert = queryType + queryValue;

        insertPathGenerator(sqlInsert);

        imageRollbackService.generateSqlScriptForInsertRollback(image.getName());

        if (dbAction)
            imageRepository.save(image);

        return INSERT_FILE_NAME;
    }

    @Transactional
    public String generateUpdateSqlScript(ImageDTO imageDto) {
        ImageEntity oldImage = imageRepository.getById(imageDto.getId());
        ImageEntity newImage = imageMapper.convertToEntity(imageDto, ImageEntity.class);
        newImage.setId(oldImage.getId());
        newImage.setCreationDate(imageDto.getCreationDate());
        newImage.setLastUpdateDate(thisMomentTime);
        String generatedScript = generateDynamicSqlUpdateScript(imageDto);
        String queryUpdate = "UPDATE Image SET " +
                "createdBy = '" + imageDto.getCreatedBy() + "', " +
                "creationDate = '" + imageDto.getCreationDate() + "', " +
                "description = '" + imageDto.getDescription() + "', " +
                "lastUpdateBy = '" + imageDto.getLastUpdateBy() + "', " +
                "lastUpdateDate = '" + thisMomentTime + "', " +
                "name = '" + imageDto.getName() + "', " +
                "updateState = '" + imageDto.getUpdateState() + "', " +
                "binaryData = '" + imageDto.getBinaryData() + "', " +
                "relativePath = '" + imageDto.getRelativePath() + "' " +
                " WHERE id = " + imageDto.getId() + ";";

        insertPathGenerator(generatedScript);

        imageRollbackService.generateSqlScriptForUpdateRollback(oldImage,imageDto);
        if (dbAction)
            imageRepository.save(newImage);

        return INSERT_FILE_NAME;
    }

    @Transactional
    public String generateDeleteSqlScript(String name) {

        ImageDTO image = findByName(name);

        System.out.println(image);

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM Image WHERE name = '" + name + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";
        insertPathGenerator(deleteQuery);

        imageRollbackService.generateSqlScriptForDeleteRollback(image);

        if (dbAction)
            imageRepository.deleteById(image.getId());

        return INSERT_FILE_NAME;
    }


    private String generateDynamicSqlUpdateScript(ImageDTO imageDTO) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE Image SET ");
        if (imageDTO.getCreatedBy() != null && !imageDTO.getCreatedBy().isEmpty()) {
            sqlBuilder.append(prefix).append("createdBy ='").append(imageDTO.getCreatedBy()).append("'");
            prefix = ", ";
        }
        if (imageDTO.getCreationDate() != null) {
            sqlBuilder.append(prefix).append(prefix).append("creationDate =' ").append(imageDTO.getCreationDate()).append("'");
            prefix = ", ";
        }
        if (imageDTO.getDescription() != null && !imageDTO.getDescription().isEmpty()) {
            sqlBuilder.append(prefix).append("description ='").append(imageDTO.getDescription()).append("'");
            prefix = ", ";
        }
        if (imageDTO.getLastUpdateBy() != null && !imageDTO.getLastUpdateBy().isEmpty()) {
            sqlBuilder.append(prefix).append("lastUpdateBy ='").append(imageDTO.getLastUpdateBy()).append("'");
            prefix = ", ";
        }
        if (thisMomentTime != null) {
            sqlBuilder.append(prefix).append("lastUpdateDate ='").append(thisMomentTime).append("'");
            prefix = ", ";
        }
        if (imageDTO.getName() != null && !imageDTO.getName().isEmpty()) {
            sqlBuilder.append(prefix).append("name ='").append(imageDTO.getName()).append("'");
            prefix = ", ";
        }
        if (imageDTO.getUpdateState() != null) {
            sqlBuilder.append(prefix).append("updateState ='").append(imageDTO.getUpdateState()).append("'");
            prefix = ", ";
        }
        if (imageDTO.getBinaryData() != null && !imageDTO.getBinaryData().isEmpty()) {
            sqlBuilder.append(prefix).append("binaryData ='").append(imageDTO.getBinaryData()).append("'");
            prefix = ", ";
        }
        if (imageDTO.getRelativePath() != null && !imageDTO.getRelativePath().isEmpty()) {
            sqlBuilder.append(prefix).append("relativePath ='").append(imageDTO.getRelativePath()).append("' ");
        }
        sqlBuilder.append("WHERE id = ").append(imageDTO.getId()).append(";");

        return sqlBuilder.toString();
    }

    private void insertPathGenerator(String sql) {

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
