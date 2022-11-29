package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.ImageEntity;
import com.energizeglobal.sqlgenerator.dto.ImageDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;

@Service
public class ImageRollbackService {
    String FILE_PATH = "src/main/resources/sql_scripts/";
    String ROLLBACK_FILE_NAME = "image_rollback.sql";
    String path = FILE_PATH + ROLLBACK_FILE_NAME;
    private final Mapping imageMapper;

    public ImageRollbackService(Mapping imageMapper) {
        this.imageMapper = imageMapper;
    }

    public String generateSqlScriptForInsertRollback(String name) {

        String deleteQuery = "\n" +
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM Image WHERE name = '" + name + "';\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        pathGenerator(deleteQuery);

        return ROLLBACK_FILE_NAME;
    }

    public String generateSqlScriptForUpdateRollback(ImageEntity oldImage, ImageDTO newImage) {

//        generateDynamicSqlUpdateScript(ImageMapper.entityToDto(oldImage));
        String queryUpdate = "UPDATE Image SET " +
                "createdBy = '" + oldImage.getCreatedBy() + "', " +
                "creationDate = '" + oldImage.getCreationDate() + "', " +
                "description = '" + oldImage.getDescription() + "', " +
                "lastUpdateBy = '" + oldImage.getLastUpdateBy() + "', " +
                "lastUpdateDate = '" + oldImage.getLastUpdateDate() + "', " +
                "name = '" + oldImage.getName() + "', " +
                "updateState = '" + oldImage.getUpdateState() + "', " +
                "binaryData = '" + oldImage.getBinaryData() + "', " +
                "relativePath = '" + oldImage.getRelativePath() + "' " +
                " WHERE id = " + oldImage.getId() + ";";

        pathGenerator(generateDynamicSqlUpdateScript(imageMapper.convertToDto(oldImage, ImageDTO.class), newImage));

        return ROLLBACK_FILE_NAME;
    }

    private String generateDynamicSqlUpdateScript(ImageDTO imageDTO, ImageDTO newImage) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE Image SET ");
        if (newImage.getCreatedBy() != null && !newImage.getCreatedBy().isEmpty()) {
            if (imageDTO.getCreatedBy() == null) {
                sqlBuilder.append(prefix).append("createdBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("createdBy ='").append(imageDTO.getCreatedBy()).append("'");
            }
            prefix = ", ";
        }
        if (newImage.getCreationDate() != null) {
            if (imageDTO.getCreationDate() == null) {
                sqlBuilder.append(prefix).append("creationDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("creationDate ='").append(imageDTO.getCreationDate()).append("'");
            }
            prefix = ", ";
        }
        if (newImage.getDescription() != null && !newImage.getDescription().isEmpty()) {
            if (imageDTO.getDescription() == null) {
                sqlBuilder.append(prefix).append("description =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description ='").append(imageDTO.getDescription()).append("'");
            }
            prefix = ", ";
        }
        if (newImage.getLastUpdateBy() != null && !newImage.getLastUpdateBy().isEmpty()) {
            if (imageDTO.getLastUpdateBy() == null) {
                sqlBuilder.append(prefix).append("lastUpdateBy =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateBy ='").append(imageDTO.getLastUpdateBy()).append("'");
            }
            prefix = ", ";
        }
        if (newImage.getLastUpdateDate() != null) {
            if (imageDTO.getLastUpdateDate() == null) {
                sqlBuilder.append(prefix).append("lastUpdateDate =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("lastUpdateDate ='").append(imageDTO.getLastUpdateDate()).append("'");
            }
            prefix = ", ";
        }
        if (newImage.getName() != null && !newImage.getName().isEmpty()) {
            if (imageDTO.getName() == null) {
                sqlBuilder.append(prefix).append("name =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("name ='").append(imageDTO.getName()).append("'");
            }
            prefix = ", ";
        }
        if (newImage.getUpdateState() != null) {
            if (imageDTO.getUpdateState() == null) {
                sqlBuilder.append(prefix).append("updateState =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("updateState ='").append(imageDTO.getUpdateState()).append("'");
            }
            prefix = ", ";
        }
        if (newImage.getBinaryData() != null && !newImage.getBinaryData().isEmpty()) {
            if (imageDTO.getBinaryData() == null) {
                sqlBuilder.append(prefix).append("binaryData =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("binaryData ='").append(imageDTO.getBinaryData()).append("'");
            }
            prefix = ", ";
        }
        if (newImage.getRelativePath() != null && !newImage.getRelativePath().isEmpty()) {
            if (imageDTO.getRelativePath() == null) {
                sqlBuilder.append(prefix).append("relativePath =").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("relativePath ='").append(imageDTO.getRelativePath()).append("' ");
            }
        }
        sqlBuilder.append("WHERE id = " + imageDTO.getId()).append(";");

        return sqlBuilder.toString();
    }

    public String generateSqlScriptForDeleteRollback(ImageDTO image) {

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
                image.getCreationDate() + "', '" +
                image.getDescription() + "', '" +
                image.getLastUpdateBy() + "', '" +
                image.getName() + "', '" +
                image.getUpdateState() + "', '" +
                image.getBinaryData() + "', '" +
                image.getRelativePath() + "');";

        String sqlInsert = queryType + queryValue;

        pathGenerator(sqlInsert);

        return ROLLBACK_FILE_NAME;
    }

    private void pathGenerator(String sql) {
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
    }
}
