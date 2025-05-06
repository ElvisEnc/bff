package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse.LoyaltyCity;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchant;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGeneralInfoResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGenericVoucherTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyImageResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyInitialPointsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevelResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStatementResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStoreFeaturedResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTermsConditionsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherConsumptionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactionsResponse;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.providers.dtos.request.loyalty.CityCategoryMerchantsAPIRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyPersonCampRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.MerchantCampaignVoucherAPIRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyCityListAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyFeaturedMerchantAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGeneralInformationResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetCategoryPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetGenericTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetImageResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetInitialPointsVamosResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetStoreFeaturedResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTermsConditionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTradeCategoryResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyMerchantCampaignVoucherAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPostRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyQrTransactionAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatementPointsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class LoyaltyMapper implements ILoyaltyMapper {
    String campaign = "1";

    @Override
    public LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response) {
        return LoyaltySystemCodeResponse.builder().codeSystem(response.getCodeSystem()).build();
    }

    @Override
    public LoyaltyPointResponse convertResponse(LoyaltyPointServerResponse response) {
        return LoyaltyPointResponse.builder().points(response.getPoint()).build();
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
                        .category(mapCategory(store.getCategory()))
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
                .map(this::mapToVoucherTransaction)
                .toList();
    }

    @SuppressWarnings("java:S3252")
    private LoyaltyVoucherTransactionsResponse mapToVoucherTransaction(LoyaltyGetTransactionsResponse r) {
        return LoyaltyVoucherTransactionsResponse.builder()
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
                .redemptionDate(r.getRedemptionDate())
                .redemptionValue(r.getRedemptionValue())
                .expirationDate(r.getExpirationDate())
                .benefitId(r.getBenefitId())
                .name(r.getName())
                .description(r.getDescription())
                .banner(r.getBanner())
                .note(r.getNote())
                .status(r.getStatus())
                .store(mapStore(r.getStore()))
                .voucherType(r.getVoucherType())
                .voucherConsumption(mapVoucherConsumption(r.getVoucherConsumption()))
                .redeemed(r.getRedeemed())
                .managerId(r.getManagerId())
                .voucherCost(r.getVoucherCost())
                .assumedPercentage(r.getAssumedPercentage())
                .build();
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

    private LoyaltyVoucherConsumptionResponse mapVoucherConsumption(LoyaltyGetGenericTransactionsResponse.GetVoucherConsumption voucherConsumption) {
        if (voucherConsumption == null) return null;

        return LoyaltyVoucherConsumptionResponse.builder()
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
        headers.put("sesion", "2204202511305398a33421103b7311");
        headers.put("idpersona", personId);
        return headers;
    }

    @Override
    public LoyaltyRegisterSubscriptionRequest mapperRequest(
            String personId, String accountId, RegisterSubscriptionRequest request
    ) {
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
    public LoyaltyRegisterRedeemVoucherRequest mapperRequest(
            String personId, String codeSystem, RegisterRedeemVoucherRequest request
    ) {
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

        return LoyaltyGetImagesRequest.builder().rutas(rutas).build();
    }

    @Override
    public LoyaltyPersonCampRequest mapperRequest(String personId) {
        return LoyaltyPersonCampRequest.builder().idPersona(personId).codigoCampana(campaign).build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyTrade mapTrade(
            LoyaltyPostRegisterRedeemVoucherResponse.LoyaltyGetTrade sourceTrade
    ) {
        return LoyaltyRedeemVoucherResponse.LoyaltyTrade.builder()
                .identifierTrade(sourceTrade.getIdentifierTrade())
                .nameTrade(sourceTrade.getNameTrade())
                .descriptionTrade(sourceTrade.getDescriptionTrade())
                .logoTrade(sourceTrade.getLogoTrade())
                .category(mapCategory(sourceTrade.getCategory()))
                .build();
    }

    private LoyaltyTradeCategoryResponse mapCategory(LoyaltyGetTradeCategoryResponse category) {
        if (category == null) {
            return null;
        }
        return LoyaltyTradeCategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .nameCategory(category.getNameCategory())
                .iconCategory(category.getIconCategory())
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher mapRedeemVoucher(
            LoyaltyPostRegisterRedeemVoucherResponse.LoyaltyRedeemVoucher sourceVoucher
    ) {
        return LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher.builder()
                .valueRedeemVoucher(sourceVoucher.getValueRedeemVoucher())
                .typeValueRedeemVoucher(sourceVoucher.getTypeValueRedeemVoucher())
                .build();
    }

    @Override
    public List<LoyaltyTradeCategoryResponse> convertResponseTradeCategory(List<LoyaltyGetTradeCategoryResponse> apiResponse) {
        if (apiResponse == null)
            return Collections.emptyList();
        return apiResponse.stream()
                .map(data -> LoyaltyTradeCategoryResponse.builder()
                        .categoryId(data.getCategoryId())
                        .nameCategory(data.getNameCategory())
                        .iconCategory(data.getIconCategory())
                        .build())
                .toList();
    }

    @Override
    public List<LoyaltyFeaturedMerchant> convertResponseFeaturedMerchant(
            List<LoyaltyFeaturedMerchantAPIResponse> apiResponse
    ) {
        if (apiResponse == null)
            return Collections.emptyList();
        return apiResponse.stream()
                .map(data -> LoyaltyFeaturedMerchant.builder()
                        .merchantId(data.getMerchantId())
                        .merchantName(data.getMerchantName())
                        .merchantDescription(data.getMerchantDescription())
                        .merchantLogo(data.getMerchantLogo())
                        .merchantCheapest(data.getMerchantCheapest())
                        .isFeatured(data.getIsFeatured())
                        .isActive(data.getIsActive())
                        .category(LoyaltyTradeCategoryResponse
                                .builder()
                                .categoryId(data.getCategory().getCategoryId())
                                .nameCategory(data.getCategory().getNameCategory())
                                .iconCategory(data.getCategory().getIconCategory())
                                .build())
                        .build())
                .toList();
    }

    @Override
    public List<LoyaltyCity> convertResponseCity(List<LoyaltyCityListAPIResponse> apiResponse) {
        if (apiResponse == null)
            return Collections.emptyList();
        return apiResponse.stream()
                .map(data -> LoyaltyCity.builder()
                        .identifier(data.getId())
                        .name(data.getName())
                        .build())
                .toList();
    }

    @Override
    public CityCategoryMerchantsAPIRequest mapperRequest(String personId, UUID cityId, UUID categoryId) {
        return CityCategoryMerchantsAPIRequest.builder().idCiudad(cityId).idCategoria(categoryId).build();
    }

    @Override
    public LoyaltyQrTransactionResponse convertResponseQrTransaction(LoyaltyQrTransactionAPIResponse apiResponse) {
        return LoyaltyQrTransactionResponse.builder()
                .identifier(apiResponse.getId())
                .voucherCode(apiResponse.getVoucherCode())
                .campignId(apiResponse.getCampignId())
                .holderName(apiResponse.getHolderName())
                .documentHolder(apiResponse.getDocumentHolder())
                .recipientName(apiResponse.getRecipientName())
                .recipientDocument(apiResponse.getRecipientDocument())
                .personId(apiResponse.getPersonId())
                .personCode(apiResponse.getPersonCode())
                .registerDate(apiResponse.getRegisterDate())
                .redemptionDate(apiResponse.getRedemptionDate())
                .redemptionValue(apiResponse.getRedemptionValue())
                .expirationDate(apiResponse.getExpirationDate())
                .benefitId(apiResponse.getBenefitId())
                .voucherName(apiResponse.getVoucherName())
                .description(apiResponse.getDescription())
                .banner(apiResponse.getBanner())
                .note(apiResponse.getNote())
                .voucherType(apiResponse.getVoucherType())
                .voucherStatus(apiResponse.getVoucherStatus())
                .merchant(
                        LoyaltyFeaturedMerchant.builder()
                                .merchantCheapest(apiResponse.getMerchant().getMerchantCheapest())
                                .merchantName(apiResponse.getMerchant().getMerchantName())
                                .merchantDescription(apiResponse.getMerchant().getMerchantDescription())
                                .merchantLogo(apiResponse.getMerchant().getMerchantLogo())
                                .category(
                                        LoyaltyTradeCategoryResponse.builder()
                                                .categoryId(apiResponse.getMerchant().getCategory().getCategoryId())
                                                .nameCategory(apiResponse.getMerchant().getCategory().getNameCategory())
                                                .iconCategory(apiResponse.getMerchant().getCategory().getIconCategory())
                                                .build())

                                .build()
                )
                .travelVoucher(
                        LoyaltyQrTransactionResponse.TravelVoucher.builder()
                                .relationship(apiResponse.getTravelVoucher().getRelationship())
                                .travelVoucherType(apiResponse.getTravelVoucher().getTravelVoucherType())
                                .origin(apiResponse.getTravelVoucher().getOrigin())
                                .destination(apiResponse.getTravelVoucher().getDestination())
                                .build()
                )
                .build();
    }

    @Override
    public MerchantCampaignVoucherAPIRequest mapperRequest(UUID merchantId, UUID categoryId, int campaignId) {
        return MerchantCampaignVoucherAPIRequest.builder()
                .merchantId(merchantId).categoryId(categoryId).campaignId(campaignId).build();
    }

    @Override
    public LoyaltyMerchantVoucherCategoryResponse convertResponse(LoyaltyMerchantCampaignVoucherAPIResponse apiResponse) {
        return LoyaltyMerchantVoucherCategoryResponse.builder()
                .redemptionVoucher(mapVouchers(apiResponse.getRedemptionVoucher()))
                .travelVoucher(mapVouchers(apiResponse.getTravelVoucher()))
                .productVoucher(mapVouchers(apiResponse.getProductVoucher()))
                .discountVoucher(mapVouchers(apiResponse.getDiscountVoucher()))
                .build();
    }


    private LoyaltyVoucherResponse[] mapVouchers(LoyaltyGetVoucherResponse[] vouchers) {
        if (vouchers == null) return new LoyaltyVoucherResponse[0];

        return Arrays.stream(vouchers)
                .map(row -> LoyaltyVoucherResponse.builder()
                        .voucherId(row.getVoucherId())
                        .voucherName(row.getVoucherName())
                        .typeVoucher(row.getTypeVoucher())
                        .voucherBanner(row.getVoucherBanner())
                        .voucherDescription(row.getVoucherDescription())
                        .merchantId(row.getMerchantId())
                        .redemptionValue(row.getRedemptionValue())
                        .build())
                .toArray(LoyaltyVoucherResponse[]::new);
    }


    @Override
    public List<LoyaltyRedeemVoucherResponse> convertVoucherTransactedListResponse(
            List<LoyaltyPostRegisterRedeemVoucherResponse> apiResponse
    ) {
        if (apiResponse == null)
            return Collections.emptyList();
        return apiResponse.stream()
                .map(data -> LoyaltyRedeemVoucherResponse.builder()
                        .identifier(data.getIdentifier())
                        .codeVoucher(data.getCodeVoucher())
                        .idCampaign(data.getIdCampaign())
                        .holderName(data.getHolderName())
                        .documentHolder(data.getDocumentHolder())
                        .beneficiaryName(data.getBeneficiaryName())
                        .beneficiaryDocument(data.getBeneficiaryDocument())
                        .idPerson(data.getIdPerson())
                        .codePerson(data.getCodePerson())
                        .dateCreation(data.getDateCreation())
                        .dateVoucher(data.getDateVoucher())
                        .valueVoucher(data.getValueVoucher())
                        .expirationDate(data.getExpirationDate())
                        .idBenefit(data.getIdBenefit())
                        .name(data.getName())
                        .description(data.getDescription())
                        .banner(data.getBanner())
                        .note(data.getNote())
                        .status(data.getStatus())
                        .trade(
                                LoyaltyRedeemVoucherResponse.LoyaltyTrade
                                        .builder()
                                        .identifierTrade(data.getTrade().getIdentifierTrade())
                                        .nameTrade(data.getTrade().getNameTrade())
                                        .descriptionTrade(data.getTrade().getDescriptionTrade())
                                        .logoTrade(data.getTrade().getLogoTrade())
                                        .category(LoyaltyTradeCategoryResponse.builder()
                                                .categoryId(data.getTrade().getCategory().getCategoryId())
                                                .nameCategory(data.getTrade().getCategory().getNameCategory())
                                                .iconCategory(data.getTrade().getCategory().getIconCategory())
                                                .build())
                                        .build())
                        .typeValue(data.getTypeValue())
                        .redeemVoucher(mapRedeemVoucher(data.getRedeemVoucher()))
                        .build())
                .toList();
    }


}

