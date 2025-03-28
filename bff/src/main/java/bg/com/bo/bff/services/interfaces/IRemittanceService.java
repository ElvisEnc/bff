package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.remittance.ConsultWURemittanceRequest;
import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceRequest;
import bg.com.bo.bff.application.dtos.request.remittance.UpdateWURemittanceRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.remittance.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface IRemittanceService {

    ListGeneralParametersResponse getGeneralParameters(String personId) throws IOException;

    GenericResponse validateAccount(String personId, String accountId) throws IOException;

    List<MoneyOrderSentResponse> getMoneyOrdersSent(String personId) throws IOException;

    List<CheckRemittanceResponse> checkRemittance(String personId, String remittanceId) throws IOException;

    List<DepositRemittanceResponse> depositRemittance(String personId, String remittanceId, DepositRemittanceRequest request) throws IOException;

    List<CheckRemittanceResponse> consultWURemittance(String personId, String remittanceId, ConsultWURemittanceRequest body) throws IOException;

    UpdateWURemittanceResponse updateWURemittance(String personId, String consultId, UpdateWURemittanceRequest request) throws IOException;

}
