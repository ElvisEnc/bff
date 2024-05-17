package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.TransferResponse;

import java.io.IOException;
import java.util.Map;

public interface ITransferService {
    TransferResponse transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    TransferResponse transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    TransferResponse transferWallet(Integer personId, Integer accountId, Integer accountNumber, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    TransferResponse transferAchAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    Pcc01Response makeControl(Pcc01Request request) throws IOException;
}
