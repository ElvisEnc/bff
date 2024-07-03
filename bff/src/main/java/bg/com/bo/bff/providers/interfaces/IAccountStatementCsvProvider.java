package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountReportBasicResponse;

import java.util.List;

public interface IAccountStatementCsvProvider {
    byte[] generateCsv(List<AccountReportBasicResponse.AccountReportData> basicResponseData);
}
