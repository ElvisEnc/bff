package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;

import java.io.IOException;
import java.util.Map;

public interface ITransferService {
    TransferResponseMD transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    TransferResponseMD transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    TransferResponseMD transferWallet(Integer personId, Integer accountId, Integer accountNumber, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    TransferResponseMD transferAchAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException;
    Pcc01Response makeControl(Pcc01Request request) throws IOException;
}
