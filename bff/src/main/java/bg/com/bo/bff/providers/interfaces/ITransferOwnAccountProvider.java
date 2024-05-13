package bg.com.bo.bff.providers.interfaces;

import java.io.IOException;
import java.util.Map;

import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;

public interface ITransferOwnAccountProvider {
    TransferResponseMD transfer(String personId, String accountId, TransferRequest request, Map<String, String>parameter) throws IOException;
}
