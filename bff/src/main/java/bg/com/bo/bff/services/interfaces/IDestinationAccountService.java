package bg.com.bo.bff.services.interfaces;


import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface IDestinationAccountService {
    GenericResponse addThirdAccount(String personId, AddThirdAccountRequest addThirdAccountRequest, Map<String, String> parameters) throws IOException;

}
