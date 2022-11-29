package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.domain.CustomItemEntity;
import com.energizeglobal.sqlgenerator.dto.CustomItemDTO;
import com.energizeglobal.sqlgenerator.service.CustomItemService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customitem")
public class CustomItemController {

    private final CustomItemService customItemService;

    public CustomItemController(CustomItemService customItemService) {
        this.customItemService = customItemService;
    }

    @GetMapping
    private ResponseEntity<Page<CustomItemDTO>> getAllCustomItems(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize ) {
        return ResponseEntity.ok(customItemService.findAllCustomItems(page, pageSize));
    }

    @GetMapping("/{id}")
    private ResponseEntity<CustomItemDTO> getById(@PathVariable Long id) {
        CustomItemDTO customItemDTO = customItemService.getById(id);
        return ResponseEntity.ok(customItemDTO);
    }

    @PostMapping()
    public ResponseEntity<String> saveBinRange(@RequestBody CustomItemDTO customItemDTO) {
        String filename = customItemService.saveCustomItem(customItemDTO);
        return ResponseEntity.ok(filename);
    }

    @PutMapping()
    public ResponseEntity<String> updateCryptoConfig(@RequestBody CustomItemDTO customItemDTO) {
        String filename = customItemService.updateCustomItem(customItemDTO);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity destroy(@PathVariable("id") long id) {
        String filename = customItemService.deleteById(id);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = customItemService.getDownloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }
}
