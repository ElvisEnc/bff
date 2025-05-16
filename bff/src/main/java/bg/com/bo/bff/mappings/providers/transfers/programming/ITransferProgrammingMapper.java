package bg.com.bo.bff.mappings.providers.transfers.programming;

import bg.com.bo.bff.application.dtos.response.transfers.programming.DeleteTransferResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.DeleteTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponse;

import java.util.List;

public interface ITransferProgrammingMapper {

    List<ProgrammedTransfersResponse> convertTransferListResponse(ProgrammedTransferMDWResponse mdwResponse);

    List<PaymentsPlanResponse> convertPaymentsPlanResponse(PaymentsPlanMDWResponse mdwResponse);

    DeleteTransferResponse convertDeleteResponse(DeleteTransferMDWResponse mdwResponse);

}
