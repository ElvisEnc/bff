package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.providers.interfaces.IDebitCardProvider;
import bg.com.bo.bff.providers.mappings.debit.card.IDebitCardMapper;
import bg.com.bo.bff.services.interfaces.IDebitCardService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DebitCardService implements IDebitCardService {
    private final IDebitCardProvider idcProvider;
    private final IDebitCardMapper idcMapper;

    public DebitCardService(IDebitCardProvider idcProvider, IDebitCardMapper idcMapper) {
        this.idcProvider = idcProvider;
        this.idcMapper = idcMapper;
    }

    @Override
    public GenericResponse changeAmount(String personId, String cardId, DCLimitsRequest request, Map<String, String> parameters) throws IOException {
        DCLimitsMWRequest mwRequest = idcMapper.mapToLimitsRequest(request, personId, cardId);
        return idcProvider.changeAmount(mwRequest, parameters);
    }

    @Override
    public InternetAuthorizationResponse getListAuthorizations(String personId, String cardId, Map<String, String> parameter) throws IOException {
        DCInternetAuthorizationNWResponse result = idcProvider.getListAuthorizations(personId, cardId, parameter);
        return  idcMapper.mapToInternetAuthorizationResponse(result);
    }
}
