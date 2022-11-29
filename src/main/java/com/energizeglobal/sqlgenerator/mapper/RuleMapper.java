package com.energizeglobal.sqlgenerator.mapper;

import com.energizeglobal.sqlgenerator.domain.Profile;
import com.energizeglobal.sqlgenerator.domain.RuleEntity;
import com.energizeglobal.sqlgenerator.dto.RuleDTO;
import org.springframework.stereotype.Service;

@Service
public class RuleMapper {

    public static RuleEntity dtoToEntity(RuleDTO ruleDto,Profile profile) {

        RuleEntity ruleEntity = new RuleEntity();

        ruleEntity.setId(ruleDto.getId());
        ruleEntity.setName(ruleDto.getName());
        ruleEntity.setOrderRule(ruleDto.getOrderRule());
        ruleEntity.setCreatedBy(ruleDto.getCreatedBy());
        ruleEntity.setLastUpdateBy(ruleDto.getLastUpdateBy());
        ruleEntity.setDescription(ruleDto.getDescription());
        ruleEntity.setLastUpdateDate(ruleDto.getLastUpdateDate());
        ruleEntity.setUpdateState(ruleDto.getUpdateState());
        ruleEntity.setCreationDate(ruleDto.getCreationDate());
        ruleEntity.setProfile(profile);

        return ruleEntity;
    }
}
