package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyError;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyServices;
import bg.com.bo.bff.providers.models.external.services.HttpClientExternalProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class LoyaltyProvider extends HttpClientExternalProvider<LoyaltyError> implements ILoyaltyProvider {

    @Value("${loyalty.server.url}")
    private String baseUrl;

    public LoyaltyProvider(IHttpClientFactory httpClientFactory, ObjectMapper objectMapper) {
        super(httpClientFactory, objectMapper, LoyaltyError.class);
    }


    @Override
    public LoyaltySystemCodeServerResponse getSystemCode(String personId, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_SYSTEM_CODE.getServiceURL(), personId);
        return this.executeGetRequest(url, headers, LoyaltySystemCodeServerResponse.class);
    }

    @Override
    public LoyaltySumPointServerResponse getSumPoint(String codeSystem, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_SUM_POINT.getServiceURL(), codeSystem);
        return this.executeGetRequest(url, headers, LoyaltySumPointServerResponse.class);
    }

    @Override
    public LoyaltyRegisterSubscriptionResponse registerSubscription(LoyaltyRegisterSubscriptionRequest requestServer, Map<String, String> headers) throws IOException {
        String url = baseUrl + LoyaltyServices.POST_REGISTER_SUBSCRIPTION.getServiceURL();
        return this.executePostRequest(url, requestServer, headers, LoyaltyRegisterSubscriptionResponse.class);
    }

    @Override
    public LoyaltyRegisterRedeemVoucherResponse registerRedeemVoucher(LoyaltyRegisterRedeemVoucherRequest requestServer, Map<String, String> headers) throws IOException {
        String url = baseUrl + LoyaltyServices.POST_REGISTER_REDEEM_VOUCHER.getServiceURL();
        return this.executePostRequest(url, requestServer, headers, LoyaltyRegisterRedeemVoucherResponse.class);
    }

    @Override
    public LoyaltyGetLevelResponse getLevel(Map<String, String> headers, String personId) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_LEVEL.getServiceURL(), personId);
        return this.executeGetRequest(url, headers, LoyaltyGetLevelResponse.class);
    }

}
