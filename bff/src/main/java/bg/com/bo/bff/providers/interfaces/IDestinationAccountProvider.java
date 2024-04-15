package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.dtos.requests.AddThirdAccountBasicRequest;

import java.io.IOException;
import java.util.Map;

public interface IDestinationAccountProvider {
    GenericResponse addThirdAccount(String token, AddThirdAccountBasicRequest request, Map<String, String> headers) throws IOException;
}
