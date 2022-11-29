package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.dto.AuthentMeansDTO;
import com.energizeglobal.sqlgenerator.service.AuthentMeansService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authent")
public class AuthentMeansController {

    private final AuthentMeansService authentMeansService;

    public AuthentMeansController(AuthentMeansService authentMeansService) {
        this.authentMeansService = authentMeansService;
    }

    @GetMapping("/list")
    private ResponseEntity<List<AuthentMeansDTO>> getAllAuthentMeans() {
        return ResponseEntity.ok(authentMeansService.getAllAuthentMeans());
    }

    @GetMapping
    public ResponseEntity<Page<AuthentMeansDTO>> getPagedAuthentMeans(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(authentMeansService.getPagedAuthentMeans(page, pageSize));
    }
    @GetMapping("/{id}")
    private ResponseEntity<AuthentMeansDTO> getByIdAuthentMean(@PathVariable Long id) {
        AuthentMeansDTO authentMeansDTO = authentMeansService.getByIdAuthentMean(id);
        return ResponseEntity.ok(authentMeansDTO);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteAuthentMeanById(@PathVariable Long id) {
        String filename = authentMeansService.deleteAuthentMeanById(id);
        return ResponseEntity.ok(filename);
    }

    @PostMapping()
    public ResponseEntity<String> saveAuthentMean(@RequestBody AuthentMeansDTO authentMeansDTO) {
        String filename = authentMeansService.saveAuthentMean(authentMeansDTO);
        return ResponseEntity.ok(filename);
    }

    @PutMapping()
    public ResponseEntity<String> updateAuthentMean(@RequestBody AuthentMeansDTO authentMeansDTO) {
        String filename = authentMeansService.updateAuthentMean(authentMeansDTO);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/script/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = authentMeansService.getDownloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }
}
