package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.account.statement.ExportRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementExportResponse;

import java.io.IOException;

public interface IExportService {
    AccountStatementExportResponse generateReport(ExportRequest request, String account) throws IOException;
}
