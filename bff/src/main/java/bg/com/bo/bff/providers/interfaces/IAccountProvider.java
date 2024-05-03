package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.dtos.TransactionLimitListMWResponse;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.models.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.providers.dtos.requests.UpdateTransactionLimitMWRequest;

import java.io.IOException;
import java.util.Map;

public interface IAccountProvider {
    ClientMWToken generateAccountAccessToken() throws IOException;

    AccountListResponse getAccounts(String token, String personId, String documentNumber) throws IOException;

    GenericResponse updateTransactionLimit(String personId, String accountId, UpdateTransactionLimitMWRequest request, Map<String, String> parameter) throws IOException;

    TransactionLimitListMWResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException;
}
