package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchant;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchantListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategory;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactedListResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.loyalty.ILoyaltyMapper;
import bg.com.bo.bff.providers.dtos.request.loyalty.CityCategoryMerchantsAPIRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.MerchantCampaignVoucherAPIRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyError;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyResponse;
import bg.com.bo.bff.services.interfaces.ILoyaltyService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse.LoyaltyCity;

@Service
public class LoyaltyService implements ILoyaltyService {
    private final ILoyaltyProvider provider;
    private final ILoyaltyMapper mapper;

    public LoyaltyService(ILoyaltyProvider idcProvider, ILoyaltyMapper idcMapper) {
        this.provider = idcProvider;
        this.mapper = idcMapper;
    }


    @Override
    public LoyaltySystemCodeResponse getSystemCode(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltySystemCodeServerResponse responseServer = provider.getSystemCode(personId, headerService);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltySumPointResponse getSumPoint(String personId, String codeSystem) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltySumPointServerResponse responseServer = provider.getSumPoint(codeSystem, headerService);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public GenericResponse registerSubscription(String personId, String accountId, RegisterSubscriptionRequest request) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyRegisterSubscriptionRequest requestServer = mapper.mapperRequest(personId, accountId, request);
        LoyaltyRegisterSubscriptionResponse responseServer = provider.registerSubscription(requestServer, headerService);
        if (responseServer.getCode() == 201) {
            return GenericResponse.instance(LoyaltyResponse.REGISTERED_EXIT);
        }
        throw new GenericException(LoyaltyError.REGISTER_ERROR);
    }

    @Override
    public LoyaltyRedeemVoucherResponse registerRedeemVoucher(String personId, String codeSystem, RegisterRedeemVoucherRequest request) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyRegisterRedeemVoucherRequest requestServer = mapper.mapperRequest(personId, codeSystem, request);
        LoyaltyRegisterRedeemVoucherResponse responseServer = provider.registerRedeemVoucher(requestServer, headerService);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltyLevel getLevel(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyGetLevelResponse responseServer = provider.getLevel(headerService, personId);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltyTradeCategoryListResponse getTradeCategories(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        List<LoyaltyTradeCategory> list = mapper.convertResponseTradeCategory(
                provider.getTradeCategories(headerService, personId)
        );
        return LoyaltyTradeCategoryListResponse.builder()
                .data(list).build();

    }

    @Override
    public LoyaltyFeaturedMerchantListResponse getFeaturedMerchants(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        List<LoyaltyFeaturedMerchant> list = mapper.convertResponseFeaturedMerchant(
                provider.getFeaturedMerchant(headerService, personId)
        );
        return LoyaltyFeaturedMerchantListResponse.builder()
                .data(list).build();
    }

    @Override
    public LoyaltyCityListResponse getCityList(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        List<LoyaltyCity> list = mapper.convertResponseCity(provider.getCityList(headerService, personId));
        return LoyaltyCityListResponse.builder()
                .data(list)
                .build();
    }

    @Override
    public LoyaltyFeaturedMerchantListResponse getCityCategoryMerchants(String personId, UUID cityId, UUID categoryId)
            throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        CityCategoryMerchantsAPIRequest requestServer = mapper.mapperRequest(personId, cityId, categoryId);
        List<LoyaltyFeaturedMerchant> list = mapper.convertResponseFeaturedMerchant(
                provider.getCityCategoryMerchants(headerService, requestServer)
        );
        return LoyaltyFeaturedMerchantListResponse.builder()
                .data(list).build();
    }

    @Override
    public LoyaltyQrTransactionResponse getVoucherDetail(String personId, UUID voucherId, String voucherType)
            throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        return mapper.convertResponseQrTransaction(
                provider.getVoucherDetail(headerService, voucherId, voucherType)
        );
    }

    @Override
    public LoyaltyMerchantVoucherCategoryResponse getMerchantCampaignVouchers(
            String personId, UUID merchantId, UUID categoryId, int campaignId
    ) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        MerchantCampaignVoucherAPIRequest requestService = mapper.mapperRequest(merchantId, categoryId, campaignId);
        return mapper.convertResponse(
                provider.getMerchantCampaignVouchers(headerService, requestService)
        );
    }

    @Override
    public LoyaltyVoucherTransactedListResponse getVoucherTransactedList(
            String personId, int campaignId, String state
    ) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        List<LoyaltyRedeemVoucherResponse> list = mapper.convertVoucherTransactedListResponse(
                provider.getVoucherTransactedList(headerService, personId, campaignId, state)
        );
        return LoyaltyVoucherTransactedListResponse.builder()
                .data(list)
                .build();
    }


}
