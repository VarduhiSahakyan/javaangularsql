package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.dto.IssuerDTO;
import com.energizeglobal.sqlgenerator.service.IssuerService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/issuers")
public class IssuerController {

    private final IssuerService issuerService;

    public IssuerController(IssuerService issuerService) {
        this.issuerService = issuerService;
    }

    @GetMapping
    public ResponseEntity<Page<IssuerDTO>> getAllIssuer(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(issuerService.getAllIssuer(page, pageSize));
    }

    @GetMapping("/list")
    public ResponseEntity<List<IssuerDTO>> getAllIssuers(){
        return ResponseEntity.ok(issuerService.getAllIssuers());
    }

    @PostMapping
    public ResponseEntity<String> generateSqlScript(@RequestBody IssuerDTO issuerDTO) {
        String fileName = issuerService.generateInsertSqlScript(issuerDTO);
        issuerService.generateInsertSqlScriptWithRollback(issuerDTO);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/{code}")
    public ResponseEntity<IssuerDTO> show(@PathVariable("code") String someCode) {
        return ResponseEntity.ok(issuerService.findByIssuerByCode(someCode));
    }

    @PutMapping()
    public ResponseEntity<String> edit(@RequestBody IssuerDTO issuerDTO) {
        String fileName = issuerService.generateEditSqlScript(issuerDTO);
        issuerService.generateEditSqlScriptWithRollback(issuerDTO);
        return ResponseEntity.ok(fileName);
    }

    @DeleteMapping("/{issuerCode}")
    public ResponseEntity destroy(@PathVariable("issuerCode") String issuerCode) {
        String fileName = issuerService.generateDeleteSqlScript(issuerCode);
        issuerService.generateDeleteSqlScriptWithRollback(issuerCode);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/script/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = issuerService.downloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }

}
