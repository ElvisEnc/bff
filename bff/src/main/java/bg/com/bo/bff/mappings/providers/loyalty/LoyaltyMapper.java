package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse.LoyaltyCity;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchant;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse.Voucher;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategory;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.providers.dtos.request.loyalty.CityCategoryMerchantsAPIRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.MerchantCampaignVoucherAPIRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyCityListAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyFeaturedMerchantAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyMerchantCampaignVoucherAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyQrTransactionAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyTradeCategoryAPIResponse;
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
        return LoyaltySystemCodeResponse.builder()
                .codeSystem(response.getCodeSystem())
                .build();
    }

    @Override
    public LoyaltySumPointResponse convertResponse(LoyaltySumPointServerResponse response) {
        return LoyaltySumPointResponse.builder()
                .points(response.getPoint())
                .build();
    }

    @Override
    public LoyaltyRedeemVoucherResponse convertResponse(LoyaltyRegisterRedeemVoucherResponse response) {
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
    public LoyaltyLevel convertResponse(LoyaltyGetLevelResponse response) {
        return LoyaltyLevel.builder()
                .identifier(response.getIdentifier())
                .name(response.getName())
                .minimumScore(response.getMinimumScore())
                .maximumScore(response.getMaximumScore())
                .idCampaign(response.getIdCampaign())
                .idLevelNext(response.getIdLevelNext())
                .build();
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
                .codeCampaign("1")
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

    private LoyaltyRedeemVoucherResponse.LoyaltyTrade mapTrade(LoyaltyRegisterRedeemVoucherResponse.LoyaltyTrade sourceTrade) {
        return LoyaltyRedeemVoucherResponse.LoyaltyTrade.builder()
                .identifierTrade(sourceTrade.getIdentifierTrade())
                .nameTrade(sourceTrade.getNameTrade())
                .descriptionTrade(sourceTrade.getDescriptionTrade())
                .logoTrade(sourceTrade.getLogoTrade())
                .category(mapCategory(sourceTrade.getCategory()))
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyCategory mapCategory(LoyaltyRegisterRedeemVoucherResponse.LoyaltyCategory sourceCategory) {
        return LoyaltyRedeemVoucherResponse.LoyaltyCategory.builder()
                .identifierCategory(sourceCategory.getIdentifierCategory())
                .nameCategory(sourceCategory.getNameCategory())
                .iconCategory(sourceCategory.getIconCategory())
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher mapRedeemVoucher(LoyaltyRegisterRedeemVoucherResponse.LoyaltyRedeemVoucher sourceVoucher) {
        return LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher.builder()
                .valueRedeemVoucher(sourceVoucher.getValueRedeemVoucher())
                .typeValueRedeemVoucher(sourceVoucher.getTypeValueRedeemVoucher())
                .build();
    }

    @Override
    public List<LoyaltyTradeCategory> convertResponseTradeCategory(List<LoyaltyTradeCategoryAPIResponse> apiResponse) {
        if (apiResponse == null)
            return Collections.emptyList();
        return apiResponse.stream()
                .map(data -> LoyaltyTradeCategory.builder()
                        .identifier(data.getId())
                        .name(data.getName())
                        .icon(data.getIcon())
                        .build())
                .toList();
    }

    @Override
    public List<LoyaltyFeaturedMerchant> convertResponseFeaturedMerchant(List<LoyaltyFeaturedMerchantAPIResponse> apiResponse) {
        if (apiResponse == null)
            return Collections.emptyList();
        return apiResponse.stream()
                .map(data -> LoyaltyFeaturedMerchant.builder()
                        .identifier(data.getId())
                        .name(data.getName())
                        .description(data.getDescription())
                        .logo(data.getLogo())
                        .cheapest(data.getCheapest())
                        .category(LoyaltyTradeCategory
                                .builder()
                                .identifier(data.getCategory().getId())
                                .name(data.getCategory().getName())
                                .icon(data.getCategory().getIcon())
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
        return CityCategoryMerchantsAPIRequest.builder()
                .idCiudad(cityId)
                .idCategoria(categoryId).build();
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
                                .identifier(apiResponse.getMerchant().getId())
                                .name(apiResponse.getMerchant().getName())
                                .description(apiResponse.getMerchant().getDescription())
                                .logo(apiResponse.getMerchant().getLogo())
                                .category(
                                        LoyaltyTradeCategory.builder()
                                                .identifier(apiResponse.getMerchant().getCategory().getId())
                                                .name(apiResponse.getMerchant().getCategory().getName())
                                                .icon(apiResponse.getMerchant().getCategory().getIcon())
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
                .merchantId(merchantId)
                .categoryId(categoryId)
                .campaignId(campaignId)
                .build();
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


    private Voucher[] mapVouchers(LoyaltyMerchantCampaignVoucherAPIResponse.Voucher[] vouchers) {
        if (vouchers == null) return new LoyaltyMerchantVoucherCategoryResponse.Voucher[0];

        return Arrays.stream(vouchers)
                .map(row -> Voucher.builder()
                        .identifier(row.getIdentifier())
                        .name(row.getName())
                        .typeVoucher(row.getTypeVoucher())
                        .banner(row.getBanner())
                        .description(row.getDescription())
                        .merchantId(row.getMerchantId())
                        .redemptionValue(row.getRedemptionValue())
                        .build())
                .toArray(Voucher[]::new);
    }


    @Override
    public List<LoyaltyRedeemVoucherResponse> convertVoucherTransactedListResponse(List<LoyaltyRegisterRedeemVoucherResponse> apiResponse) {
        if (apiResponse == null)
            return Collections.emptyList();
        return apiResponse.stream()
                .map(data -> LoyaltyRedeemVoucherResponse.builder()
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
                        .typeValue(data.getTypeValue())
                        .redeemVoucher(mapRedeemVoucher(data.getRedeemVoucher()))
                        .build())
                .toList();
    }


}

