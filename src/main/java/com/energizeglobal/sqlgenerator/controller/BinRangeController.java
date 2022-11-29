package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.dto.BinRangeDto;
import com.energizeglobal.sqlgenerator.service.BinRangeService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/binrange")
public class BinRangeController {

    public final BinRangeService binRangeService;

    public BinRangeController(BinRangeService binRangeService) {
        this.binRangeService = binRangeService;
    }

    @GetMapping
    private ResponseEntity<List<BinRangeDto>> getAllBinRanges() {
        List<BinRangeDto> binRangeDtos = binRangeService.findAllBinRages();
        return ResponseEntity.ok(binRangeDtos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<BinRangeDto> getById(@PathVariable Long id) {
        BinRangeDto binRangeDto = binRangeService.getById(id);
        return ResponseEntity.ok(binRangeDto);
    }


    @PostMapping()
    public ResponseEntity<String> saveBinRange(@RequestBody BinRangeDto binRangeDto) {
        String filename = binRangeService.saveBinRange(binRangeDto);
        return ResponseEntity.ok(filename);
    }

    @PutMapping()
    public ResponseEntity<String> updateCryptoConfig(@RequestBody BinRangeDto binRangeDto) {
        String filename = binRangeService.updateBinRange(binRangeDto);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteById(@PathVariable Long id) {
        String filename = binRangeService.deleteById(id);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = binRangeService.getDownloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }
}
