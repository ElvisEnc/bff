package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.TransferResponse;

import java.io.IOException;
import java.util.Map;

public interface ITransferService {
    Pcc01Response makeControl(Pcc01Request request) throws IOException;
    TransferResponse transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    TransferResponse transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
}
