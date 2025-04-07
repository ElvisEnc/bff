package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.loyalty.ILoyaltyMapper;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
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
        Map<String, String> requestService = mapper.mapperRequestGet(personId);
        LoyaltySystemCodeServerResponse responseServer = provider.getSystemCode(personId, requestService);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public LoyaltySumPointResponse getSumPoint(String personId, String codeSystem) throws IOException {
        Map<String, String> requestService = mapper.mapperRequestGet(personId);
        LoyaltySumPointServerResponse responseServer = provider.getSumPoint(codeSystem, requestService);
        return mapper.convertResponse(responseServer);
    }

    @Override
    public GenericResponse registerSubscription(String personId, String accountId, RegisterSubscriptionRequest request) throws IOException {
        Map<String, String> requestService = mapper.mapperRequestPost(personId);
        LoyaltyRegisterSubscriptionRequest requestServer = mapper.mapperRequest(personId, accountId, request);
        LoyaltyRegisterSubscriptionResponse responseServer = provider.registerSubscription(requestServer, requestService);

        if (responseServer.getCode() == 201) {
            return GenericResponse.instance(LoyaltyResponse.REGISTERED_EXIT);
        } else if (responseServer.getCode() == 400) {
            return GenericResponse.instance(LoyaltyResponse.REGISTRATION_EXISTS);
        }
        throw new GenericException(LoyaltyError.REGISTER_ERROR);
    }
}
