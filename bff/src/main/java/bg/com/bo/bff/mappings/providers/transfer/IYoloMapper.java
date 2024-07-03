package bg.com.bo.bff.mappings.providers.transfer;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferYoloNetRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.ProviderNetResponse;

public interface IYoloMapper {
    TransferYoloNetRequest mapperRequest(Integer personId, Integer accountId, Integer accountNumber, TransferRequest request);

    TransferMWResponse convertResponse(ProviderNetResponse yoloNetResponse);
}
