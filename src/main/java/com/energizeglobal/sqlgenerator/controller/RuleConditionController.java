package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.domain.RuleCondition;
import com.energizeglobal.sqlgenerator.dto.RuleConditionDTO;
import com.energizeglobal.sqlgenerator.service.RuleConditionService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/condition")
public class RuleConditionController {

    private final RuleConditionService ruleConditionService;

    public RuleConditionController(RuleConditionService ruleConditionService) {
        this.ruleConditionService = ruleConditionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleConditionDTO> getConditionById(@PathVariable Long id){
        RuleConditionDTO conditionDto = ruleConditionService.findById(id);
        return ResponseEntity.ok(conditionDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<RuleConditionDTO>> getAllConditions(){
        return ResponseEntity.ok(ruleConditionService.getAllConditions());
    }

    @GetMapping
    public ResponseEntity<Page<RuleConditionDTO>> getAllConditions(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(ruleConditionService.getAllConditions(page, pageSize));
    }

    @PostMapping
    public ResponseEntity<String> insertCondition(@RequestBody RuleConditionDTO conditionDto){
        String filename = ruleConditionService.generateInsertSqlScript(conditionDto);
        return ResponseEntity.ok(filename);
    }

    @PutMapping
    public ResponseEntity<String> updateCondition(@RequestBody RuleConditionDTO conditionDto){
        String filename = ruleConditionService.generateUpdateSqlScript(conditionDto);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping("/{id}/{ruleId}")
    public ResponseEntity deleteCondition(@PathVariable Long id, @PathVariable Long ruleId){
        String filename = ruleConditionService.generateDeleteSqlScript(id, ruleId);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/script/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = ruleConditionService.downloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }

}
