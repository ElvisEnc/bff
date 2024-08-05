package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;

import java.util.List;

public interface IAccountStatementCsvProvider {
    byte[] generateCsv(List<AccountStatementsMWResponse.AccountStatementMW> basicResponseData);
}
