package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchantListResponse;
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
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactedListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface ILoyaltyService {

    LoyaltySystemCodeResponse getSystemCode(String personId) throws IOException;

    LoyaltyPointResponse getSumPoint(String personId, String codeSystem) throws IOException;

    GenericResponse registerSubscription(String personId, String accountId, RegisterSubscriptionRequest request) throws IOException;

    LoyaltyRedeemVoucherResponse registerRedeemVoucher(String personId, String codeSystem, RegisterRedeemVoucherRequest request) throws IOException;

    LoyaltyLevelResponse getLevel(String personId) throws IOException;

    LoyaltyPointResponse getPointsPeriod(String personId, String accountId) throws IOException;

    LoyaltyInitialPointsResponse getInitialPointsVAMOS(String personId) throws IOException;

    GenericResponse verifySubscription(String personId) throws IOException;

    List<LoyaltyStatementResponse> statementPoints(String personId, String codeSystem, LoyaltyStatementRequest request) throws IOException;

    LoyaltyGeneralInfoResponse getGeneralInformation(String personId) throws IOException;

    LoyaltyImageResponse getImageInformation(String imageId) throws IOException;

    List<LoyaltyImageResponse> getImagesInformation(LoyaltyImageRequest request) throws IOException;

    List<LoyaltyCategoryPromotionResponse> getCategoryPromotions(String personId) throws IOException;

    List<LoyaltyLevelResponse> getCategoryPointsLevels(String personId) throws IOException;

    LoyaltyTermsConditionsResponse termsConditions(String personId) throws IOException;

    GenericResponse checkFlow(String personId) throws IOException;

    LoyaltyPromotionResponse getPromotions(String personId, String promotionId) throws IOException;

    List<LoyaltyStoreFeaturedResponse> getStoreFeatured(String personId) throws IOException;

    LoyaltyGenericVoucherTransactionResponse getQRTransactions(String personId, String voucherId, String typeVoucher) throws IOException;

    List<LoyaltyVoucherTransactionsResponse> getVoucherTransactions(String personId, String codeSystem, String status) throws IOException;

    LoyaltyTradeCategoryListResponse getTradeCategories(String personId) throws IOException;

    LoyaltyFeaturedMerchantListResponse getFeaturedMerchants(String personId) throws IOException;

    LoyaltyCityListResponse getCityList(String personId) throws IOException;

    LoyaltyFeaturedMerchantListResponse getCityCategoryMerchants(String personId, UUID cityId, UUID categoryId)
            throws IOException;

    LoyaltyQrTransactionResponse getVoucherDetail(String personId, UUID voucherId, String voucherType)
            throws IOException;

    LoyaltyMerchantVoucherCategoryResponse getMerchantCampaignVouchers(
            String personId, UUID merchantId, UUID categoryId, int campaignId
    ) throws IOException;

    LoyaltyVoucherTransactedListResponse getVoucherTransactedList(
            String personId, int systemCode, String state
    ) throws IOException;


}
