package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;

import java.util.Map;

public interface ILoyaltyMapper {

    LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response);

    LoyaltySumPointResponse convertResponse(LoyaltySumPointServerResponse response);

    LoyaltyRedeemVoucherResponse convertResponse(LoyaltyRegisterRedeemVoucherResponse response);

    LoyaltyLevel convertResponse(LoyaltyGetLevelResponse response);

    Map<String, String> mapperRequestService(String personId);

    LoyaltyRegisterSubscriptionRequest mapperRequest(String personId, String accountId, RegisterSubscriptionRequest request);

    LoyaltyRegisterRedeemVoucherRequest mapperRequest(String personId, String codeSystem, RegisterRedeemVoucherRequest request);
}
