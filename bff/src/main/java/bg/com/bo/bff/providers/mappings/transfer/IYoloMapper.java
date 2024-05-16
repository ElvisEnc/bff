package bg.com.bo.bff.providers.mappings.transfer;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.providers.dtos.requests.TransferYoloNetRequest;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import bg.com.bo.bff.providers.dtos.responses.TransferYoloNetResponse;

public interface IYoloMapper {
    TransferYoloNetRequest mapperRequest(Integer personId, Integer accountId, Integer accountNumber, TransferRequest request);

    TransferResponseMD convertResponse(TransferYoloNetResponse yoloNetResponse);
}
