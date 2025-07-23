package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;

import java.util.List;

public interface IAccountStatementCsvProvider {
    byte[] generateCsv(List<AccountStatementsResponse> basicResponseData);
}
