package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse.LoyaltyCity;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchant;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategory;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ILoyaltyMapper {

    LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response);

    LoyaltySumPointResponse convertResponse(LoyaltySumPointServerResponse response);

    LoyaltyRedeemVoucherResponse convertResponse(LoyaltyRegisterRedeemVoucherResponse response);

    LoyaltyLevel convertResponse(LoyaltyGetLevelResponse response);

    Map<String, String> mapperRequestService(String personId);

    LoyaltyRegisterSubscriptionRequest mapperRequest(String personId, String accountId, RegisterSubscriptionRequest request);

    LoyaltyRegisterRedeemVoucherRequest mapperRequest(String personId, String codeSystem, RegisterRedeemVoucherRequest request);

    List<LoyaltyTradeCategory> convertResponseTradeCategory(List<LoyaltyTradeCategoryAPIResponse> apiResponse);

    List<LoyaltyFeaturedMerchant> convertResponseFeaturedMerchant(List<LoyaltyFeaturedMerchantAPIResponse> apiResponse);

    List<LoyaltyCity> convertResponseCity(List<LoyaltyCityListAPIResponse> apiResponse);

    CityCategoryMerchantsAPIRequest mapperRequest(String personId, UUID cityId, UUID categoryId);

    LoyaltyQrTransactionResponse convertResponseQrTransaction(LoyaltyQrTransactionAPIResponse apiResponse);

    MerchantCampaignVoucherAPIRequest mapperRequest(UUID merchantId, UUID categoryId, int campaignId);

    LoyaltyMerchantVoucherCategoryResponse convertResponse(LoyaltyMerchantCampaignVoucherAPIResponse response);

    List<LoyaltyRedeemVoucherResponse> convertVoucherTransactedListResponse(List<LoyaltyRegisterRedeemVoucherResponse> apiResponse);

}
