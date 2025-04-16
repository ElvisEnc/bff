package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGeneralInformationResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetImageResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetInitialPointsVamosResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatementPointsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyError;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyServices;
import bg.com.bo.bff.providers.models.external.services.HttpClientExternalProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
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
    public LoyaltyPointServerResponse getSumPoint(String codeSystem, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_SUM_POINT.getServiceURL(), codeSystem);
        return this.executeGetRequest(url, headers, LoyaltyPointServerResponse.class);
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

    @Override
    public LoyaltyPointServerResponse getPointsPeriod(Map<String, String> headers, String codeSystem) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_POINTS_PERIOD.getServiceURL(), codeSystem);
        return this.executeGetRequest(url, headers, LoyaltyPointServerResponse.class);
    }

    @Override
    public LoyaltyGetInitialPointsVamosResponse getInitialPointsVAMOS(Map<String, String> headers, String personId) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_INITIAL_POINTS_VAMOS.getServiceURL(), personId);
        return this.executeGetRequest(url, headers, LoyaltyGetInitialPointsVamosResponse.class);
    }

    @Override
    public LoyaltySubscriptionResponse verifySubscription(Map<String, String> headers, String personId) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_CHECK_SUBSCRIPTION.getServiceURL(), personId);
        return this.executeGetRequest(url, headers, LoyaltySubscriptionResponse.class);
    }

    @Override
    public List<LoyaltyStatementPointsResponse> statementPoints(LoyaltyStatementPointRequest requestServer, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_STATEMENT_POINTS.getServiceURL());
        return this.executePostRequest(url, requestServer, headers,
                new TypeReference<List<LoyaltyStatementPointsResponse>>() {});
    }

    @Override
    public LoyaltyGeneralInformationResponse getGeneralInformation(Map<String, String> headers, String personId) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_GENERAL_INFORMATION.getServiceURL(), personId);
        return this.executeGetRequest(url, headers, LoyaltyGeneralInformationResponse.class);
    }

    @Override
    public LoyaltyGetImageResponse getImageInformation(String imageId) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_IMAGE.getServiceURL(), imageId);
        return this.executeGetRequest(url, Collections.emptyMap(), LoyaltyGetImageResponse.class);
    }

    @Override
    public List<LoyaltyGetImageResponse> getImagesInformation(LoyaltyGetImagesRequest requestServer) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_LIST_IMAGES.getServiceURL());
        return this.executePostRequest(url, requestServer, Collections.emptyMap(),
                new TypeReference<List<LoyaltyGetImageResponse>>() {});
    }

}
