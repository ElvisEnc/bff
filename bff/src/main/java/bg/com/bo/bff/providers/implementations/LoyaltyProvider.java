package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyServices;
import bg.com.bo.bff.providers.models.external.services.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class LoyaltyProvider implements ILoyaltyProvider {

    private final HttpClient httpClient;

    @Value("${loyalty.server.url}")
    private String baseUrl;
    @Autowired
    public LoyaltyProvider(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public LoyaltySystemCodeServerResponse getSystemCode(String personId, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_SYSTEM_CODE.getServiceURL(), personId);
        return httpClient.executeGetRequest(url, headers, LoyaltySystemCodeServerResponse.class);
    }

    @Override
    public LoyaltySumPointServerResponse getSumPoint(String codeSystem, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_SUM_POINT.getServiceURL(), codeSystem);
        return httpClient.executeGetRequest(url, headers, LoyaltySumPointServerResponse.class);
    }

    @Override
    public LoyaltyRegisterSubscriptionResponse registerSubscription(LoyaltyRegisterSubscriptionRequest requestServer, Map<String, String> headers) throws IOException {
        String url = baseUrl + LoyaltyServices.POST_REGISTER_SUBSCRIPTION.getServiceURL();
        return httpClient.executePostRequest(url, requestServer, headers, LoyaltyRegisterSubscriptionResponse.class);
    }
}
