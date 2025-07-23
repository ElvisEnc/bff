package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.export.AccountStatementExportRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;

import java.io.IOException;
import java.util.List;

public interface IAccountStatementPdfProvider {
    byte[] generatePdf(List<AccountStatementsResponse> accountReportData, AccountStatementExportRequest request, String accountId) throws IOException;
}
