package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.domain.RuleEntity;
import com.energizeglobal.sqlgenerator.dto.RuleDTO;
import com.energizeglobal.sqlgenerator.dto.SubIssuerDTO;
import com.energizeglobal.sqlgenerator.service.RuleService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDTO> getRuleById(@PathVariable Long id) {
        RuleDTO ruleDTO = ruleService.findById(id);
        return ResponseEntity.ok(ruleDTO);
    }

    @GetMapping
    public ResponseEntity<Page<RuleDTO>> getAllRules(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(ruleService.getAllRules(page, pageSize));
    }

    @GetMapping("/list")
    public ResponseEntity<List<RuleDTO>> getAllRules() {
        List<RuleDTO> rulesList = ruleService.getAllRules();
        return ResponseEntity.ok(rulesList);
    }

    @PostMapping
    public ResponseEntity<String> generateSqlScript(@RequestBody RuleDTO ruleDto) {
        String filename = ruleService.generateInsertSqlScript(ruleDto);
        return ResponseEntity.ok(filename);
    }

    @PutMapping
    public ResponseEntity<String> updateRule(@RequestBody RuleDTO ruleDto) {
        String filename = ruleService.generateUpdateSqlScript(ruleDto);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        String filename = ruleService.generateDeleteSqlScript(id);
        return ResponseEntity.ok(filename);

    }

    @GetMapping("/script/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = ruleService.downloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }

}
