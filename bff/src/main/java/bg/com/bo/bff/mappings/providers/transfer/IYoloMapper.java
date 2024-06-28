package bg.com.bo.bff.mappings.providers.transfer;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.providers.dtos.request.TransferYoloNetRequest;
import bg.com.bo.bff.providers.dtos.response.TransferResponseMD;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;

public interface IYoloMapper {
    TransferYoloNetRequest mapperRequest(Integer personId, Integer accountId, Integer accountNumber, TransferRequest request);

    TransferResponseMD convertResponse(ProviderNetResponse yoloNetResponse);
}
