package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;

import java.util.List;

public interface IAccountStatementCsvProvider {
    byte[] generateCsv(List<AccountReportBasicResponse.AccountReportData> basicResponseData);
}
