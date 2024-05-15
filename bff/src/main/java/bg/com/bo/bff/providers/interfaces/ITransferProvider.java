package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;

import java.io.IOException;
import java.util.Map;

public interface ITransferProvider {
    Pcc01Response validateControl(Pcc01Request request) throws IOException;
    TransferResponseMD transferOwnAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameter) throws IOException;
    TransferResponseMD transferThirdAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameter) throws IOException;
}
