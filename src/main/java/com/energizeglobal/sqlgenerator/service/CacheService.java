package com.energizeglobal.sqlgenerator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"authentMeans", "binRange", "cryptoConfig", "customItem", "customItemSet", "image", "issuer" ,
        "profile", "profileSet", "ruleCondition", "rule", "subIssuer"})
public class CacheService {
    public Logger logger = LoggerFactory.getLogger(AuthentMeansService.class);

    @CacheEvict
    @Scheduled(fixedRateString = "${spring.jpa.properties.caching.listTTL}")
    public void emptyCountryCodeCache() {
        logger.info("Emptying cache");
    }
}
