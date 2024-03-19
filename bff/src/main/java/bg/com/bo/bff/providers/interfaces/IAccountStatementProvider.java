package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.ExtractRequest;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.requests.AccountReportBasicRequest;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;

import java.io.IOException;

public interface IAccountStatementProvider {

    ClientToken generateToken() throws IOException;

    AccountReportBasicResponse getAccountStatement(AccountReportBasicRequest request, String token);
}
