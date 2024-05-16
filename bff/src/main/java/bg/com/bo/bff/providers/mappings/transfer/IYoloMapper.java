package bg.com.bo.bff.providers.mappings.transfer;

import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import bg.com.bo.bff.providers.dtos.responses.TransferYoloNetResponse;

public interface IYoloMapper {
    TransferResponseMD convert(TransferYoloNetResponse yoloNetResponse);
}
