package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.*;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyPersonCampRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetCategoryPromotionResponse;

import java.util.List;
import java.util.Map;

public interface ILoyaltyMapper {

    LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response);

    LoyaltyPointResponse convertResponse(LoyaltyPointServerResponse response);

    LoyaltyRedeemVoucherResponse convertResponse(LoyaltyRegisterRedeemVoucherResponse response);

    LoyaltyLevelResponse convertResponse(LoyaltyGetLevelResponse response);

    LoyaltyInitialPointsResponse convertResponse(LoyaltyGetInitialPointsVamosResponse response);

    List<LoyaltyStatementResponse> convertResponse(List<LoyaltyStatementPointsResponse> response);

    LoyaltyGeneralInfoResponse convertResponse(LoyaltyGeneralInformationResponse response);

    LoyaltyImageResponse convertResponse(LoyaltyGetImageResponse response);

    List<LoyaltyLevelResponse> convertLevels(List<LoyaltyGetLevelResponse> levels);

    List<LoyaltyImageResponse> convertResponseImage(List<LoyaltyGetImageResponse> response);

    List<bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse> convertResponseCategoryProm(List<LoyaltyGetCategoryPromotionResponse> response);

    LoyaltyTermsConditionsResponse convertResponse(LoyaltyGetTermsConditionsResponse response);

    LoyaltyPromotionResponse convertResponse(LoyaltyGetPromotionResponse response);

    Map<String, String> mapperRequestService(String personId);

    LoyaltyRegisterSubscriptionRequest mapperRequest(String personId, String accountId, RegisterSubscriptionRequest request);

    LoyaltyRegisterRedeemVoucherRequest mapperRequest(String personId, String codeSystem, RegisterRedeemVoucherRequest request);

    LoyaltyStatementPointRequest mapperRequest(String personId, String codeSystem, LoyaltyStatementRequest request);

    LoyaltyGetImagesRequest mapperRequest(LoyaltyImageRequest request);

    LoyaltyPersonCampRequest mapperRequest(String personId);
}
