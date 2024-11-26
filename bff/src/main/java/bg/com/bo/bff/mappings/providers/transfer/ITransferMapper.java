package bg.com.bo.bff.mappings.providers.transfer;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.providers.dtos.request.transfer.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferWalletMWResponse;

public interface ITransferMapper {

   Pcc01MWRequest mapperRequest(String personId, String accountId, Pcc01Request request);

   TransferResponse convert(TransferWalletMWResponse responseMW);

   TransferResponse convert(TransferMWResponse responseMW);

   TransferResponse convert(TransferAchMwResponse responseMW);

}
