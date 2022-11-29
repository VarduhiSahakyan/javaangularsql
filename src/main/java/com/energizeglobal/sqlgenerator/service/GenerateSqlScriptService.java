package com.energizeglobal.sqlgenerator.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class GenerateSqlScriptService {

    public void insertSqlScript(String insertQuery, String fileName) {

        String line = "-- -------------------------U5G_ACS_BO-------------------------------";
        String dbName = "USE `U5G_ACS_BO`;";
        String dateTime = "-- " + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        String FILE_PATH = "src/main/resources/sql_scripts/";
        String path = FILE_PATH + fileName;

        Path newFilePath = Paths.get(path);
        try {
            if (Files.exists(newFilePath)) {
                dateTime = System.getProperty("line.separator") + dateTime;
                insertQuery = System.getProperty("line.separator") + insertQuery;
                String data = dateTime + insertQuery;
                Files.write(newFilePath, data.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            } else {
                Path fileDirectory = Paths.get(FILE_PATH);
                Files.createDirectories(fileDirectory);
                dbName = System.getProperty("line.separator") + dbName;
                dateTime = System.getProperty("line.separator") + dateTime;
                insertQuery = System.getProperty("line.separator") + insertQuery;
                String data = System.getProperty("line.separator") + line + dbName + dateTime + insertQuery;
                Files.write(newFilePath, data.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException: " + e.getMessage());
        }
    }
}
