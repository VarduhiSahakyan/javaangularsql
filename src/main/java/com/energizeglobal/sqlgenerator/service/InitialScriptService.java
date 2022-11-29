package com.energizeglobal.sqlgenerator.service;

import com.energizeglobal.sqlgenerator.json.*;
import com.energizeglobal.sqlgenerator.json.conditionmeans.ConditionMeansProcessStatus;
import com.energizeglobal.sqlgenerator.json.conditionmeans.ConditionTransactionStatuses;
import com.energizeglobal.sqlgenerator.json.crypto.AcsBoCrypto;
import com.energizeglobal.sqlgenerator.json.crypto.ProtocolOne;
import com.energizeglobal.sqlgenerator.json.crypto.ProtocolTwo;
import com.energizeglobal.sqlgenerator.json.customitemset.CustomItemSet;
import com.energizeglobal.sqlgenerator.json.general.AcsBoGeneral;
import com.energizeglobal.sqlgenerator.json.image.AcsBoImage;
import com.energizeglobal.sqlgenerator.json.profile.AcsBoProfile;
import com.energizeglobal.sqlgenerator.json.rule.Rule;
import com.energizeglobal.sqlgenerator.json.rule.RuleCondition;
import com.energizeglobal.sqlgenerator.json.subissuer.SubIssuer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InitialScriptService {

    private TemplateEngine textTemplateEngine;
    Map<String, String> generatedSqlScript = new HashMap<>();

    public InitialScriptService(TemplateEngine textTemplateEngine) {
        this.textTemplateEngine = textTemplateEngine;
    }

    public Acs getInitialScriptJsonData01(){
        Acs acsObject = null;
        try {
            acsObject = new ObjectMapper().readValue(new ClassPathResource("json/01_init_script.json").getFile(), Acs.class);
            acsObject.getAcsProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acsObject;
    }

    public Acs getInitialScriptJsonData02(){
        Acs acsObject = null;
        try {
            acsObject = new ObjectMapper().readValue(new ClassPathResource("json/02_init_script.json").getFile(), Acs.class);
            acsObject.getAcsProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acsObject;
    }

    public String generateInitialScript01(){
        Context context = new Context();
        AcsBoGeneral general = getInitialScriptJsonData01().getAcsProperties().getAcsBoGeneral();
        context.setVariable("db", general.getDatabaseAcsBo());
        context.setVariable("createdBy", general.getCreatedBy());
        context.setVariable("updateState", general.getUpdateState());
        context.setVariable("bankB", general.getBankB());
        context.setVariable("bankUB", general.getBankUB());
        context.setVariable("usedSchemeVisa", general.getUsedSchemeVisa());
        context.setVariable("usedSchemeMasterCard", general.getUsedSchemeMasterCard());
//Issuer
        Issuer issuer = getInitialScriptJsonData01().getAcsProperties().getIssuer();
        context.setVariable("issuerCode", issuer.getCode());
        context.setVariable("issuerName", issuer.getName());
        context.setVariable("issuerDescription", issuer.getDescription());
        context.setVariable("availaibleAuthentMeans", issuer.getAvailaibleAuthentMeans());
//CryptoConfig
        AcsBoCrypto crypto = getInitialScriptJsonData01().getAcsProperties().getAcsBoCrypto();
        context.setVariable("cryptoDescription", crypto.getCryptoDescription());

        ProtocolOne protocolOne = getInitialScriptJsonData01().getAcsProperties().getAcsBoCrypto().getProtocolOne();
        context.setVariable("cavvKeyIndicator1", protocolOne.getCavvKeyIndicator());
        context.setVariable("secondFactorAuthentication1", protocolOne.getSecondFactorAuthentication());
        context.setVariable("cipherKeyIdentifier1", protocolOne.getCipherKeyIdentifier());
        context.setVariable("acsIdForCrypto1", protocolOne.getAcsIdForCrypto());
        context.setVariable("binKeyIdentifier1", protocolOne.getBinKeyIdentifier());
        context.setVariable("hubAESKey1", protocolOne.getHubAESKey());
        context.setVariable("informationalData1", protocolOne.getInformationalData());
        context.setVariable("cardNetworkAlgorithmMapVisa1", protocolOne.getCardNetworkAlgorithmMap().getVISA());
        context.setVariable("cardNetworkSeqGenerationMethodMapVisa1", protocolOne.getCardNetworkSeqGenerationMethodMap().getVISA());
        context.setVariable("cardNetworkIdentifierMapVisa1", protocolOne.getCardNetworkIdentifierMap().getVISA());
        context.setVariable("desCipherKeyIdentifier1", protocolOne.getDesCipherKeyIdentifier());
        context.setVariable("desKeyId1", protocolOne.getDesKeyId());
        context.setVariable("cardNetworkSignatureKeyMap1", protocolOne.getCardNetworkSignatureKeyMap().getVISA());
        context.setVariable("signingCertificate1", protocolOne.getCardNetworkCertificateMap().getVisaObj().getSigningCertificate());
        context.setVariable("authorityCertificate1", protocolOne.getCardNetworkCertificateMap().getVisaObj().getAuthorityCertificate());
        context.setVariable("rootCertificate1", protocolOne.getCardNetworkCertificateMap().getVisaObj().getRootCertificate());

        ProtocolTwo protocolTwo = getInitialScriptJsonData01().getAcsProperties().getAcsBoCrypto().getProtocolTwo();
        context.setVariable("cavvKeyIndicator2", protocolTwo.getCavvKeyIndicator());
        context.setVariable("cipherKeyIdentifier2", protocolTwo.getCipherKeyIdentifier());
        context.setVariable("acsIdForCrypto2", protocolTwo.getAcsIdForCrypto());
        context.setVariable("binKeyIdentifier2", protocolTwo.getBinKeyIdentifier());
        context.setVariable("hubAESKey2", protocolTwo.getHubAESKey());
        context.setVariable("informationalData2", protocolTwo.getInformationalData());
        context.setVariable("cardNetworkAlgorithmMapVisa2", protocolTwo.getCardNetworkAlgorithmMap().getVISA());
        context.setVariable("cardNetworkSeqGenerationMethodMapVisa2", protocolTwo.getCardNetworkSeqGenerationMethodMap().getVISA());
        context.setVariable("cardNetworkIdentifierMapVisa2", protocolTwo.getCardNetworkIdentifierMap().getVISA());
        context.setVariable("acsSignedContentCertificateMap2", protocolTwo.getAcsSignedContentCertificateKeyMap().getVISA());
        context.setVariable("signingCertificate2", protocolTwo.getCardNetworkCertificateMap().getVisaObj().getSigningCertificate());
        context.setVariable("authorityCertificate2", protocolTwo.getCardNetworkCertificateMap().getVisaObj().getAuthorityCertificate());
        context.setVariable("rootCertificate2", protocolTwo.getCardNetworkCertificateMap().getVisaObj().getRootCertificate());
//SubIssuer
        SubIssuer subIssuer = getInitialScriptJsonData01().getAcsProperties().getSubIssuer();
        context.setVariable("subIssuer", subIssuer);
//BinRange
        List<BinRange> binRangeList = getInitialScriptJsonData01().getAcsProperties().getBinRanges();
        context.setVariable("binRangeList", binRangeList);
//MerchantPivotList
        MerchantPivotList merchantPivotList = getInitialScriptJsonData01().getAcsProperties().getMerchantPivotList();
        context.setVariable("merchantPivotList", merchantPivotList);

        String initialScript1 = textTemplateEngine.process("01_init_script", context);
        return initialScript1;
    }

    public String generateInitialScript02(){
        Context context = new Context();
        AcsBoGeneral general = getInitialScriptJsonData02().getAcsProperties().getAcsBoGeneral();
        context.setVariable("db", general.getDatabaseAcsBo());
        context.setVariable("createdBy", general.getCreatedBy());
        context.setVariable("updateState", general.getUpdateState());
        context.setVariable("bankB", general.getBankB());
        context.setVariable("bankUB", general.getBankUB());
        context.setVariable("issuerCode", general.getIssuerCode());
        context.setVariable("subIssuerCode", general.getSubIssuerCode());
        context.setVariable("subIssuerName", general.getSubIssuerName());
//CustomPageLayout
        List<CustomPageLayout> pageLayoutList = getInitialScriptJsonData02().getAcsProperties().getCustomPageLayout();
        context.setVariable("pageLayoutList", pageLayoutList);
//CustomComponent
        List<CustomComponent> customComponentList = getInitialScriptJsonData02().getAcsProperties().getCustomComponentList();
        context.setVariable("customComponentList", customComponentList);
//Image
        AcsBoImage image = getInitialScriptJsonData02().getAcsProperties().getAcsBoImage();
        context.setVariable("image", image);
//CustomItemSet
        List<CustomItemSet> customItemSetList = getInitialScriptJsonData02().getAcsProperties().getCustomItemSetList();
        context.setVariable("customItemSetList", customItemSetList);
//Profile
        List<AcsBoProfile> profileList = getInitialScriptJsonData02().getAcsProperties().getProfileArrayList();
        context.setVariable("profileList", profileList);
//Rule
        List<Rule> ruleList = getInitialScriptJsonData02().getAcsProperties().getRuleList();
        context.setVariable("ruleList", ruleList);
//RuleCondition
        List<RuleCondition> ruleConditionList = getInitialScriptJsonData02().getAcsProperties().getRuleConditionList();
        context.setVariable("ruleConditionList", ruleConditionList);
//ConditionTransactionStatuses
        List<ConditionTransactionStatuses> transactionStatusesList = getInitialScriptJsonData02().getAcsProperties().getConditionTransactionStatusesList();
        context.setVariable("transactionStatusesList", transactionStatusesList);
//ConditionMeansProcessStatus
        List<ConditionMeansProcessStatus> processStatusList = getInitialScriptJsonData02().getAcsProperties().getConditionMeansProcessStatusList();
        context.setVariable("processStatusList", processStatusList);
//CustomItem
        List<CustomItem> customItemList = getInitialScriptJsonData02().getAcsProperties().getCustomItemList();
        context.setVariable("customItemList", customItemList);

        String initialScript2 = textTemplateEngine.process("02_init_script", context);
        return initialScript2;
    }

//    public Map<String, String> generateSqlScriptFromJson() {
//
//        Acs acsObject = getAcsObjectFromJson();
//        generatedSqlScript.put("general", generateGeneralScriptFromJson(acsObject.getAcsProperties().getAcsBoGeneral(), acsObject.getAcsProperties().getIssuer()));
//        generatedSqlScript.put("issuer", generateIssuerSqlScriptFromJson(acsObject.getAcsProperties().getIssuer()));
//        generatedSqlScript.put("crypto", generateCryptoSqlScriptFromJson(acsObject.getAcsProperties().getAcsBoCrypto()));
//        generatedSqlScript.put("subissuer", generateSubIssuerSqlScriptFromJson(acsObject.getAcsProperties().getSubIssuerList()));
//        generatedSqlScript.put("profileSet", generateProfileSetSqlScriptFromJson(acsObject.getAcsProperties().getSubIssuerList()));
//        generatedSqlScript.put("binRange", generateBinRangeSqlScriptFromJson(acsObject.getAcsProperties().getBinRanges(), acsObject.getAcsProperties().getSubIssuerList()));
//        generatedSqlScript.put("customPageLayout", generateCustomPageLayoutSqlScriptFromJson(acsObject.getAcsProperties().getCustomPageLayout()));
//        generatedSqlScript.put("customPageLayoutProfileSet", generateCustomPageLayoutProfileSetSqlScriptFromJson(acsObject.getAcsProperties().getSubIssuerList()));
//        generatedSqlScript.put("image", generateImageSqlScriptFromJson(acsObject.getAcsProperties().getAcsBoImage()));
//        generatedSqlScript.put("customItemSet", generateCustomItemSetSqlScriptFromJson(acsObject.getAcsProperties().getCustomItemSetList(),  acsObject.getAcsProperties().getSubIssuerList()));
//        generatedSqlScript.put("profile", generateProfileSqlScriptFromJson(acsObject.getAcsProperties().getProfileArrayList(), acsObject.getAcsProperties().getCustomItemSetList(), acsObject.getAcsProperties().getAcsBoGeneral().getAuthentMeans()));
//        generatedSqlScript.put("rule", generateRuleSqlScriptFromJson(acsObject.getAcsProperties().getRuleList(), acsObject.getAcsProperties().getAcsBoGeneral()));
//        generatedSqlScript.put("ruleCondition", generateRuleConditionSqlScriptFromJson(acsObject.getAcsProperties().getRuleConditionList(), acsObject.getAcsProperties().getAcsBoGeneral()));
//        generatedSqlScript.put("merchantPivotList", generateMerchantPivotListSqlScriptFromJson(acsObject.getAcsProperties().getMerchantPivotList()));
//        generatedSqlScript.put("conditionMeansProcess", generateConditionMeans(acsObject.getAcsProperties().getConditionMeansProcessStatusList()));
//        generatedSqlScript.put("transactionStatus", generateTransactionStatusesMeans(acsObject.getAcsProperties().getConditionTransactionStatusesList()));
//
//        return generatedSqlScript;
//    }
//
//    private String generateGeneralScriptFromJson(AcsBoGeneral acsBoGeneral, Issuer issuer) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("USE `").append(acsBoGeneral.getDatabaseAcsBo()).append("`;\n\n");
//
//        sql.append("SET @createdBy = '").append(acsBoGeneral.getCreatedBy()).append("';\n");
//        sql.append("SET @updateState = '").append(acsBoGeneral.getUpdateState()).append("';\n");
//        sql.append("SET @bankB = '").append(acsBoGeneral.getBankB()).append("';\n");
//        sql.append("SET @bankUB = '").append(acsBoGeneral.getBankUB()).append("';\n");
//        sql.append("SET @issuerNameAndLabel = '").append(issuer.getName()).append("';\n");
//        sql.append("SET @issuerCode = '").append(issuer.getCode()).append("';\n");
//        sql.append("SET @issuerId = (SELECT `id` FROM `Issuer` WHERE `code` = @issuerCode);\n\n");
//
//        sql.append("SET @visaId = (SELECT `id` FROM `Network` WHERE `code` = '").append(acsBoGeneral.getUsedSchemeVisa()).append("');\n");
//        sql.append("SET @visaName = (SELECT `name` FROM `Network` WHERE `code` = '").append(acsBoGeneral.getUsedSchemeVisa()).append("');\n\n");
//
//        sql.append("SET @mastercardId = (SELECT `id` FROM `Network` WHERE `code` = '").append(acsBoGeneral.getUsedSchemeMasterCard()).append("');\n");
//        sql.append("SET @mastercardName = (SELECT `name` FROM `Network` WHERE `code` = '").append(acsBoGeneral.getUsedSchemeMasterCard()).append("');\n\n");
//
//        //sql.append("SET @availableAuthMeans = '").append(acsBoGeneral.getAvailaibleAuthentMeans()).append("';\n\n");
//
//        return sql.toString();
//    }
//
//    private String generateIssuerSqlScriptFromJson(Issuer issuer) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("INSERT INTO `Issuer` (`code`, `createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`, `updateState`, `label`, `availaibleAuthentMeans`) ");
//        sql.append("VALUES \n");
//        sql.append("(");
//        sql.append("@issuerCode").append(", ");
//        sql.append("@createdBy").append(", ");
//        sql.append("NOW()").append(", ");
//        sql.append("'").append(issuer.getDescription()).append("', ");
//        sql.append("@issuerNameAndLabel").append(", ");
//        sql.append("@updateState").append(", ");
//        sql.append("@issuerNameAndLabel").append(", ");
//        sql.append("@availableAuthMeans");
//        sql.append(");\n\n");
//        return sql.toString();
//    }
//
//    private String generateCryptoSqlScriptFromJson(AcsBoCrypto crypto) {
//        StringBuilder sql = new StringBuilder();
//        // Crypto
//        sql.append("SET @protocol1 = ' \n");
//        sql.append("{ \n");
//        sql.append("\t\"cavvKeyIndicator\" : \"").append(crypto.getProtocolOne().getCavvKeyIndicator()).append("\",\n");
//        sql.append("\t\"secondFactorAuthentication\" : \"").append(crypto.getProtocolOne().getSecondFactorAuthentication()).append("\",\n");
//        sql.append("\t\"cipherKeyIdentifier\" : \"").append(crypto.getProtocolOne().getCipherKeyIdentifier()).append("\",\n");
//        sql.append("\t\"acsIdForCrypto\" : \"").append(crypto.getProtocolOne().getAcsIdForCrypto()).append("\",\n");
//        sql.append("\t\"binKeyIdentifier\" : \"").append(crypto.getProtocolOne().getBinKeyIdentifier()).append("\",\n");
//        sql.append("\t\"hubAESKey\" : \"").append(crypto.getProtocolOne().getHubAESKey()).append("\",\n");
//        sql.append("\t\"informationalData\" : \"").append(crypto.getProtocolOne().getInformationalData()).append("\",\n");
//        sql.append("\t\"cardNetworkAlgorithmMap\" : {\n");
//        sql.append("\t\t\"VISA\" : \"").append(crypto.getProtocolOne().getCardNetworkAlgorithmMap().getVISA()).append("\",\n");
//        sql.append("\t},\n");
//        sql.append("\t\"cardNetworkSeqGenerationMethodMap\" : {\n");
//        sql.append("\t\t\"VISA\" : \"").append(crypto.getProtocolOne().getCardNetworkSeqGenerationMethodMap().getVISA()).append("\",\n");
//        sql.append("\t},\n");
//        sql.append("\t\"cardNetworkIdentifierMap\" : {\n");
//        sql.append("\t\t\"VISA\" : \"").append(crypto.getProtocolOne().getCardNetworkIdentifierMap().getVISA()).append("\",\n");
//        sql.append("\t},\n");
//        sql.append("\t\"desCipherKeyIdentifier\" : \"").append(crypto.getProtocolOne().getDesCipherKeyIdentifier()).append("\",\n");
//        sql.append("\t\"desKeyId\" : \"").append(crypto.getProtocolOne().getDesKeyId()).append("\",\n");
//        sql.append("\t\"cardNetworkSignatureKeyMap\" : {\n");
//        sql.append("\t\t\"VISA\" : \"").append(crypto.getProtocolOne().getCardNetworkSignatureKeyMap().getVISA()).append("\",\n");
//        sql.append("\t},\n");
//        sql.append("\t\"cardNetworkCertificateMap\" : {\n");
//        sql.append("\t\t\"VISA\" : {\n");
//        sql.append("\t\t\t\"signingCertificate\" : \"").append(crypto.getProtocolOne().getCardNetworkCertificateMap().getVisaObj().getSigningCertificate()).append("\",\n");
//        sql.append("\t\t\t\"authorityCertificate\" : \"").append(crypto.getProtocolOne().getCardNetworkCertificateMap().getVisaObj().getAuthorityCertificate()).append("\",\n");
//        sql.append("\t\t\t\"rootCertificate\" : \"").append(crypto.getProtocolOne().getCardNetworkCertificateMap().getVisaObj().getRootCertificate()).append("\",\n");
//        sql.append("\t\t}\n");
//        sql.append("\t}\n");
//        sql.append("}'; \n\n");
//
//        sql.append("SET @protocol2 = ' \n");
//        sql.append("{ \n");
//        sql.append("\t\"cavvKeyIndicator\" : \"").append(crypto.getProtocolTwo().getCavvKeyIndicator()).append("\",\n");
//        sql.append("\t\"cipherKeyIdentifier\" : \"").append(crypto.getProtocolTwo().getCipherKeyIdentifier()).append("\",\n");
//        sql.append("\t\"acsIdForCrypto\" : \"").append(crypto.getProtocolTwo().getAcsIdForCrypto()).append("\",\n");
//        sql.append("\t\"binKeyIdentifier\" : \"").append(crypto.getProtocolTwo().getBinKeyIdentifier()).append("\",\n");
//        sql.append("\t\"hubAESKey\" : \"").append(crypto.getProtocolTwo().getHubAESKey()).append("\",\n");
//        sql.append("\t\"informationalData\" : \"").append(crypto.getProtocolTwo().getInformationalData()).append("\",\n");
//        sql.append("\t\"cardNetworkAlgorithmMap\" : {\n");
//        sql.append("\t\t\"VISA\" : \"").append(crypto.getProtocolTwo().getCardNetworkAlgorithmMap().getVISA()).append("\",\n");
//        sql.append("\t},\n");
//        sql.append("\t\"cardNetworkSeqGenerationMethodMap\" : {\n");
//        sql.append("\t\t\"VISA\" : \"").append(crypto.getProtocolTwo().getCardNetworkSeqGenerationMethodMap().getVISA()).append("\",\n");
//        sql.append("\t},\n");
//        sql.append("\t\"cardNetworkIdentifierMap\" : {\n");
//        sql.append("\t\t\"VISA\" : \"").append(crypto.getProtocolTwo().getCardNetworkIdentifierMap().getVISA()).append("\",\n");
//        sql.append("\t},\n");
//        sql.append("\t\"cardNetworkCertificateMap\" : {\n");
//        sql.append("\t\t\"VISA\" : {\n");
//        sql.append("\t\t\t\"signingCertificate\" : \"").append(crypto.getProtocolTwo().getCardNetworkCertificateMap().getVisaObj().getSigningCertificate()).append("\",\n");
//        sql.append("\t\t\t\"authorityCertificate\" : \"").append(crypto.getProtocolTwo().getCardNetworkCertificateMap().getVisaObj().getAuthorityCertificate()).append("\",\n");
//        sql.append("\t\t\t\"rootCertificate\" : \"").append(crypto.getProtocolTwo().getCardNetworkCertificateMap().getVisaObj().getRootCertificate()).append("\",\n");
//        sql.append("\t\t}\n");
//        sql.append("\t}\n");
//        sql.append("}'; \n\n");
//
//        sql.append("INSERT INTO `CryptoConfig` (`protocolOne`, `protocolTwo`, `description`) ").append("VALUES \n");
//        sql.append("(@protocol1, @protocol2, '").append(crypto.getCryptoDescription()).append("'); \n\n");
//
//        sql.append("SET @cryptoConfigId = (SELECT id FROM CryptoConfig where description = '").append(crypto.getCryptoDescription()).append("'); \n\n");
//
//        return sql.toString();
//    }
//
//    private String generateSubIssuerSqlScriptFromJson(List<SubIssuer> subIssuerList) {
//
//        StringBuilder sql = new StringBuilder();
//
//        for (SubIssuer subIssuer : subIssuerList) {
//            sql.append("SET @subIssuerCode_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getSubIssuerCode()).append("';\n");
//            sql.append("SET @subIssuerNameAndLabel_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getSubIssuerName()).append("';\n");
//            sql.append("SET @authenticationTimeOut_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getAuthenticationTimeOut()).append(";\n");
//            sql.append("SET @freshnessPeriod_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getFreshnessPeriod()).append(";\n");
//            sql.append("SET @transactionTimeOut_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getTransactionTimeOut()).append(";\n");
//            sql.append("SET @acsId_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getAcsId()).append("';\n");
//            sql.append("SET @currencyCode_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getCurrencyCode()).append("';\n");
//            sql.append("SET @backUpLanguages_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getBackupLanguages()).append("';\n");
//            sql.append("SET @defaultLanguage_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getDefaultLanguage()).append("';\n");
//            sql.append("SET @HUBcallMode_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getHubCallMode()).append("';\n");
//            sql.append("SET @acsURL1VEMastercard_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getAcsUrl1VeMc()).append("';\n");
//            sql.append("SET @acsURL2VEMastercard_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getAcsUrl2VeMc()).append("';\n");
//            sql.append("SET @acsURL1VEVisa_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getAcsUrl1VeVisa()).append("';\n");
//            sql.append("SET @acsURL2VEVisa_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getAcsUrl2VeVisa()).append("';\n");
//            sql.append("SET @activatedAuthMeans_").append(subIssuer.getSubIssuerCode()).append(" = '[\n");
//            for (ActivatedAuthMeans activatedAuthMean : subIssuer.getActivatedAuthMeans()) {
//                sql.append("\t\t{\n");
//                sql.append("\t\t\t \"authentMeans\" : \"").append(activatedAuthMean.getAuthentMeans()).append("\",\n");
//                sql.append("\t\t\t \"validate\" : ").append(activatedAuthMean.isValidate()).append("\n");
//                if (subIssuer.getActivatedAuthMeans().indexOf(activatedAuthMean) == subIssuer.getActivatedAuthMeans().size() - 1) {
//                    sql.append("\t\t}\n");
//                } else {
//                    sql.append("\t\t},\n");
//                }
//            }
//            sql.append("\t ]'; \n");
//            sql.append("SET @preferredAuthMean_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getPreferredAuthentMeans()).append("';\n");
//            sql.append("SET @issuerCountryCode_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getIssuerCountry()).append("';\n");
//            sql.append("SET @maskParam_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getMaskParams()).append("';\n");
//            sql.append("SET @dateFormat_").append(subIssuer.getSubIssuerCode()).append(" = '").append(subIssuer.getDateFormat()).append("';\n");
//            sql.append("SET @currencyFormat_").append(subIssuer.getSubIssuerCode()).append(" = '{ \n");
//            sql.append("\t\t\"useAlphaCurrencySymbol\" : ").append(subIssuer.getCurrencyFormat().isUseAlphaCurrencySymbol()).append(", \n");
//            sql.append("\t\t\"currencySymbolPosition\" : \"").append(subIssuer.getCurrencyFormat().getCurrencySymbolPosition()).append("\", \n");
//            sql.append("\t\t\"decimalDelimiter\" : \"").append(subIssuer.getCurrencyFormat().getDecimalDelimiter()).append("\", \n");
//            sql.append("\t\t\"thousandDelimiter\" : \"").append(subIssuer.getCurrencyFormat().getThousandDelimiter()).append("\" \n");
//            sql.append("\t\t}'; \n");
//            sql.append("SET @3DS2AdditionalInfo_").append(subIssuer.getSubIssuerCode()).append(" = '{ \n");
//            sql.append("\t \"VISA\" : {\n");
//            sql.append("\t\t\"operatorId\" : \"").append(subIssuer.getDS2AdditionalInfo().getVisaObj().getOperatorId()).append("\", \n");
//            sql.append("\t\t\"dsKeyAlias\" : \"").append(subIssuer.getDS2AdditionalInfo().getVisaObj().getDsKeyAlias()).append("\" \n");
//            sql.append("\t}, \n");
//            sql.append("\t \"MASTERCARD\" : {\n");
//            sql.append("\t\t\"operatorId\" : \"").append(subIssuer.getDS2AdditionalInfo().getMasterCard().getOperatorId()).append("\", \n");
//            sql.append("\t\t\"dsKeyAlias\" : \"").append(subIssuer.getDS2AdditionalInfo().getMasterCard().getDsKeyAlias()).append("\" \n");
//            sql.append("\t}'; \n");
//            sql.append("SET @manageBackupsCombinedAmounts_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getManageBackupsCombinedAmounts()).append("; \n");
//            sql.append("SET @manageChoicesCombinedAmounts_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getManageChoicesCombinedAmounts()).append("; \n");
//            sql.append("SET @personnalDataStorage_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getPersonnalDataStorage()).append("; \n");
//            sql.append("SET @resetBackupsIfSuccess_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getResetBackupsIfSuccess()).append("; \n");
//            sql.append("SET @resetChoicesIfSuccess_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getResetChoicesIfSuccess()).append("; \n\n");
//            sql.append("SET @automaticDeviceSelection_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getAutomaticDeviceSelection()).append("; \n");
//            sql.append("SET @userChoiceAllowed_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getUserChoiceAllowed()).append("; \n");
//            sql.append("SET @backupAllowed_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getBackupAllowed()).append("; \n");
//            sql.append("SET @defaultDeviceChoice_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getDefaultDeviceChoice()).append("; \n");
//            sql.append("SET @verifyCardStatus_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getVerifyCardStatus()).append("; \n");
//            sql.append("SET @resendOTPThreshold_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getResendOTPThreshold()).append("; \n");
//            sql.append("SET @resendSameOTP_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getResendSameOTP()).append("; \n");
//            sql.append("SET @combinedAuthenticationAllowed_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getCombinedAuthenticationAllowed()).append("; \n");
//            sql.append("SET @displayLanguageSelectPage_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getDisplayLanguageSelectPage()).append("; \n");
//            sql.append("SET @trustedBeneficiariesAllowed_").append(subIssuer.getSubIssuerCode()).append(" = ").append(subIssuer.getTrustedBeneficiariesAllowed()).append("; \n\n");
//            sql.append("SET @paChallengeURL_").append(subIssuer.getSubIssuerCode()).append("  = '{\n");
//            sql.append("\t\"Vendom\" : \"").append(subIssuer.getPaChallengePublicUrl().getVendome()).append("\", \n");
//            sql.append("\t\"Seclin\" : \"").append(subIssuer.getPaChallengePublicUrl().getSeclin()).append("\", \n");
//            sql.append("\t\"Unknown\" : \"").append(subIssuer.getPaChallengePublicUrl().getUnknown()).append("\" \n");
//            sql.append("}'; \n\n");
//
//            sql.append("INSERT INTO `SubIssuer` (`acsId`, `authenticationTimeOut`, `backupLanguages`, `code`, `codeSvi`, `currencyCode`,\n" +
//                    "\t\t\t\t `createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`, `updateState`,\n" +
//                    "\t\t\t\t `defaultLanguage`, `freshnessPeriod`, `label`, `manageBackupsCombinedAmounts`, `manageChoicesCombinedAmounts`,\n" +
//                    "\t\t\t\t `personnalDataStorage`, `resetBackupsIfSuccess`, `resetChoicesIfSuccess`,\n" +
//                    "\t\t\t\t `transactionTimeOut`, `acs_URL1_VE_MC`, `acs_URL2_VE_MC`, `acs_URL1_VE_VISA`, `acs_URL2_VE_VISA`,\n" +
//                    "\t\t\t\t `automaticDeviceSelection`, `userChoiceAllowed`, `backupAllowed`, `defaultDeviceChoice`, `preferredAuthentMeans`,\n" +
//                    "\t\t\t\t `issuerCountry`, `hubCallMode`, `fk_id_issuer`, `maskParams`, `dateFormat`,`paChallengePublicUrl`,\n" +
//                    "\t\t\t\t `verifyCardStatus`,`3DS2AdditionalInfo`,`resendOTPThreshold`, `resendSameOTP`,`combinedAuthenticationAllowed`,\n" +
//                    "\t\t\t\t `displayLanguageSelectPage`,`trustedBeneficiariesAllowed`,`authentMeans`, `fk_id_cryptoConfig`, `currencyFormat`) VALUES \n");
//            sql.append("(@acsId_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@authenticationTimeOut_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@backUpLanguages_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@subIssuerCode_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@subIssuerCode_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@currencyCode_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@createdBy").append(", ");
//            sql.append("NOW(), NULL, NULL, NULL,");
//            sql.append("@subIssuerNameAndLabel_").append(subIssuer.getSubIssuerCode()).append(", \n");
//            sql.append("@updateState").append(", ");
//            sql.append("@defaultLanguage_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@freshnessPeriod_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@subIssuerNameAndLabel_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@manageBackupsCombinedAmounts_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@manageChoicesCombinedAmounts_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@personnalDataStorage_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@resetBackupsIfSuccess_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@resetChoicesIfSuccess_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@transactionTimeOut_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@acsURL1VEMastercard_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@acsURL2VEMastercard_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@acsURL1VEVisa_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@acsURL2VEVisa_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@automaticDeviceSelection_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@userChoiceAllowed_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@backupAllowed_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@defaultDeviceChoice_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@preferredAuthMean_").append(subIssuer.getSubIssuerCode()).append(", \n");
//            sql.append("@issuerCountryCode_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@HUBcallMode_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@issuerId").append(", ");
//            sql.append("@maskParam_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@dateFormat_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@paChallengeURL_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@verifyCardStatus_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@3DS2AdditionalInfo_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@resendOTPThreshold_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@resendSameOTP_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@combinedAuthenticationAllowed_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@displayLanguageSelectPage_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@trustedBeneficiariesAllowed_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@activatedAuthMeans_").append(subIssuer.getSubIssuerCode()).append(", ");
//            sql.append("@cryptoConfigID").append(", ");
//            sql.append("@currencyFormat_").append(subIssuer.getSubIssuerCode()).append("); \n\n");
//
//            sql.append("SET @subIssuerId_").append(subIssuer.getSubIssuerCode()).append(" = (SELECT id FROM `SubIssuer` WHERE `code` = @subIssuerCode_").append(subIssuer.getSubIssuerCode()).append(" AND `name` = @subIssuerNameAndLabel_").append(subIssuer.getSubIssuerCode()).append("); \n\n");
//        }
//        return sql.toString();
//    }
//
//    private String generateProfileSetSqlScriptFromJson(List<SubIssuer> subIssuerList) {
//        StringBuilder sql = new StringBuilder();
//        sql.append(" /* ----------- ProfileSet ----------- */ \n\n");
//        for (SubIssuer subIssuer : subIssuerList) {
//            sql.append("INSERT INTO `ProfileSet` (`createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`, `updateState`, `fk_id_subIssuer`) \n");
//            sql.append("SELECT @createdBy, NOW(), CONCAT(@BankB, ' profile set'), NULL, NULL, CONCAT('PS_', @BankUB, '_01'), @updateState, si.id \n");
//            sql.append("FROM `SubIssuer` si \n");
//            sql.append("WHERE si.fk_id_issuer = @issuerId and si.id = @subIssuerId_").append(subIssuer.getSubIssuerCode()).append(";\n\n");
//
//            sql.append("SET @profileSetId_").append(subIssuer.getSubIssuerCode()).append(" = (SELECT id FROM `ProfileSet` WHERE `name` = CONCAT('PS_', @BankUB, '_01') " +
//                    "AND fk_id_subIssuer = @subIssuerId_").append(subIssuer.getSubIssuerCode()).append("); \n\n");
//        }
//        return sql.toString();
//    }
//
//    private String generateBinRangeSqlScriptFromJson(List<BinRange> binRangeList, List<SubIssuer> subIssuerList) {
//        StringBuilder sql = new StringBuilder();
//        sql.append(" /* ----------- BinRange  ----------- */ \n\n");
//        for (BinRange bin : binRangeList) {
//
//            sql.append("SET @subIssuerId_").append(bin.getSubIssuerCode()).append(" = (SELECT id FROM `SubIssuer` WHERE `code` = ").append(bin.getSubIssuerCode()).append(");\n");
//            sql.append("SET @profileSetId_").append(bin.getSubIssuerCode()).append(" = (SELECT id FROM `ProfileSet` WHERE `name` = CONCAT('PS_', @BankUB, '_01') " +
//                    "AND fk_id_subIssuer = @subIssuerId_").append(bin.getSubIssuerCode()).append("); \n\n");
//
//            sql.append("INSERT INTO `BinRange` (`activateState`, `createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`,\n" +
//                    "\t\t\t`name`, `updateState`, `immediateActivation`, `activationDate`, `lowerBound`, `panLength`,\n" +
//                    "\t\t\t`sharedBinRange`, `updateDSDate`, `upperBound`, `toExport`, `fk_id_profileSet`, `fk_id_network`, `coBrandedCardNetwork`) VALUES \n");
//            sql.append("(");
//            sql.append("'").append(bin.getActiveState()).append("', ");
//            sql.append("@createdBy").append(", ");
//            sql.append("NOW()").append(", ");
//            sql.append("NULL").append(", ");
//            sql.append("NULL").append(", ");
//            sql.append("NULL").append(", ");
//            sql.append("'").append(bin.getName()).append("', ");
//            sql.append("@updateState").append(", ");
//            sql.append(bin.isImmediateActivation()).append(", ");
//            sql.append("NOW()").append(", ");
//            sql.append(bin.getLowerBound()).append(", ");
//            sql.append(bin.getPanLength()).append(", ");
//            sql.append(bin.isSharedBinRange()).append(", ");
//            sql.append("NULL").append(", ");
//            sql.append(bin.getUpperBound()).append(", ");
//            sql.append(bin.isToExport()).append(", ");
//            sql.append("@profileSetId_").append(bin.getSubIssuerCode()).append(", ");
//            sql.append("@visaId").append(", ");
//            sql.append("NULL); \n\n");
//            sql.append(" /* -------------------------- */ \n\n");
//
//        }
//
//        // BinRange_SubIssuer
//        sql.append(" /* ------------- BinRange_SubIssuer ------------- */ \n\n");
//
//        for (SubIssuer subIssuer : subIssuerList){
//            sql.append("SET @profileSetId_").append(subIssuer.getSubIssuerCode()).append(" = (SELECT id FROM `ProfileSet` WHERE `name` = CONCAT('PS_', @BankUB, '_01') " +
//                    "AND fk_id_subIssuer = @subIssuerId_").append(subIssuer.getSubIssuerCode()).append("); \n\n");
//            for (BinRange binRange : binRangeList) {
//                sql.append("INSERT INTO `BinRange_SubIssuer` (`id_binRange`, `id_subIssuer`) \n");
//                sql.append("SELECT b.id, s.id FROM BinRange b, SubIssuer s \n");
//                sql.append("WHERE (b.lowerBound = ").append(binRange.getLowerBound()).append(" AND b.upperBound = ").append(binRange.getUpperBound()).append(") \n");
//                sql.append("AND b.fk_id_profileSet = @profileSetId_").append(subIssuer.getSubIssuerCode()).append(" AND s.code = ").append(subIssuer.getSubIssuerCode()).append("; \n\n");
//            }
//        }
//        return sql.toString();
//    }
//
//    private String generateCustomPageLayoutSqlScriptFromJson(List<CustomPageLayout> pageLayoutList) {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("\nINSERT INTO `CustomPageLayout` (`controller`,`pageType`,`description`) VALUES \n");
//        for (CustomPageLayout pageLayout : pageLayoutList) {
//            sql.append("(");
//            if (pageLayout.getController().isEmpty()) {
//                sql.append("NULL").append(", ");
//            } else {
//                sql.append("'").append(pageLayout.getController()).append("', ");
//            }
//            sql.append("'").append(pageLayout.getPageType()).append("', ");
//            sql.append("CONCAT('").append(pageLayout.getDescription()).append(" (', @BankB, ')' )");
//            if(pageLayoutList.indexOf(pageLayout) == pageLayoutList.size() - 1){
//                sql.append(");\n");
//            } else {
//                sql.append("),\n");
//            }
//        }
//        return sql.toString();
//    }
//
//    private String generateCustomPageLayoutProfileSetSqlScriptFromJson(List<SubIssuer> subIssuerList) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("\n /* ------------- CustomPageLayout_ProfileSet ------------- */ \n");
//        for (SubIssuer subIssuer : subIssuerList) {
//            sql.append("\nSET @profileSetId_").append(subIssuer.getSubIssuerCode()).append(" = (SELECT id FROM `ProfileSet` WHERE `name` = CONCAT('PS_', @BankUB, '_01') " +
//                    "AND fk_id_subIssuer = @subIssuerId_").append(subIssuer.getSubIssuerCode()).append("); \n");
//
//            sql.append("\nINSERT INTO `CustomPageLayout_ProfileSet` (`customPageLayout_id`, `profileSet_id`)\n" +
//                    "\tSELECT cpl.id, p.id\n" +
//                    "\tFROM `CustomPageLayout` cpl, `ProfileSet` p\n" +
//                    "\tWHERE cpl.description like CONCAT('%', @BankB, '%') AND p.id = @profileSetId_").append(subIssuer.getSubIssuerCode()).append("; \n");
//        }
//        return sql.toString();
//    }
//
//    private String generateImageSqlScriptFromJson(AcsBoImage image) {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("\n\nINSERT INTO `Image` (`createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`, `updateState`, `binaryData`, `relativePath`) VALUES \n");
//        sql.append("(").append("@createdBy, NOW(), CONCAT(@BankUB,' Logo'), ");
//        sql.append("NULL").append(", ");
//        sql.append("NULL").append(", ");
//        sql.append("@BankUB, 'PUSHED_TO_CONFIG', ");
//        sql.append("'").append(image.getBinaryData()).append("', ");
//        sql.append("'").append(image.getRelativePath()).append("'");
//        sql.append(");\n");
//        return sql.toString();
//    }
//
//    private String generateCustomItemSetSqlScriptFromJson(List<CustomItemSet> customItemSetList, List<SubIssuer> subIssuerList) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("\n /* -------------- CustomItemSet ----------------- */ \n\n");
//        sql.append("SET @status = '").append("DEPLOYED_IN_PRODUCTION").append("'; \n\n");
//        for (SubIssuer subIssuer : subIssuerList) {
//            sql.append("SET @subIssuerId_").append(subIssuer.getSubIssuerCode()).append(" = (SELECT id FROM `SubIssuer` WHERE `code` = @subIssuerCode_").append(subIssuer.getSubIssuerCode()).append(" AND `name` = @subIssuerNameAndLabel_").append(subIssuer.getSubIssuerCode()).append("); \n\n");
//            sql.append("INSERT INTO `CustomItemSet` (`createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`,\n" +
//                    "\t\t\t\t\t\t\t `updateState`, `status`, `versionNumber`, `validationDate`, `deploymentDate`, `fk_id_subIssuer`) VALUES \n");
//            for (CustomItemSet customItemSet : customItemSetList) {
//                sql.append("(");
//                sql.append("@createdBy").append(", ");
//                sql.append("NOW()").append(", ");
//                sql.append("CONCAT('customitemset_',  @BankUB, '_").append(customItemSet.getDescription()).append("'), ");
//                sql.append("NULL").append(", ");
//                sql.append("NULL").append(", ");
//                sql.append("CONCAT('customitemset_',  @BankUB, '_").append(customItemSet.getName()).append("'), ");
//                sql.append("@updateState").append(", ");
//                sql.append("@status").append(", ");
//                sql.append(customItemSet.getVersionNumber()).append(", ");
//                sql.append("NULL").append(", ");
//                sql.append("NULL").append(", ");
//                sql.append("@subIssuerId_").append(subIssuer.getSubIssuerCode());
//                if(customItemSetList.indexOf(customItemSet) == customItemSetList.size() - 1){
//                    sql.append(");\n\n");
//                }
//                else {
//                    sql.append("),\n");
//                }
//            }
//        }
//        return sql.toString();
//    }
//
//    private String generateProfileSqlScriptFromJson(List<AcsBoProfile> profileList, List<CustomItemSet> customItemSetList, List<String> authentMeansList) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("\n /* -------------- Profile ----------------- */ \n\n");
//        for (CustomItemSet customItemSet: customItemSetList){
//            sql.append("SET @customItemSet_").append(customItemSet.getName()).append(" = (SELECT id FROM `CustomItemSet` WHERE `name` = CONCAT('customItemSet_',  @BankUB, '_").append(customItemSet.getName()).append("')); \n");
//        }
//        sql.append("\n");
//        for (String authentMean: authentMeansList) {
//            sql.append("SET @authMean_").append(authentMean).append(" = (SELECT id FROM `AuthentMeans` WHERE `name` = '").append(authentMean).append("');\n");
//        }
//
//        for (AcsBoProfile profile: profileList){
//
//            sql.append("\nSET @subIssuerId_").append(profile.getSubIssuerCode()).append(" = (SELECT id FROM `SubIssuer` WHERE `code` = @subIssuerCode_").append(profile.getSubIssuerCode()).append(" AND `name` = @subIssuerNameAndLabel_").append(profile.getSubIssuerCode()).append("); \n");
//
//            sql.append("INSERT INTO `Profile` (`createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`,\n" +
//                    "\t\t\t\t\t\t`updateState`, `maxAttempts`,`dataEntryFormat`, `dataEntryAllowedPattern`, `fk_id_authentMeans`,\n" +
//                    "\t\t\t\t\t\t`fk_id_customItemSetCurrent`, `fk_id_customItemSetOld`, `fk_id_customItemSetNew`, `fk_id_subIssuer`) VALUES \n");
//            sql.append("(@createdBy, ");
//            sql.append("NOW(), ");
//            sql.append("'").append(profile.getDescription()).append("', ");
//            sql.append("NULL, ");
//            sql.append("NULL, ");
//            sql.append("CONCAT(@BankUB, '_").append(profile.getName()).append("'), ");
//            sql.append("@updateState, ");
//            sql.append(profile.getMaxAttempts()).append(", ");
//            sql.append("'").append(profile.getDataEntryFormat()).append("', ");
//            sql.append("'").append(profile.getDataEntryAllowedPattern()).append("', ");
//            if (profile.getAuthentMeans().equalsIgnoreCase("ACCEPT") || profile.getAuthentMeans().equalsIgnoreCase("DECLINE")) {
//                sql.append("@authMean_ACCEPT, ");
//            }
//            else if (profile.getAuthentMeans().equalsIgnoreCase("REFUSAL")){
//                sql.append("@authMean_REFUSAL, ");
//            }
//            else if (profile.getAuthentMeans().equalsIgnoreCase("MOBILE_APP_EXT")){
//                sql.append("@authMean_MOBILE_APP_EXT, ");
//            }
//
//            if (profile.getCustomItemSetName().equalsIgnoreCase("MOBILE_APP_EXT")){
//                sql.append("@customItemSet_MOBILE_APP_EXT, ");
//            }
//            else if (profile.getCustomItemSetName().equalsIgnoreCase("FRAUD_REFUSAL")){
//                sql.append("@customItemSet_FRAUD_REFUSAL, ");
//            }
//            else if (profile.getCustomItemSetName().equalsIgnoreCase("DEFAULT_REFUSAL")) {
//                sql.append("@customItemSet_DEFAULT_REFUSAL, ");
//            }
//            else if (profile.getCustomItemSetName().equalsIgnoreCase("NULL")) {
//                sql.append("NULL, ");
//            }
//            sql.append("NULL, ");
//            sql.append("NULL, ");
//            sql.append("@subIssuerId_").append(profile.getSubIssuerCode()).append("");
//            sql.append("); \n\n");
//        }
//        sql.append("\n /* -------------------End Profile-------------------- */ \n\n");
//        return sql.toString();
//    }
//
//    private String generateRuleSqlScriptFromJson(List<Rule> ruleList, AcsBoGeneral general) {
//
//        StringBuilder sql = new StringBuilder();
//
//        for (Rule rule : ruleList) {
//            sql.append("\nSET @profileId" + rule.getProfileName() + " = (SELECT id FROM `Profile` WHERE `name` = CONCAT(@BankUB, '_" + rule.getProfileName() + "'));");
//
//        }
//
//        sql.append("\n\nINSERT INTO `Rule` (`createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`,\n" +
//                "\t\t\t\t\t`updateState`, `orderRule`, `fk_id_profile`) VALUES \n");
//
//        for (int i = 0; i < ruleList.size(); i++) {
//
//            sql.append("(");
//            sql.append("@createdBy").append(", ");
//            sql.append("'").append(general.getCreationDate()).append("', ");
//            sql.append("'").append(ruleList.get(i).getDescription()).append("', ");
//            sql.append("'").append("NULL").append("', ");
//            sql.append("'").append("NULL").append("', ");
//            sql.append("'").append(ruleList.get(i).getName()).append("', ");
//            sql.append("'").append(general.getUpdateState()).append("', ");
//            sql.append("'").append(i + 1).append("', ");
//            sql.append("'").append("@profileId" + ruleList.get(i).getProfileName()).append("'");
//            sql.append(");\n");
//        }
//        return sql.toString();
//    }
//
//    private String generateRuleConditionSqlScriptFromJson(List<RuleCondition> ruleConditions, AcsBoGeneral general) {
//
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("\n\nINSERT INTO `RuleCondition` (`createdBy`, `creationDate`, `description`, `lastUpdateBy`, `lastUpdateDate`, `name`,\n" +
//                "\t\t\t\t\t\t\t `updateState`, `fk_id_rule`) VALUES\n");
//
//        for (RuleCondition ruleCondition : ruleConditions) {
//
//            sql.append("(");
//            sql.append("@createdBy").append(", ");
//            sql.append("'").append(general.getCreationDate()).append("', ");
//            sql.append("'").append("NULL").append("', ");
//            sql.append("'").append("NULL").append("', ");
//            sql.append("'").append("NULL").append("', ");
//            sql.append("'").append("C1_P_".concat(general.getBankB()).concat(ruleCondition.getName())).append("', ");
//            sql.append("'").append(general.getUpdateState()).append("', ");
//            sql.append(");\n");
//        }
//        return sql.toString();
//    }
//
//    private String generateMerchantPivotListSqlScriptFromJson(MerchantPivotList merchantPivot) {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("\n\nINSERT INTO `MerchantPivotList` " +
//                "(`level`, " +
//                "`keyword`, " +
//                "`type`, " +
//                "`amount`, " +
//                "`display`," +
//                "`forceAuthent`, " +
//                "`fk_id_issuer`, " +
//                "`fk_id_subIssuer`, " +
//                "`expertMode`) ");
//        sql.append("VALUES \n");
//        sql.append("(");
//        sql.append("'").append(merchantPivot.getLevel()).append("', ");
//        sql.append("'").append(merchantPivot.getKeyword()).append("', ");
//        sql.append("'").append(merchantPivot.getType()).append("', ");
//        sql.append(" ").append(merchantPivot.getAmount()).append(", ");
//        sql.append(" ").append(merchantPivot.getDisplay()).append(", ");
//        sql.append(" ").append(merchantPivot.getForceAuthent()).append(", ");
//        sql.append(" ").append("@issuerId").append(", ");
//        sql.append(" ").append("@subIssuerId").append(", ");
//        sql.append(" ").append(merchantPivot.getExpertMode()).append(" ");
//        sql.append(");\n");
//
//        return sql.toString();
//    }
//
//    private String generateConditionMeans(List<ConditionMeansProcessStatus> conditionMeansProcessStatuses) {
//
//        StringBuilder sql = new StringBuilder();
//
//        for (ConditionMeansProcessStatus conditionMeansProcessStatus : conditionMeansProcessStatuses) {
//
//            sql.append("\nSET @authentMeans = (SELECT id FROM `AuthentMeans` WHERE `name` = " + conditionMeansProcessStatus.getAuthentMeansName() + ");");
//            sql.append("\nSET @conditionId = (SELECT id FROM `RuleCondition` WHERE `name` = CONCAT('C1_P_',@BankUB, '_" + conditionMeansProcessStatus.getRuleConditionName() + "'));");
//            sql.append("\nSET @meansProcessStatusesId = (SELECT id FROM `MeansProcessStatuses` WHERE `fk_id_authentMean` = "+ "@authentMeans" +"AND meansProcessStatusType" + conditionMeansProcessStatus.getMeansProcessStatusType() + " AND reversed = "+conditionMeansProcessStatus.getReversed()+"'));");
//
//            sql.append("\n\nINSERT INTO `Condition_MeansProcessStatuses` (`id_condition`, `id_meansProcessStatuses`) VALUES");
//            sql.append("(");
//            sql.append("'").append("@conditionId").append("', ");
//            sql.append("'").append("@meansProcessStatusesId").append("'");
//            sql.append(");\n");
//        }
//        return sql.toString();
//    }
//
//    private String generateTransactionStatusesMeans(List<ConditionTransactionStatuses> conditionTransactionStatusesMeans) {
//
//        StringBuilder sql = new StringBuilder();
//
//        for (ConditionTransactionStatuses conditionTransactionStatuses1 : conditionTransactionStatusesMeans) {
//
//            sql.append("\nSET @conditionId = (SELECT id FROM `RuleCondition` WHERE `name` = CONCAT('C1_P_',@BankUB, '_" + conditionTransactionStatuses1.getRuleConditionName() + "'));");
//            sql.append("\nSET @meansProcessStatusesId = (SELECT id FROM `TransactionStatuses` WHERE  transactionStatusType" + conditionTransactionStatuses1.getTransactionStatusType() + " AND reversed = "+ conditionTransactionStatuses1.getReversed()+"'));");
//
//            sql.append("\n\nINSERT INTO `Condition_TransactionStatuses` (`id_condition`, `id_transactionStatuses`) VALUES");
//            sql.append("(");
//            sql.append("'").append("@conditionId").append("', ");
//            sql.append("'").append("@meansProcessStatusesId").append("'");
//            sql.append(");\n");
//        }
//        return sql.toString();
//    }
}
