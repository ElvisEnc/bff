package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGeneralInfoResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyImageResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyInitialPointsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevelResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStatementResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStoreFeaturedResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTermsConditionsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGenericVoucherTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactionsResponse;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyPersonCampRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LoyaltyMapper implements ILoyaltyMapper{
    String campaign = "1";

    @Override
    public LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response) {
        return LoyaltySystemCodeResponse.builder()
                .codeSystem(response.getCodeSystem())
                .build();
    }

    @Override
    public LoyaltyPointResponse convertResponse(LoyaltyPointServerResponse response) {
        return LoyaltyPointResponse.builder()
                .points(response.getPoint())
                .build();
    }

    @Override
    public LoyaltyRedeemVoucherResponse convertResponse(LoyaltyPostRegisterRedeemVoucherResponse response) {
        return LoyaltyRedeemVoucherResponse.builder()
                .identifier(response.getIdentifier())
                .codeVoucher(response.getCodeVoucher())
                .idCampaign(response.getIdCampaign())
                .holderName(response.getHolderName())
                .documentHolder(response.getDocumentHolder())
                .beneficiaryName(response.getBeneficiaryName())
                .beneficiaryDocument(response.getBeneficiaryDocument())
                .idPerson(response.getIdPerson())
                .codePerson(response.getCodePerson())
                .dateCreation(response.getDateCreation())
                .dateVoucher(response.getDateVoucher())
                .valueVoucher(response.getValueVoucher())
                .expirationDate(response.getExpirationDate())
                .idBenefit(response.getIdBenefit())
                .name(response.getName())
                .description(response.getDescription())
                .banner(response.getBanner())
                .note(response.getNote())
                .status(response.getStatus())
                .typeValue(response.getTypeValue())
                .trade(mapTrade(response.getTrade()))
                .redeemVoucher(mapRedeemVoucher(response.getRedeemVoucher()))
                .build();
    }

    @Override
    public LoyaltyLevelResponse convertResponse(LoyaltyGetLevelResponse response) {
        return LoyaltyLevelResponse.builder()
                .identifier(response.getIdentifier())
                .name(response.getName())
                .minimumScore(response.getMinimumScore())
                .maximumScore(response.getMaximumScore())
                .idCampaign(response.getIdCampaign())
                .idLevelNext(response.getIdLevelNext())
                .build();
    }

    @Override
    public LoyaltyInitialPointsResponse convertResponse(LoyaltyGetInitialPointsVamosResponse response) {
        return LoyaltyInitialPointsResponse.builder()
                .points(response.getPointsVamos())
                .datePoints(response.getDatePointsVamos())
                .pointsLoyalty(response.getPointsLoyalty())
                .build();
    }

    @Override
    public List<LoyaltyStatementResponse> convertResponse(List<LoyaltyStatementPointsResponse> response) {
        if (response == null || response.isEmpty()) {
            return List.of();
        }
        return response.stream()
                .map(item -> LoyaltyStatementResponse.builder()
                        .action(item.getAction())
                        .comment(item.getComment())
                        .dateCreation(item.getDateCreation())
                        .origin(item.getOrigin())
                        .campaignScore(item.getCampaignScore())
                        .build())
                .toList();
    }

    @Override
    public LoyaltyGeneralInfoResponse convertResponse(LoyaltyGeneralInformationResponse response) {
        return LoyaltyGeneralInfoResponse.builder()
                .codeSystem(response.getCodeSystem())
                .level(convertLevel(response.getLevel()))
                .levels(convertLevels(response.getLevels()))
                .points(response.getPoints())
                .pointsPeriod(response.getPointsPeriod())
                .build();
    }

    @Override
    public LoyaltyImageResponse convertResponse(LoyaltyGetImageResponse response) {
        return LoyaltyImageResponse.builder()
                .identifier(response.getIdentifier())
                .idImageMongo(response.getIdImageMongo())
                .filename(response.getFilename())
                .fileType(response.getFileType())
                .fileContent(response.getFileContent())
                .pathImage(response.getPathImage())
                .build();
    }

    @Override
    public List<LoyaltyImageResponse> convertResponseImage(List<LoyaltyGetImageResponse> response) {
        if (response == null || response.isEmpty()) {
            return List.of();
        }
        return response.stream()
                .map(img -> LoyaltyImageResponse.builder()
                        .identifier(img.getIdentifier() != 0 ? img.getIdentifier() : null)
                        .idImageMongo(img.getIdImageMongo())
                        .filename(img.getFilename())
                        .fileType(img.getFileType())
                        .fileContent(img.getFileContent())
                        .pathImage(img.getPathImage())
                        .build())
                .toList();
    }

    @Override
    public List<LoyaltyCategoryPromotionResponse> convertResponseCategoryProm(List<LoyaltyGetCategoryPromotionResponse> response) {
        if (response == null || response.isEmpty()) {
            return List.of();
        }
        return response.stream()
                .map(cat -> LoyaltyCategoryPromotionResponse.builder()
                        .identifier(cat.getIdentifier())
                        .name(cat.getName())
                        .text(cat.getText())
                        .link(cat.getLink())
                        .routeImageThumbnail(cat.getRouteImageThumbnail())
                        .build())
                .toList();
    }

    @Override
    public LoyaltyTermsConditionsResponse convertResponse(LoyaltyGetTermsConditionsResponse response) {
        LoyaltyGetTermsConditionsResponse.Person person = response.getInformationPerson();
        return LoyaltyTermsConditionsResponse.builder()
                .contractName(response.getContractName())
                .contract(response.getContract())
                .informationPerson(
                        LoyaltyTermsConditionsResponse.PersonData.builder()
                                .documentNumber(person.getDocumentNumber())
                                .documentType(person.getDocumentType())
                                .namePerson(person.getNamePerson())
                                .build()
                )
                .build();
    }

    @Override
    public LoyaltyPromotionResponse convertResponse(LoyaltyGetPromotionResponse response) {
        if (response == null) {
            return null;
        }
        LoyaltyImageResponse image = null;
        if (response.getImage() != null) {
            image = convertResponse(response.getImage());
        }

        return LoyaltyPromotionResponse.builder()
                .identifier(response.getIdentifier())
                .namePromotion(response.getNamePromotion())
                .text(response.getText())
                .link(response.getLink())
                .imagePath(response.getImagePath())
                .image(image)
                .build();
    }

    @Override
    public List<LoyaltyStoreFeaturedResponse> convertStoreFeatured(List<LoyaltyGetStoreFeaturedResponse> response) {
        return response.stream().map(store ->
                LoyaltyStoreFeaturedResponse.builder()
                        .identifier(store.getIdentifier())
                        .name(store.getName())
                        .description(store.getDescription())
                        .logo(store.getLogo())
                        .isFeatured(store.getIsFeatured())
                        .categoryId(store.getCategoryId())
                        .category(
                                store.getCategory() != null ? LoyaltyStoreFeaturedResponse.Category.builder()
                                        .categoryId(store.getCategory().getCategoryId())
                                        .nameCategory(store.getCategory().getNameCategory())
                                        .iconCategory(store.getCategory().getIconCategory())
                                        .build() : null
                        )
                        .isActive(store.getIsActive())
                        .cheaper(store.getCheaper())
                        .build()
        ).toList();
    }

    @Override
    public LoyaltyGenericVoucherTransactionResponse convertVoucherQrTransaction(LoyaltyGetGenericTransactionsResponse response) {
        if (response == null) {
            return null;
        }
        return LoyaltyGenericVoucherTransactionResponse.builder()
                .identifier(response.getIdentifier())
                .voucherCode(response.getVoucherCode())
                .campaignId(response.getCampaignId())
                .holderName(response.getHolderName())
                .holderDocument(response.getHolderDocument())
                .beneficiaryName(response.getBeneficiaryName())
                .beneficiaryDocument(response.getBeneficiaryDocument())
                .personId(response.getPersonId())
                .personCode(response.getPersonCode())
                .creationDate(response.getCreationDate())
                .redemptionDate(response.getRedemptionDate())
                .redemptionValue(response.getRedemptionValue())
                .expirationDate(response.getExpirationDate())
                .benefitId(response.getBenefitId())
                .name(response.getName())
                .description(response.getDescription())
                .banner(response.getBanner())
                .note(response.getNote())
                .status(response.getStatus())
                .voucherType(response.getVoucherType())
                .store(mapStore(response.getStore()))
                .voucherConsumption(mapVoucherConsumption(response.getVoucherConsumption()))
                .build();
    }

    @Override
    public List<LoyaltyVoucherTransactionsResponse> convertVoucherTransaction(List<LoyaltyGetTransactionsResponse> response) {
        if (response == null || response.isEmpty()) {
            return List.of();
        }
        return response.stream()
                .map(r -> LoyaltyVoucherTransactionsResponse.builder()
                        .identifier(r.getIdentifier())
                        .voucherCode(r.getVoucherCode())
                        .campaignId(r.getCampaignId())
                        .holderName(r.getHolderName())
                        .holderDocument(r.getHolderDocument())
                        .beneficiaryName(r.getBeneficiaryName())
                        .beneficiaryDocument(r.getBeneficiaryDocument())
                        .personId(r.getPersonId())
                        .personCode(r.getPersonCode())
                        .creationDate(r.getCreationDate())
                        .redemptionValue(r.getRedemptionValue())
                        .expirationDate(r.getExpirationDate())
                        .benefitId(r.getBenefitId())
                        .name(r.getName())
                        .description(r.getDescription())
                        .banner(r.getBanner())
                        .redemptionDate(r.getRedemptionDate())
                        .voucherType(r.getVoucherType())
                        .note(r.getNote())
                        .status(r.getStatus())
                        .store(mapStore(r.getStore()))
                        .voucherConsumption(mapVoucherConsumption(r.getVoucherConsumption()))
                        .redeemed(r.getRedeemed())
                        .managerId(r.getManagerId())
                        .voucherCost(r.getVoucherCost())
                        .assumedPercentage(r.getAssumedPercentage())
                        .build())
                .collect(Collectors.toList());
    }

    private LoyaltyStoreFeaturedResponse mapStore(LoyaltyGetStoreFeaturedResponse store) {
        if (store == null) return null;

        return LoyaltyStoreFeaturedResponse.builder()
                .identifier(store.getIdentifier())
                .name(store.getName())
                .description(store.getDescription())
                .logo(store.getLogo())
                .isFeatured(store.getIsFeatured())
                .categoryId(store.getCategoryId())
                .category(mapCategory(store.getCategory()))
                .isActive(store.getIsActive())
                .cheaper(store.getCheaper())
                .build();
    }
    private LoyaltyStoreFeaturedResponse.Category mapCategory(LoyaltyGetStoreFeaturedResponse.GetCategory category) {
        if (category == null) return null;

        return LoyaltyStoreFeaturedResponse.Category.builder()
                .categoryId(category.getCategoryId())
                .nameCategory(category.getNameCategory())
                .iconCategory(category.getIconCategory())
                .build();
    }

    private LoyaltyGenericVoucherTransactionResponse.VoucherConsumption mapVoucherConsumption(LoyaltyGetGenericTransactionsResponse.GetVoucherConsumption voucherConsumption) {
        if (voucherConsumption == null) return null;

        return LoyaltyGenericVoucherTransactionResponse.VoucherConsumption.builder()
                .valueVoucher(voucherConsumption.getValueVoucher())
                .valueType(voucherConsumption.getValueType())
                .build();
    }

    private LoyaltyLevelResponse convertLevel(LoyaltyGetLevelResponse level) {
        if (level == null) {
            return null;
        }
        return convertResponse(level);
    }

    @Override
    public List<LoyaltyLevelResponse> convertLevels(List<LoyaltyGetLevelResponse> levels) {
        if (levels == null || levels.isEmpty()) {
            return List.of();
        }
        return levels.stream()
                .map(this::convertLevel)
                .toList();
    }

    @Override
    public Map<String, String> mapperRequestService(String personId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("sesion", "0704202517010198a33421103b7311");
        headers.put("idpersona", personId);
        return headers;
    }

    @Override
    public LoyaltyRegisterSubscriptionRequest mapperRequest(String personId, String accountId, RegisterSubscriptionRequest request) {
        return LoyaltyRegisterSubscriptionRequest.builder()
                .signatureDigital(true)
                .idPerson(personId)
                .codeCampaign(campaign)
                .jtsOidAccountNumber(accountId)
                .email(request.getEmail())
                .subscriptionOrigin(CanalMW.GANAMOVIL.getDescription())
                .build();
    }

    @Override
    public LoyaltyRegisterRedeemVoucherRequest mapperRequest(String personId, String codeSystem, RegisterRedeemVoucherRequest request) {
        return LoyaltyRegisterRedeemVoucherRequest.builder()
                .codigoCampana(campaign)
                .idCodigoSistema(codeSystem)
                .idBeneficio(request.getIdBenefit())
                .tipoBeneficio(request.getTypeBenefit())
                .informacion(
                        request.getInformation() != null
                                ? LoyaltyRegisterRedeemVoucherRequest.Information.builder()
                                .nombreBeneficiario(request.getInformation().getBeneficiaryName())
                                .documentoBeneficiario(request.getInformation().getBeneficiaryDocument())
                                .parentesco(request.getInformation().getBeneficiaryRelationship())
                                .build()
                                : null
                )
                .build();
    }

    @Override
    public LoyaltyStatementPointRequest mapperRequest(String personId, String codeSystem, LoyaltyStatementRequest request) {
        return LoyaltyStatementPointRequest.builder()
                .codigoPersona(codeSystem)
                .codigoCampana(campaign)
                .fechaInicial(request.getStartDate())
                .fechaFinal(request.getEndDate())
                .build();
    }

    @Override
    public LoyaltyGetImagesRequest mapperRequest(LoyaltyImageRequest request) {
        List<LoyaltyGetImagesRequest.Ruta> rutas = request.getFilePaths().stream()
                .map(filePath -> LoyaltyGetImagesRequest.Ruta.builder()
                        .filePath(filePath.getFile())
                        .build())
                .toList();

        return LoyaltyGetImagesRequest.builder()
                .rutas(rutas)
                .build();
    }

    @Override
    public LoyaltyPersonCampRequest mapperRequest(String personId) {
        return LoyaltyPersonCampRequest.builder()
                .idPersona(personId)
                .codigoCampana(campaign)
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyTrade mapTrade(LoyaltyPostRegisterRedeemVoucherResponse.LoyaltyGetTrade sourceTrade) {
        return LoyaltyRedeemVoucherResponse.LoyaltyTrade.builder()
                .identifierTrade(sourceTrade.getIdentifierTrade())
                .nameTrade(sourceTrade.getNameTrade())
                .descriptionTrade(sourceTrade.getDescriptionTrade())
                .logoTrade(sourceTrade.getLogoTrade())
                .category(mapCategory(sourceTrade.getCategory()))
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyCategory mapCategory(LoyaltyPostRegisterRedeemVoucherResponse.LoyaltyCategory sourceCategory) {
        return LoyaltyRedeemVoucherResponse.LoyaltyCategory.builder()
                .identifierCategory(sourceCategory.getIdentifierCategory())
                .nameCategory(sourceCategory.getNameCategory())
                .iconCategory(sourceCategory.getIconCategory())
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher mapRedeemVoucher(LoyaltyPostRegisterRedeemVoucherResponse.LoyaltyRedeemVoucher sourceVoucher) {
        return LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher.builder()
                .valueRedeemVoucher(sourceVoucher.getValueRedeemVoucher())
                .typeValueRedeemVoucher(sourceVoucher.getTypeValueRedeemVoucher())
                .build();
    }
}

