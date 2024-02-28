package bg.com.bo.bff.services.interfaces;

import java.io.IOException;

import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.TransferResponse;

public interface IOtherAccountTransferService {
    TransferResponse transfer(String personId, TransferRequest transferRequest) throws IOException;

}
