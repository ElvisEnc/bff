package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.MoneyOrderSentResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface IRemittanceService {

    ListGeneralParametersResponse getGeneralParameters(String personId) throws IOException;

    GenericResponse validateAccount(String personId, String accountId) throws IOException;

    List<MoneyOrderSentResponse> getMoneyOrdersSent(String personId) throws IOException;

}
