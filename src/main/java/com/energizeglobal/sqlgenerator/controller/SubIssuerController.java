package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.dto.SubIssuerDTO;
import com.energizeglobal.sqlgenerator.service.SubIssuerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/subissuer")
public class SubIssuerController {

    private final Logger log = LoggerFactory.getLogger(SubIssuerController.class);

    private final SubIssuerService subIssuerService;

    public SubIssuerController(SubIssuerService subIssuerServiceImpl) {
        this.subIssuerService = subIssuerServiceImpl;
    }

    @GetMapping
    public ResponseEntity<Page<SubIssuerDTO>> getAllSubIssuer(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(subIssuerService.getAllSubIssuer(page, pageSize));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SubIssuerDTO>> getAllSubIssuers(){
        return ResponseEntity.ok(subIssuerService.getAllSubIssuer());
    }

    @GetMapping("/{code}")
    public ResponseEntity<SubIssuerDTO> getByCodeSubIssuer(@PathVariable("code") String code) {
        return ResponseEntity.ok(subIssuerService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<String> generateSqlScript(@RequestBody SubIssuerDTO subIssuerDTO) {
        String filename = subIssuerService.generateInsertSqlScript(subIssuerDTO);
        return ResponseEntity.ok(filename);
    }

    @PutMapping
    public ResponseEntity<String> updateSubissuer(@RequestBody SubIssuerDTO subIssuerDto) {
        String filename = subIssuerService.generateUpdateSqlScript(subIssuerDto);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity deleteSubIssuerByCode(@PathVariable String code) {
        String filename = subIssuerService.generateDeleteSqlScript(code);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/script/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = subIssuerService.downloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }
}

