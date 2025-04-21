package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchantListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactedListResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public interface ILoyaltyService {

    LoyaltySystemCodeResponse getSystemCode(String personId) throws IOException;

    LoyaltySumPointResponse getSumPoint(String personId, String codeSystem) throws IOException;

    GenericResponse registerSubscription(String personId, String accountId, RegisterSubscriptionRequest request) throws IOException;

    LoyaltyRedeemVoucherResponse registerRedeemVoucher(String personId, String codeSystem, RegisterRedeemVoucherRequest request) throws IOException;

    LoyaltyLevel getLevel(String personId) throws IOException;

    LoyaltyTradeCategoryListResponse getTradeCategories(String personId) throws IOException;

    LoyaltyFeaturedMerchantListResponse getFeaturedMerchants(String personId) throws IOException;

    LoyaltyCityListResponse getCityList(String personId) throws IOException;

    LoyaltyFeaturedMerchantListResponse getCityCategoryMerchants(String personId, UUID cityId, UUID categoryId)
            throws IOException;

    LoyaltyQrTransactionResponse getVoucherDetail(String personId, UUID voucherId, String voucherType)
            throws IOException;

    LoyaltyMerchantVoucherCategoryResponse getMerchantCampaignVouchers(
            String personId, UUID merchantId, UUID categoryId, int campaignId
    ) throws IOException;

    LoyaltyVoucherTransactedListResponse getVoucherTransactedList(
            String personId, int campaigId, String state
    ) throws IOException;


}
