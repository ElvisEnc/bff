package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;

import java.util.Map;

public interface ILoyaltyMapper {

    LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response);

    LoyaltySumPointResponse convertResponse(LoyaltySumPointServerResponse response);

    Map<String, String> mapperRequestService(String personId);

    LoyaltyRegisterSubscriptionRequest mapperRequest(String personId, String accountId, RegisterSubscriptionRequest request);
}
