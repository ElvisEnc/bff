package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;

import java.io.IOException;

public interface ITransferYoloNetProvider {
    TransferResponseMD transferToYolo(Integer personId, Integer accountId, Integer accountNumber, TransferRequest request) throws IOException;
}
