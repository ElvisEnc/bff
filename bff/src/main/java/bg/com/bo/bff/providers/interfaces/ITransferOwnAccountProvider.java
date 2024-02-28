package bg.com.bo.bff.providers.interfaces;

import java.io.IOException;

import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.TransferResponse;

public interface ITransferOwnAccountProvider {
    TransferResponse transfer(String personId, TransferRequest request) throws IOException;
}
