package bg.com.bo.bff.services.interfaces;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.TransferRequest;
import bg.com.bo.bff.controllers.response.TransferResponse;

public interface IOwnAccountTransferService {

    TransferResponse transfer(String personId, TransferRequest transferRequest) throws IOException;
}
