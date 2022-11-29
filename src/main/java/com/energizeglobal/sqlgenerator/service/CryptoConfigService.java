package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.domain.CryptoConfigurationEntity;
import com.energizeglobal.sqlgenerator.dto.CryptoConfigDTO;
import com.energizeglobal.sqlgenerator.mapper.Mapping;
import com.energizeglobal.sqlgenerator.repository.CryptoConfigRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "cryptoConfig")
public class CryptoConfigService {

    static boolean activeDB = false;

    private final String INSERT_SQL_FILE_NAME = "crypto.sql";

    private final String ROLLBACK_SQL_FILE_NAME = "crypto_rollback.sql";

    private final Mapping mappingCryptoConfig;

    private final CryptoConfigRepository cryptoConfigRepository;

    private final GenerateSqlScriptService generateSqlScriptService;

    private final DownloadFileService downloadFileService;

    public CryptoConfigService(
            CryptoConfigRepository cryptoConfigRepository,
            Mapping mappingCryptoConfig,
            DownloadFileService downloadFileService,
            GenerateSqlScriptService generateSqlScriptService) {
        this.cryptoConfigRepository = cryptoConfigRepository;
        this.mappingCryptoConfig = mappingCryptoConfig;
        this.generateSqlScriptService = generateSqlScriptService;
        this.downloadFileService = downloadFileService;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<CryptoConfigDTO> findAllCryptoConfigs() {
        return mappingCryptoConfig.mapList(cryptoConfigRepository.findAll(), CryptoConfigDTO.class);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Page<CryptoConfigDTO> getPagedCryptoConfigs(Integer page, Integer pageSize){
        Pageable paging = PageRequest.of(page, pageSize);
        return new PageImpl<>(mappingCryptoConfig.mapList(cryptoConfigRepository.findAll(paging), CryptoConfigDTO.class), paging, cryptoConfigRepository.count());
    }

    @Transactional(readOnly = true)
    @Cacheable
    public CryptoConfigDTO getById(Long id) {
        return mappingCryptoConfig.convertToDto(cryptoConfigRepository.getById(id), CryptoConfigDTO.class);
    }

    @Transactional
    public String deleteById(Long id) {

        CryptoConfigurationEntity cryptoConfigurationEntity = cryptoConfigRepository.getById(id);

        String deleteQuery =
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM CryptoConfig WHERE id = " + id + ";\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        String rollbackQuery = String.format("INSERT INTO `CryptoConfig` (`protocolOne`, `protocolTwo`, `description`) " +
                        "VALUES('%s', '%s', '%s');",
                cryptoConfigurationEntity.getProtocolOne(),
                cryptoConfigurationEntity.getProtocolTwo(),
                cryptoConfigurationEntity.getDescription());

        generateSqlScriptService.insertSqlScript(deleteQuery, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(rollbackQuery, ROLLBACK_SQL_FILE_NAME);
        if (activeDB)
            cryptoConfigRepository.deleteById(id);
        return INSERT_SQL_FILE_NAME;
    }

    @Transactional
    public String saveCryptoConfig(CryptoConfigDTO cryptoConfigDTO) {

        CryptoConfigurationEntity cryptoConfigurationEntity = mappingCryptoConfig.convertToEntity(cryptoConfigDTO, CryptoConfigurationEntity.class);

        String queryType = String.format("INSERT INTO `CryptoConfig` (`protocolOne`, `protocolTwo`, `description`) VALUES('%s', '%s', '%s');",
                cryptoConfigurationEntity.getProtocolOne(),
                cryptoConfigurationEntity.getProtocolTwo(),
                cryptoConfigurationEntity.getDescription());

        String queryTypeRollback =
                        "START TRANSACTION; \n" +
                        "SET FOREIGN_KEY_CHECKS = 0; \n" +
                        "DELETE FROM CryptoConfig WHERE description = '" + cryptoConfigDTO.getDescription() + "';\n" +
                        "SET FOREIGN_KEY_CHECKS = 1; \n" +
                        "COMMIT;";


        cryptoConfigRepository.save(cryptoConfigurationEntity);

        generateSqlScriptService.insertSqlScript(queryType, INSERT_SQL_FILE_NAME);
        generateSqlScriptService.insertSqlScript(queryTypeRollback, ROLLBACK_SQL_FILE_NAME);

        Long lastId = cryptoConfigRepository.getMaxId();

        String deleteQuery =
                "START TRANSACTION; \n" +
                "SET FOREIGN_KEY_CHECKS = 0; \n" +
                "DELETE FROM CryptoConfig WHERE id = " + lastId + ";\n" +
                "SET FOREIGN_KEY_CHECKS = 1; \n" +
                "COMMIT;";

        if (activeDB)
            generateSqlScriptService.insertSqlScript(deleteQuery, ROLLBACK_SQL_FILE_NAME);

        return INSERT_SQL_FILE_NAME;
    }

    @Transactional
    public String updateCryptoConfig(CryptoConfigDTO dto) {

        CryptoConfigurationEntity cryptoConfigurationEntity = mappingCryptoConfig.convertToEntity(dto, CryptoConfigurationEntity.class);
        cryptoConfigurationEntity.setId(dto.getId());
        CryptoConfigurationEntity rollbackCryptoConfigurationEntity = cryptoConfigRepository.getById(dto.getId());


        String updateSql = generateDynamicSqlUpdateScript(dto,dto);

        String updateQuery = String.format("UPDATE `CryptoConfig` SET `protocolOne` = '%s', `protocolTwo` = '%s', `description` = '%s' WHERE id = '%s';",
                cryptoConfigurationEntity.getProtocolOne(),
                cryptoConfigurationEntity.getProtocolTwo(),
                cryptoConfigurationEntity.getDescription(),
                cryptoConfigurationEntity.getId());

        String updateRollbackSql = generateDynamicSqlUpdateScript(mappingCryptoConfig.convertToDto(rollbackCryptoConfigurationEntity,CryptoConfigDTO.class),dto);

        String rollbackQuery = String.format("UPDATE `CryptoConfig` SET `protocolOne` = '%s', `protocolTwo` = '%s', `description` = '%s' WHERE id = '%s';",
                rollbackCryptoConfigurationEntity.getProtocolOne(),
                rollbackCryptoConfigurationEntity.getProtocolTwo(),
                rollbackCryptoConfigurationEntity.getDescription(),
                rollbackCryptoConfigurationEntity.getId());

        generateSqlScriptService.insertSqlScript(updateSql, INSERT_SQL_FILE_NAME);

        generateSqlScriptService.insertSqlScript(updateRollbackSql, ROLLBACK_SQL_FILE_NAME);

        if (activeDB)
            cryptoConfigRepository.save(cryptoConfigurationEntity);

        return INSERT_SQL_FILE_NAME;
    }

    private String generateDynamicSqlUpdateScript(CryptoConfigDTO cryptoConfigDTO,CryptoConfigDTO newCryptoConfigDTO) {
        String prefix = "";
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE CryptoConfig SET ");
        if (newCryptoConfigDTO.getProtocolOne() != null && !newCryptoConfigDTO.getProtocolOne().isEmpty()) {
            if(cryptoConfigDTO.getProtocolOne() == null) {
                sqlBuilder.append(prefix).append("protocolOne = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("protocolOne = '").append(cryptoConfigDTO.getProtocolOne()).append("' ");
            }
            prefix = ",";
        }
        if (newCryptoConfigDTO.getProtocolTwo() != null && !newCryptoConfigDTO.getProtocolTwo().isEmpty()) {
            if(cryptoConfigDTO.getProtocolTwo() == null) {
                sqlBuilder.append(prefix).append("protocolTwo = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("protocolTwo = '").append(cryptoConfigDTO.getProtocolTwo()).append("' ");
            }
            prefix = ",";
        }
        if (newCryptoConfigDTO.getDescription() != null && !newCryptoConfigDTO.getDescription().isEmpty()) {
            if(cryptoConfigDTO.getDescription() == null) {
                sqlBuilder.append(prefix).append("description = ").append("NULL").append(" ");
            } else {
                sqlBuilder.append(prefix).append("description = '").append(cryptoConfigDTO.getDescription()).append("' ");
            }
        }
        sqlBuilder.append("WHERE id = ").append(cryptoConfigDTO.getId()).append( ";");
        return sqlBuilder.toString();
    }

    public Resource getDownloadFile(String FILE_NAME) {
        String FILE_PATH = "src/main/resources/sql_scripts/";
        return downloadFileService.downloadFile(FILE_NAME, FILE_PATH);
    }
}
