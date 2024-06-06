package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;

import java.io.IOException;
import java.util.Map;

public interface IDebitCardProvider {
    GenericResponse changeAmount(DCLimitsMWRequest request, Map<String, String> parameters) throws IOException;
}
