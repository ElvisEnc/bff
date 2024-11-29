package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IAccountStatementService {

    List<AccountStatementsResponse> getAccountStatement(String accountId, String personId, AccountStatementsRequest request, Map<String, String> parameter) throws IOException;
}
