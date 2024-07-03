package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ITransferProvider {
    TransferMWResponse transferOwnAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameter) throws IOException;

    TransferMWResponse transferThirdAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameter) throws IOException;

    Pcc01Response validateControl(Pcc01Request request) throws IOException;
}
