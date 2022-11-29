package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.dto.ProfileSetDTO;
import com.energizeglobal.sqlgenerator.service.ProfileSetService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profileset")
public class ProfileSetController {

    private final ProfileSetService profileSetService;

    public ProfileSetController(ProfileSetService profileSetService) {
        this.profileSetService = profileSetService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProfileSetDTO>> getAllProfileSets(){
        return ResponseEntity.ok(profileSetService.getAllProfileSets());
    }

    @GetMapping
    public ResponseEntity<Page<ProfileSetDTO>> getPagedProfileSets(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(profileSetService.getAllProfileSets(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileSetDTO> getProfileSetById(@PathVariable("id") Long id){
        return ResponseEntity.ok(profileSetService.getProfileSet(id));
    }

    @PostMapping
    public ResponseEntity<String> generateSqlScript(@RequestBody ProfileSetDTO profileSetDTO) {
        String fileName = profileSetService.generateInsertSqlScript(profileSetDTO);
        profileSetService.generateInsertSqlScriptWithRollback(profileSetDTO);
        return ResponseEntity.ok(fileName);
    }

    @PutMapping
    public ResponseEntity<String> editProfileSet(@RequestBody ProfileSetDTO dto){
        String fileName = profileSetService.generateEditSqlScript(dto);
        profileSetService.generateEditSqlScriptWithRollback(dto);
        return ResponseEntity.ok(fileName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProfileSet(@PathVariable("id") Long id){
        String fileName = profileSetService.generateDeleteSqlScript(id);
        profileSetService.generateDeleteSqlScriptWithRollback(id);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/script/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = profileSetService.downloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }
}
