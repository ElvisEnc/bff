package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.GetTransactionLimitResponse;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;

import java.io.IOException;
import java.util.Map;

public interface IAccountService {
    AccountListResponse getAccounts(String personId, String documentNumber) throws IOException;

    GenericResponse updateTransactionLimit(String personId, String accountId, UpdateTransactionLimitRequest request, Map<String, String> parameter) throws IOException;

    GetTransactionLimitResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException;
}
