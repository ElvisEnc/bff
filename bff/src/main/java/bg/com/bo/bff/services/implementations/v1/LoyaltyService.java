package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.loyalty.ILoyaltyMapper;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyError;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyResponse;
import bg.com.bo.bff.services.interfaces.ILoyaltyService;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

}
