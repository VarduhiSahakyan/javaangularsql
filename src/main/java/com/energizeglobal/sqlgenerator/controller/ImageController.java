package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.dto.ImageDTO;
import com.energizeglobal.sqlgenerator.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<Page<ImageDTO>> getAllImage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(imageService.getAllImages(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable("id") Long id) {
        ImageDTO imageDto = imageService.findById(id);
        return ResponseEntity.ok(imageDto);

    }

    @PostMapping
    public ResponseEntity<String> generateSqlScript(@RequestBody ImageDTO imageDto) {
        String filename = imageService.generateInsertSqlScript(imageDto);
        return ResponseEntity.ok(filename);
    }

    @PutMapping
    public ResponseEntity<String> updateImage(@RequestBody ImageDTO imageDto) {
        String filename = imageService.generateUpdateSqlScript(imageDto);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        ImageDTO imageDTO = imageService.findById(id);
        String filename = imageService.generateDeleteSqlScript(imageDTO.getName());
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/script/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = imageService.downloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }

}
