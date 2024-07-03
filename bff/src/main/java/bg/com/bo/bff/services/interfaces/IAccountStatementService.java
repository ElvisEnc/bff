package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.account.statement.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementExtractResponse;

import java.io.IOException;

public interface IAccountStatementService {
    AccountStatementExtractResponse getAccountStatement(ExtractRequest request, String accountId) throws IOException;
}
