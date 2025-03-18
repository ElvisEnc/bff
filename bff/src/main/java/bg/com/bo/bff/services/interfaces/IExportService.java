package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.export.AccountStatementExportRequest;
import bg.com.bo.bff.application.dtos.response.export.AccountStatementExportResponse;

import java.io.IOException;
import java.util.Map;

public interface IExportService {
    AccountStatementExportResponse generateReport(AccountStatementExportRequest request, String accountId, String personId, Map<String, String> parameter) throws IOException;
}
