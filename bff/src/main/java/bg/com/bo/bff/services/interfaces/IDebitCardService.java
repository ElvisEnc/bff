package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface IDebitCardService {

    GenericResponse changeAmount(String personId, String cardId, DCLimitsRequest request, Map<String, String> parameter) throws IOException;

    InternetAuthorizationResponse getListAuthorizations(String personId, String cardId, Map<String, String> parameter) throws IOException;
}
