package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;

import java.io.IOException;

public interface ITransferYoloNetProvider {
    TransferMWResponse transferToYolo(Integer personId, Integer accountId, Integer accountNumber, TransferRequest request) throws IOException;
}
