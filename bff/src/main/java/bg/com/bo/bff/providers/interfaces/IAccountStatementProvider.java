package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.ExportRequest;
import bg.com.bo.bff.application.dtos.request.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;

import java.io.IOException;

public interface IAccountStatementProvider {

    ClientToken generateToken() throws IOException;

    ExtractDataResponse getAccountStatement(ExtractRequest request, String token, String accountId);

        AccountReportBasicResponse getAccountStatementForExport(ExportRequest request, String accountId, String token);
}
