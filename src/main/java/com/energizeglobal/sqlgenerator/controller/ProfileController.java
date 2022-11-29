package com.energizeglobal.sqlgenerator.controller;


import com.energizeglobal.sqlgenerator.domain.Profile;
import com.energizeglobal.sqlgenerator.dto.ProfileDTO;
import com.energizeglobal.sqlgenerator.service.ProfileService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ProfileDTO>> getAllProfiles(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(service.getAllProfiles(page, pageSize));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        return ResponseEntity.ok(service.getAllProfiles());
    }

    @PostMapping
    public ResponseEntity<String> generateSqlScript(@RequestBody ProfileDTO profileDTO) {
        String fileName = service.generateInsertSqlScript(profileDTO);
//      service.generateInsertSqlScriptWithRollback(profileDTO);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> show(@PathVariable("id") Long id) {
        Profile profile = this.service.findByProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<String> edit(@RequestBody ProfileDTO profileDTO) {
        String fileName = service.generateEditSqlScript(profileDTO);
        this.service.generateEditSqlScriptWithRollback(profileDTO);
        return ResponseEntity.ok(fileName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroy(@PathVariable("id") Long id) {
         String fileName = service.generateDeleteSqlScript(id);
         service.generateDeleteSqlScriptWithRollback(id);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/script/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = this.service.downloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }
}
