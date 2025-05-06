package bg.com.bo.bff.providers.interfaces;

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
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyMerchantCampaignVoucherAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPostRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyQrTransactionAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatementPointsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatusResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface ILoyaltyProvider {

    LoyaltySystemCodeServerResponse getSystemCode(String personId, Map<String, String> headers) throws IOException;

    LoyaltyPointServerResponse getSumPoint(String codeSystem, Map<String, String> headers) throws IOException;

    LoyaltyRegisterSubscriptionResponse registerSubscription(
            LoyaltyRegisterSubscriptionRequest requestServer, Map<String, String> headers) throws IOException;

    LoyaltyPostRegisterRedeemVoucherResponse registerRedeemVoucher(LoyaltyRegisterRedeemVoucherRequest requestServer, Map<String, String> headers) throws IOException;

    LoyaltyGetLevelResponse getLevel(Map<String, String> headers, String personId) throws IOException;

    LoyaltyPointServerResponse getPointsPeriod(Map<String, String> headers, String codeSystem) throws IOException;

    LoyaltyGetInitialPointsVamosResponse getInitialPointsVAMOS(Map<String, String> headers, String personId) throws IOException;

    LoyaltyStatusResponse verifySubscription(Map<String, String> headers, String personId) throws IOException;

    List<LoyaltyStatementPointsResponse> statementPoints(LoyaltyStatementPointRequest request, Map<String, String> headers) throws IOException;

    LoyaltyGeneralInformationResponse getGeneralInformation(Map<String, String> headers, String personId) throws IOException;

    LoyaltyGetImageResponse getImageInformation(String imageId) throws IOException;

    List<LoyaltyGetImageResponse> getImagesInformation(LoyaltyGetImagesRequest request) throws IOException;

    List<LoyaltyGetCategoryPromotionResponse> getCategoryPromotions(Map<String, String> headers) throws IOException;

    List<LoyaltyGetLevelResponse> getCategoryPointsLevels(Map<String, String> headers) throws IOException;

    LoyaltyGetTermsConditionsResponse termsConditions(LoyaltyPersonCampRequest request, Map<String, String> headers) throws IOException;

    LoyaltyStatusResponse checkFlow(Map<String, String> headers, String personId) throws IOException;

    LoyaltyGetPromotionResponse getPromotions(Map<String, String> headers, String promotionId) throws IOException;

    List<LoyaltyGetStoreFeaturedResponse> getStoreFeatured(Map<String, String> headers) throws IOException;

    LoyaltyGetGenericTransactionsResponse getQRTransactions(Map<String, String> headers, String voucherId, String typeVoucher) throws IOException;

    List<LoyaltyGetTransactionsResponse> getVoucherTransactions(Map<String, String> headers, String codeSystem, String status) throws IOException;

    List<LoyaltyGetTradeCategoryResponse> getTradeCategories(Map<String, String> headers, String personId)
            throws IOException;

    List<LoyaltyFeaturedMerchantAPIResponse> getFeaturedMerchant(Map<String, String> headers, String personId)
            throws IOException;

    List<LoyaltyCityListAPIResponse> getCityList(Map<String, String> header, String personId) throws IOException;

    List<LoyaltyFeaturedMerchantAPIResponse> getCityCategoryMerchants(
            Map<String, String> headers, CityCategoryMerchantsAPIRequest request
    ) throws IOException;

    LoyaltyQrTransactionAPIResponse getVoucherDetail(
            Map<String, String> headers, UUID voucherId, String voucherType
    ) throws IOException;

    LoyaltyMerchantCampaignVoucherAPIResponse getMerchantCampaignVouchers(
            Map<String, String> headers, MerchantCampaignVoucherAPIRequest request
    ) throws IOException;

    List<LoyaltyPostRegisterRedeemVoucherResponse> getVoucherTransactedList(
            Map<String, String> headers, String personId, int systemCode, String state
    ) throws IOException;


}
