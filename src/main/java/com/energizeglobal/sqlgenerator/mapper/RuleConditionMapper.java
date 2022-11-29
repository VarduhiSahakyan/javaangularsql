package com.energizeglobal.sqlgenerator.mapper;

import com.energizeglobal.sqlgenerator.domain.RuleCondition;
import com.energizeglobal.sqlgenerator.domain.RuleEntity;
import com.energizeglobal.sqlgenerator.dto.RuleConditionDTO;

public class RuleConditionMapper {

  public static RuleCondition dtoToEntity(RuleConditionDTO ruleConditionDto, RuleEntity rule) {

        RuleCondition ruleCondition = new RuleCondition();

        ruleCondition.setId(ruleConditionDto.getId());
        ruleCondition.setName(ruleConditionDto.getName());
        ruleCondition.setCreatedBy(ruleConditionDto.getCreatedBy());
        ruleCondition.setLastUpdateBy(ruleConditionDto.getLastUpdateBy());
        ruleCondition.setDescription(ruleConditionDto.getDescription());
        ruleCondition.setLastUpdateDate(ruleConditionDto.getLastUpdateDate());
        ruleCondition.setUpdateState(ruleConditionDto.getUpdateState());
        ruleCondition.setCreationDate(ruleConditionDto.getCreationDate());
        ruleCondition.setRule(rule);

        return ruleCondition;
    }

}
