package bg.com.bo.bff.services.interfaces;

import java.io.IOException;
import java.util.Map;

import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.TransferResponse;

public interface IOwnAccountTransferService {

    TransferResponse transfer(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
}
