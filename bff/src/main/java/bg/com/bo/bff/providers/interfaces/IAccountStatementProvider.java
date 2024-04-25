package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.export.account.statement.ExportRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractRequest;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;

import java.io.IOException;

public interface IAccountStatementProvider {
    ClientToken generateToken() throws IOException;

    AccountReportBasicResponse getAccountStatement(ExtractRequest request, String token, String accountId, String extractId, Boolean clearCache);

    AccountReportBasicResponse getAccountStatementForExport(ExportRequest request, String accountId, String token);
}
