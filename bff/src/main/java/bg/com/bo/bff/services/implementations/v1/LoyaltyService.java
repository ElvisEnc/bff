package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGeneralInfoResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyImageResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyInitialPointsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevelResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStatementResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStoreFeaturedResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTermsConditionsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherQrTransactionsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactionsResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyPersonCampRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.loyalty.ILoyaltyMapper;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGeneralInformationResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetCategoryPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetImageResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetInitialPointsVamosResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetQrTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetStoreFeaturedResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTermsConditionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPostRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatementPointsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatusResponse;
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
    public LoyaltyPointResponse getSumPoint(String personId, String codeSystem) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyPointServerResponse responseServer = provider.getSumPoint(codeSystem, headerService);
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
        LoyaltyPostRegisterRedeemVoucherResponse responseServer = provider.registerRedeemVoucher(requestServer, headerService);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltyLevelResponse getLevel(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyGetLevelResponse responseServer = provider.getLevel(headerService, personId);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltyPointResponse getPointsPeriod(String personId, String accountId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyPointServerResponse responseServer = provider.getPointsPeriod(headerService, accountId);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltyInitialPointsResponse getInitialPointsVAMOS(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyGetInitialPointsVamosResponse responseServer = provider.getInitialPointsVAMOS(headerService, personId);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public GenericResponse verifySubscription(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyStatusResponse responseServer = provider.verifySubscription(headerService, personId);
        if (responseServer.isStatus()) {
            return GenericResponse.instance(LoyaltyResponse.SUBSCRIPTION_EXISTS);
        }
        throw new GenericException(LoyaltyError.NOT_SUBSCRIPTION);
    }

    @Override
    public List<LoyaltyStatementResponse> statementPoints(String personId, String codeSystem, LoyaltyStatementRequest request) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyStatementPointRequest requestServer = mapper.mapperRequest(personId, codeSystem, request);
        List<LoyaltyStatementPointsResponse> responseServer = provider.statementPoints(requestServer, headerService);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltyGeneralInfoResponse getGeneralInformation(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyGeneralInformationResponse responseServer = provider.getGeneralInformation(headerService,personId);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltyImageResponse getImageInformation(String imageId) throws IOException {
        LoyaltyGetImageResponse responseServer = provider.getImageInformation(imageId);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public List<LoyaltyImageResponse> getImagesInformation(LoyaltyImageRequest request) throws IOException {
        LoyaltyGetImagesRequest requestServer = mapper.mapperRequest(request);
        List<LoyaltyGetImageResponse> responseServer = provider.getImagesInformation(requestServer);
        return mapper.convertResponseImage(responseServer);
    }

    @Override
    public List<LoyaltyCategoryPromotionResponse> getCategoryPromotions(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        List<LoyaltyGetCategoryPromotionResponse> responseServer = provider.getCategoryPromotions(headerService);
        return mapper.convertResponseCategoryProm(responseServer);
    }

    @Override
    public List<LoyaltyLevelResponse> getCategoryPointsLevels(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        List<LoyaltyGetLevelResponse> responseServer = provider.getCategoryPointsLevels(headerService);
        return mapper.convertLevels(responseServer);
    }

    @Override
    public LoyaltyTermsConditionsResponse termsConditions(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyPersonCampRequest requestServer = mapper.mapperRequest(personId);
        LoyaltyGetTermsConditionsResponse responseServer = provider.termsConditions(requestServer, headerService);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public GenericResponse checkFlow(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyStatusResponse responseServer = provider.checkFlow(headerService, personId);
        if (responseServer.isStatus()) {
            return GenericResponse.instance(LoyaltyResponse.VALIDATE_PROGRAM);
        }
        throw new GenericException(LoyaltyError.NOT_VALIDATE_PROGRAM);
    }

    @Override
    public LoyaltyPromotionResponse getPromotions(String personId, String promotionId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyGetPromotionResponse responseServer = provider.getPromotions(headerService, promotionId);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public List<LoyaltyStoreFeaturedResponse> getStoreFeatured(String personId) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        List<LoyaltyGetStoreFeaturedResponse> responseServer = provider.getStoreFeatured(headerService);
        return mapper.convertStoreFeatured(responseServer);
    }

    @Override
    public LoyaltyVoucherQrTransactionsResponse getQRTransactions(String personId, String voucherId, String typeVoucher) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        LoyaltyGetQrTransactionsResponse responseServer = provider.getQRTransactions(headerService, voucherId, typeVoucher);
        return mapper.convertVoucherQrTransaction(responseServer);
    }

    @Override
    public List<LoyaltyVoucherTransactionsResponse> getVoucherTransactions(String personId, String codeSystem, String status) throws IOException {
        Map<String, String> headerService = mapper.mapperRequestService(personId);
        List<LoyaltyGetTransactionsResponse> responseServer = provider.getVoucherTransactions(headerService, codeSystem, status);
        return mapper.convertVoucherTransaction(responseServer);
    }

}
