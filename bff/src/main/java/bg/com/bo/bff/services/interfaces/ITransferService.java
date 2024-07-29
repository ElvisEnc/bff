package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ITransferService {
    TransferResponse transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;

    TransferResponse transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;

    TransferResponse transferWallet(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;

    TransferResponse transferAchAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;

    Pcc01Response makeControl(Pcc01Request request, Map<String, String> parameter) throws IOException;
}
