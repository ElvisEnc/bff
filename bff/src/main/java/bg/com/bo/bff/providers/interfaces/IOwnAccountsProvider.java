package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.*;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;

import java.io.IOException;
import java.util.Map;

public interface IOwnAccountsProvider {
    OwnAccountsListMWResponse getAccounts(String personId, String userDeviceId, Map<String, String> parameter) throws IOException;

    GenericResponse updateTransactionLimit(String personId, String accountId, UpdateTransactionLimitMWRequest request, Map<String, String> parameter) throws IOException;

    TransactionLimitsMWResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException;
}
