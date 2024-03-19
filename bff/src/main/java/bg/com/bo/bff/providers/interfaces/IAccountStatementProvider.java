package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;
import bg.com.bo.bff.models.ClientToken;

import java.io.IOException;

public interface IAccountStatementProvider {

    ClientToken generateToken() throws IOException;

    ExtractDataResponse getAccountStatement(ExtractRequest request, String token, String accountId);
}
