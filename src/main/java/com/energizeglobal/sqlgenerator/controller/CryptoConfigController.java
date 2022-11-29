package com.energizeglobal.sqlgenerator.controller;

import com.energizeglobal.sqlgenerator.dto.CryptoConfigDTO;
import com.energizeglobal.sqlgenerator.service.CryptoConfigService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/crypto")
public class CryptoConfigController {
    private final CryptoConfigService cryptoConfigService;

    public CryptoConfigController(CryptoConfigService cryptoConfigService) {
        this.cryptoConfigService = cryptoConfigService;
    }

    @GetMapping("/list")
    private ResponseEntity<List<CryptoConfigDTO>> getAllCryptoConfigs() {
        List<CryptoConfigDTO> cryptoConfigDTOS = cryptoConfigService.findAllCryptoConfigs();
        return ResponseEntity.ok(cryptoConfigDTOS);
    }

    @GetMapping
    public ResponseEntity<Page<CryptoConfigDTO>> getPageCryptoConfigs(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer pageSize){
        return ResponseEntity.ok(cryptoConfigService.getPagedCryptoConfigs(page, pageSize));
    }


    @GetMapping("/{id}")
    private ResponseEntity<CryptoConfigDTO> getById(@PathVariable Long id) {
        CryptoConfigDTO cryptoConfigDTO = cryptoConfigService.getById(id);
        return ResponseEntity.ok(cryptoConfigDTO);
    }


    @PostMapping()
    public ResponseEntity<String> saveCryptoConfig(@RequestBody CryptoConfigDTO cryptoConfigDTO) {
        String filename = cryptoConfigService.saveCryptoConfig(cryptoConfigDTO);
        return ResponseEntity.ok(filename);
    }

    @PutMapping()
    public ResponseEntity<String> updateCryptoConfig(@RequestBody CryptoConfigDTO cryptoConfigDTO) {
        String filename = cryptoConfigService.updateCryptoConfig(cryptoConfigDTO);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteById(@PathVariable Long id) {
        String filename = cryptoConfigService.deleteById(id);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = cryptoConfigService.getDownloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }
}
