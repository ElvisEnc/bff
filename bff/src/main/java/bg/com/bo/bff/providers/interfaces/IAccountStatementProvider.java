package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;

import java.io.IOException;
import java.util.Map;

public interface IAccountStatementProvider {

    AccountStatementsMWResponse getAccountStatements(AccountStatementsMWRequest request, Map<String, String> parameter) throws IOException;
}
