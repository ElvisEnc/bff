package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGeneralInfoResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGenericVoucherTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyImageResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyInitialPointsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevelResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse.LoyaltyCity;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchant;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStatementResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStoreFeaturedResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTermsConditionsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactionsResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyPersonCampRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategory;
import bg.com.bo.bff.providers.dtos.request.loyalty.CityCategoryMerchantsAPIRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGeneralInformationResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetCategoryPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetGenericTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetImageResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetInitialPointsVamosResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetStoreFeaturedResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTermsConditionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPostRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatementPointsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.MerchantCampaignVoucherAPIRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyCityListAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyFeaturedMerchantAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyMerchantCampaignVoucherAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyQrTransactionAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyTradeCategoryAPIResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ILoyaltyMapper {

    LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response);

    LoyaltyPointResponse convertResponse(LoyaltyPointServerResponse response);

    LoyaltyRedeemVoucherResponse convertResponse(LoyaltyPostRegisterRedeemVoucherResponse response);

    LoyaltyLevelResponse convertResponse(LoyaltyGetLevelResponse response);

    LoyaltyInitialPointsResponse convertResponse(LoyaltyGetInitialPointsVamosResponse response);

    List<LoyaltyStatementResponse> convertResponse(List<LoyaltyStatementPointsResponse> response);

    LoyaltyGeneralInfoResponse convertResponse(LoyaltyGeneralInformationResponse response);

    LoyaltyImageResponse convertResponse(LoyaltyGetImageResponse response);

    List<LoyaltyLevelResponse> convertLevels(List<LoyaltyGetLevelResponse> levels);

    List<LoyaltyImageResponse> convertResponseImage(List<LoyaltyGetImageResponse> response);

    List<LoyaltyCategoryPromotionResponse> convertResponseCategoryProm(List<LoyaltyGetCategoryPromotionResponse> response);

    LoyaltyTermsConditionsResponse convertResponse(LoyaltyGetTermsConditionsResponse response);

    LoyaltyPromotionResponse convertResponse(LoyaltyGetPromotionResponse response);

    List<LoyaltyStoreFeaturedResponse> convertStoreFeatured(List<LoyaltyGetStoreFeaturedResponse> response);

    LoyaltyGenericVoucherTransactionResponse convertVoucherQrTransaction(LoyaltyGetGenericTransactionsResponse response);

    List<LoyaltyVoucherTransactionsResponse> convertVoucherTransaction(List<LoyaltyGetTransactionsResponse> response);

    Map<String, String> mapperRequestService(String personId);

    LoyaltyRegisterSubscriptionRequest mapperRequest(String personId, String accountId, RegisterSubscriptionRequest request);

    LoyaltyRegisterRedeemVoucherRequest mapperRequest(String personId, String codeSystem, RegisterRedeemVoucherRequest request);

    LoyaltyStatementPointRequest mapperRequest(String personId, String codeSystem, LoyaltyStatementRequest request);

    LoyaltyGetImagesRequest mapperRequest(LoyaltyImageRequest request);

    LoyaltyPersonCampRequest mapperRequest(String personId);

    List<LoyaltyTradeCategory> convertResponseTradeCategory(List<LoyaltyTradeCategoryAPIResponse> apiResponse);

    List<LoyaltyFeaturedMerchant> convertResponseFeaturedMerchant(List<LoyaltyFeaturedMerchantAPIResponse> apiResponse);

    List<LoyaltyCity> convertResponseCity(List<LoyaltyCityListAPIResponse> apiResponse);

    CityCategoryMerchantsAPIRequest mapperRequest(String personId, UUID cityId, UUID categoryId);

    LoyaltyQrTransactionResponse convertResponseQrTransaction(LoyaltyQrTransactionAPIResponse apiResponse);

    MerchantCampaignVoucherAPIRequest mapperRequest(UUID merchantId, UUID categoryId, int campaignId);

    LoyaltyMerchantVoucherCategoryResponse convertResponse(LoyaltyMerchantCampaignVoucherAPIResponse response);

    List<LoyaltyRedeemVoucherResponse> convertVoucherTransactedListResponse(List<LoyaltyPostRegisterRedeemVoucherResponse> apiResponse);

}
