package bg.com.bo.bff.providers.interfaces;
 ;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
 import java.util.Map;

@Service
public interface ILoyaltyProvider {

    LoyaltySystemCodeServerResponse getSystemCode(String personId, Map<String, String> headers) throws IOException;

    LoyaltySumPointServerResponse getSumPoint(String codeSystem, Map<String, String> headers) throws IOException;

    LoyaltyRegisterSubscriptionResponse registerSubscription(LoyaltyRegisterSubscriptionRequest requestServer, Map<String, String> headers) throws IOException;
}
