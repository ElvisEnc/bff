package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.providers.dtos.request.loyalty.MerchantCampaignVoucherAPIRequest;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyPersonCampRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.CityCategoryMerchantsAPIRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGeneralInformationResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetCategoryPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetImageResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetInitialPointsVamosResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetGenericTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetStoreFeaturedResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTermsConditionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPostRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatementPointsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatusResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyCityListAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyFeaturedMerchantAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyMerchantCampaignVoucherAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyQrTransactionAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyTradeCategoryAPIResponse;
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
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

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
    public LoyaltyPostRegisterRedeemVoucherResponse registerRedeemVoucher(LoyaltyRegisterRedeemVoucherRequest requestServer, Map<String, String> headers) throws IOException {
        String url = baseUrl + LoyaltyServices.POST_REGISTER_REDEEM_VOUCHER.getServiceURL();
        return this.executePostRequest(url, requestServer, headers, LoyaltyPostRegisterRedeemVoucherResponse.class);
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
    public LoyaltyStatusResponse verifySubscription(Map<String, String> headers, String personId) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_CHECK_SUBSCRIPTION.getServiceURL(), personId);
        return this.executeGetRequest(url, headers, LoyaltyStatusResponse.class);
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

    @Override
    public List<LoyaltyGetCategoryPromotionResponse> getCategoryPromotions(Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_CATEGORY_PROMOTION.getServiceURL());
        return this.executeGetRequest(url, headers,
                new TypeReference<List<LoyaltyGetCategoryPromotionResponse>>() {});
    }

    @Override
    public List<LoyaltyGetLevelResponse> getCategoryPointsLevels(Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_CATEGORY_POINTS_LEVEL.getServiceURL());
        return this.executeGetRequest(url, headers,
                new TypeReference<List<LoyaltyGetLevelResponse>>() {});
    }

    @Override
    public LoyaltyGetTermsConditionsResponse termsConditions(LoyaltyPersonCampRequest requestServer, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_TERMS_CONDITIONS.getServiceURL());
        return this.executePostRequest(url, requestServer, headers, LoyaltyGetTermsConditionsResponse.class);
    }

    @Override
    public LoyaltyStatusResponse checkFlow(Map<String, String> headers, String personId) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.VALIDATE_PROGRAM.getServiceURL(), personId);
        return this.executeGetRequest(url, headers, LoyaltyStatusResponse.class);
    }

    @Override
    public LoyaltyGetPromotionResponse getPromotions(Map<String, String> headers, String promotionId) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_PROMOTION.getServiceURL(), promotionId);
        return this.executeGetRequest(url, headers, LoyaltyGetPromotionResponse.class);
    }

    @Override
    public List<LoyaltyGetStoreFeaturedResponse> getStoreFeatured(Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_STORE_FEATURED.getServiceURL());
        return this.executeGetRequest(url, headers,
                new TypeReference<List<LoyaltyGetStoreFeaturedResponse>>() {});
    }

    @Override
    public LoyaltyGetGenericTransactionsResponse getQRTransactions(Map<String, String> headers, String voucherId, String typeVoucher) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_QR_TRANSACTION.getServiceURL(), voucherId, typeVoucher);
        return this.executeGetRequest(url, headers, LoyaltyGetGenericTransactionsResponse.class);
    }

    @Override
    public List<LoyaltyGetTransactionsResponse> getVoucherTransactions(Map<String, String> headers, String codeSystem, String status) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_VOUCHER_TRANSACTION.getServiceURL(), codeSystem, status);
        return this.executeGetRequest(url, headers,
                new TypeReference<List<LoyaltyGetTransactionsResponse>>() {});
    }

    @Override
    public List<LoyaltyTradeCategoryAPIResponse> getTradeCategories(Map<String, String> headers, String personId)
            throws IOException {
        String url = baseUrl + LoyaltyServices.GET_TRADE_CATEGORIES.getServiceURL();
        return Arrays.asList(this.executeGetRequest(url, headers, LoyaltyTradeCategoryAPIResponse[].class));
    }

    @Override
    public List<LoyaltyFeaturedMerchantAPIResponse> getFeaturedMerchant(Map<String, String> headers, String personId)
            throws IOException {
        String url = baseUrl + LoyaltyServices.GET_FEATURED_MERCHANTS.getServiceURL();
        return Arrays.asList(this.executeGetRequest(url, headers, LoyaltyFeaturedMerchantAPIResponse[].class));
    }

    @Override
    public List<LoyaltyCityListAPIResponse> getCityList(Map<String, String> headers, String personId) throws IOException {
        String url = baseUrl + LoyaltyServices.GET_CITIES.getServiceURL();
        return Arrays.asList(this.executeGetRequest(url, headers, LoyaltyCityListAPIResponse[].class));
    }

    @Override
    public List<LoyaltyFeaturedMerchantAPIResponse> getCityCategoryMerchants(
            Map<String, String> headers, CityCategoryMerchantsAPIRequest request
    ) throws IOException {
        String url = baseUrl + LoyaltyServices.GET_CITY_CATEGORY_MERCHANTILS.getServiceURL();
        return Arrays.asList(this.executePostRequest(url, request, headers, LoyaltyFeaturedMerchantAPIResponse[].class));
    }

    @Override
    public LoyaltyQrTransactionAPIResponse getVoucherDetail(
            Map<String, String> headers, UUID voucherId, String voucherType
    ) throws IOException {
        String url = baseUrl + String.format(
                LoyaltyServices.GET_QR_VOUCHER_TRANSACTION.getServiceURL(), voucherId, voucherType
        );
        return this.executeGetRequest(url, headers, LoyaltyQrTransactionAPIResponse.class);
    }

    @Override
    public LoyaltyMerchantCampaignVoucherAPIResponse getMerchantCampaignVouchers(
            Map<String, String> headers, MerchantCampaignVoucherAPIRequest request
    ) throws IOException {
        String url = baseUrl + String.format(
                LoyaltyServices.POST_COMPANY_MERCHANT_CATEGORY_VOUCHERS.getServiceURL()
        );
        return this.executePostRequest(url, request, headers, LoyaltyMerchantCampaignVoucherAPIResponse.class);
    }

    @Override
    public List<LoyaltyPostRegisterRedeemVoucherResponse> getVoucherTransactedList(
            Map<String, String> headers, String personId, int campaignId, String state
    ) throws IOException {
        String url = baseUrl + String.format(
                LoyaltyServices.GET_VOUCHER_TRANSACTED_LIST.getServiceURL(),campaignId, personId, state
        );
        return Arrays.asList(this.executeGetRequest(url, headers, LoyaltyPostRegisterRedeemVoucherResponse[].class));
    }


}
