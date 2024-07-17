package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.TransactionLimitListMWResponse;
import bg.com.bo.bff.application.dtos.response.own.account.AccountListResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.ClientMWToken;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;

import java.io.IOException;
import java.util.Map;

public interface IAccountProvider {
    ClientMWToken generateAccountAccessToken() throws IOException;

    AccountListResponse getAccounts(String token, String personId, Map<String, String> parameter) throws IOException;

    GenericResponse updateTransactionLimit(String personId, String accountId, UpdateTransactionLimitMWRequest request, Map<String, String> parameter) throws IOException;

    TransactionLimitListMWResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException;
}
