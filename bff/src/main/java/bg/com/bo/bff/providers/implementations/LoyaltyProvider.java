package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.providers.dtos.request.loyalty.MerchantCampaignVoucherAPIRequest;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.CityCategoryMerchantsAPIRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyCityListAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyFeaturedMerchantAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyMerchantCampaignVoucherAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyQrTransactionAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyTradeCategoryAPIResponse;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyError;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyServices;
import bg.com.bo.bff.providers.models.external.services.HttpClientExternalProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
    public List<LoyaltyRegisterRedeemVoucherResponse> getVoucherTrasanctedList(
            Map<String, String> headers, String personId, int campaignId, String state
    ) throws IOException {
        String url = baseUrl + String.format(
                LoyaltyServices.GET_VOUCHER_TRANSACTED_LIST.getServiceURL(),campaignId, personId, state
        );
        return Arrays.asList(this.executeGetRequest(url, headers, LoyaltyRegisterRedeemVoucherResponse[].class));
    }


}
