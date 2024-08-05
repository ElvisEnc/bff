package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.own.account.TransactionLimitsResponse;
import bg.com.bo.bff.application.dtos.response.own.account.OwnAccountsResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IAccountService {
    List<OwnAccountsResponse> getAccounts(String personId, String userDeviceId, Map<String, String> parameters) throws IOException;

    GenericResponse updateTransactionLimit(String personId, String accountId, UpdateTransactionLimitRequest request, Map<String, String> parameter) throws IOException;

    TransactionLimitsResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException;
}
