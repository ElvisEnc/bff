package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface IRemittanceService {

    ListGeneralParametersResponse getGeneralParameters(String personId) throws IOException;
}
