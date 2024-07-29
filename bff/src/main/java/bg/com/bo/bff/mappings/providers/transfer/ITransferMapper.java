package bg.com.bo.bff.mappings.providers.transfer;

import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferWalletMWResponse;

public interface ITransferMapper {

   TransferResponse convert(TransferWalletMWResponse responseMW);

   TransferResponse convert(TransferMWResponse responseMW);

   TransferResponse convert(TransferAchMwResponse responseMW);

}
