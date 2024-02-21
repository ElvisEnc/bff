package bg.com.bo.bff.provider.interfaces;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.TransferRequest;
import bg.com.bo.bff.controllers.response.TransferResponse;

public interface ITransferOwnAccountProvider {
    TransferResponse transfer(String personId, TransferRequest request) throws IOException;
}
