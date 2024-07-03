package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ITransferService {
    TransferMWResponse transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;

    TransferMWResponse transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;

    TransferMWResponse transferWallet(Integer personId, Integer accountId, Integer accountNumber, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;

    TransferMWResponse transferAchAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;

    Pcc01Response makeControl(Pcc01Request request) throws IOException;
}
