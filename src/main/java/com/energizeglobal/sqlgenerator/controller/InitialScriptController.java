package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.service.InitialScriptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;

@RestController
@RequestMapping("/initial")
public class InitialScriptController {

    private String FILE_PATH = "src/main/resources/sql_scripts/";
//    private String INSERT_FILE_NAME = "init-ACS-BO.sql";
//    private String path = FILE_PATH + INSERT_FILE_NAME;

    private InitialScriptService initialScriptService;
    private TemplateEngine textTemplateEngine;

    public InitialScriptController(InitialScriptService initialScriptService, TemplateEngine textTemplateEngine) {
        this.initialScriptService = initialScriptService;
        this.textTemplateEngine = textTemplateEngine;
    }

    @PostMapping("/script1")
    public ResponseEntity<String> generateInitialScript01(){

        String sql = initialScriptService.generateInitialScript01();
        writeToFile("01_init_script.sql", sql);
        return ResponseEntity.ok(sql);
    }

    @PostMapping("/script2")
    public ResponseEntity<String> generateInitialScript02(){

        String sql = initialScriptService.generateInitialScript02();
        writeToFile("02_init_script.sql", sql);
        return ResponseEntity.ok(sql);
    }

//    @PostMapping("/script/sql/init/json")
//    public ResponseEntity<String> generateSqlScriptFromJson() {
//
//        StringBuilder generatedSqlScript = new StringBuilder();
////        Map<String, String> generatedSqlMap = sqlGeneratorService.generateSqlScript();
//        Map<String, String> generatedSqlMap = initialScriptService.generateSqlScriptFromJson();
//
//        generatedSqlScript.append(generatedSqlMap.get("general"));
//        generatedSqlScript.append(generatedSqlMap.get("issuer"));
//        generatedSqlScript.append(generatedSqlMap.get("crypto"));
//        generatedSqlScript.append(generatedSqlMap.get("subissuer"));
//        generatedSqlScript.append(generatedSqlMap.get("profileSet"));
//        generatedSqlScript.append(generatedSqlMap.get("binRange"));
//        generatedSqlScript.append(generatedSqlMap.get("customPageLayout"));
//        generatedSqlScript.append(generatedSqlMap.get("customPageLayoutProfileSet"));
//        generatedSqlScript.append(generatedSqlMap.get("image"));
//        generatedSqlScript.append(generatedSqlMap.get("customItemSet"));
//        generatedSqlScript.append(generatedSqlMap.get("profile"));
//        generatedSqlScript.append(generatedSqlMap.get("rule"));
//        generatedSqlScript.append(generatedSqlMap.get("ruleCondition"));
//        generatedSqlScript.append(generatedSqlMap.get("merchantPivotList"));
//        generatedSqlScript.append(generatedSqlMap.get("conditionMeansProcess"));
//        generatedSqlScript.append(generatedSqlMap.get("transactionStatus"));
//
//        writeToFile("init-script-json.sql", generatedSqlScript.toString());
//
//        return ResponseEntity.ok(generatedSqlScript.toString());
//    }

    private void writeToFile(String fileName, String sql) {

        String path = FILE_PATH+fileName;
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
                    bufferedWriter.write(sql);
                }
            } 
        } catch (IOException e) {
            throw new RuntimeException("IOException: " + e.getMessage());
        }
    }
}
