package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;

import java.io.IOException;

public interface IAccountStatementService {
    ExtractDataResponse getAccountStatement(ExtractRequest request, String accountId) throws IOException;
}
