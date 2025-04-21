package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.loyalty.CityCategoryMerchantsAPIRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.MerchantCampaignVoucherAPIRequest;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface ILoyaltyProvider {

    LoyaltySystemCodeServerResponse getSystemCode(String personId, Map<String, String> headers) throws IOException;

    LoyaltySumPointServerResponse getSumPoint(String codeSystem, Map<String, String> headers) throws IOException;

    LoyaltyRegisterSubscriptionResponse registerSubscription(
            LoyaltyRegisterSubscriptionRequest requestServer, Map<String, String> headers) throws IOException;

    LoyaltyRegisterRedeemVoucherResponse registerRedeemVoucher(
            LoyaltyRegisterRedeemVoucherRequest requestServer, Map<String, String> headers) throws IOException;

    LoyaltyGetLevelResponse getLevel(Map<String, String> headers, String personId) throws IOException;

    List<LoyaltyTradeCategoryAPIResponse> getTradeCategories(Map<String, String> headers, String personId)
            throws IOException;

    List<LoyaltyFeaturedMerchantAPIResponse> getFeaturedMerchant(Map<String, String> headers, String personId)
            throws IOException;

    List<LoyaltyCityListAPIResponse> getCityList(Map<String, String> header, String personId) throws IOException;

    List<LoyaltyFeaturedMerchantAPIResponse> getCityCategoryMerchants(
            Map<String, String> headers, CityCategoryMerchantsAPIRequest request
    ) throws IOException;

    LoyaltyQrTransactionAPIResponse getVoucherDetail(
            Map<String, String> headers, UUID voucherId, String voucherType
    ) throws IOException;

    LoyaltyMerchantCampaignVoucherAPIResponse getMerchantCampaignVouchers(
            Map<String, String> headers, MerchantCampaignVoucherAPIRequest request
    ) throws IOException;

    List<LoyaltyRegisterRedeemVoucherResponse> getVoucherTransactedList(
            Map<String, String> headers, String personId, int campaignId, String state
    ) throws IOException;


}
