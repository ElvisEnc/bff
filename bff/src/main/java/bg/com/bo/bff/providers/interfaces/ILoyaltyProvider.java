package bg.com.bo.bff.providers.interfaces;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyPersonCampRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface ILoyaltyProvider {

    LoyaltySystemCodeServerResponse getSystemCode(String personId, Map<String, String> headers) throws IOException;

    LoyaltyPointServerResponse getSumPoint(String codeSystem, Map<String, String> headers) throws IOException;

    LoyaltyRegisterSubscriptionResponse registerSubscription(LoyaltyRegisterSubscriptionRequest requestServer, Map<String, String> headers) throws IOException;

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

    LoyaltyGetQrTransactionsResponse getQRTransactions(Map<String, String> headers, String voucherId, String typeVoucher) throws IOException;

    List<LoyaltyGetTransactionsResponse> getVoucherTransactions(Map<String, String> headers, String codeSystem, String status) throws IOException;
}
