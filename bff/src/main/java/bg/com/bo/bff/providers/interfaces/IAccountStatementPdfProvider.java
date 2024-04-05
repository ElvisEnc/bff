package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.ExportRequest;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;

import java.io.IOException;
import java.util.List;

public interface IAccountStatementPdfProvider {
    byte[] generatePdf(List<AccountReportBasicResponse.AccountReportData> accountReportData, ExportRequest request, String accountId) throws IOException;
}
