package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.transfers.programming.SaveTransferRequest;
import bg.com.bo.bff.application.dtos.response.transfers.programming.DeleteTransferResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.SaveProgrammedTransferResponse;

import java.io.IOException;
import java.util.List;

public interface ITransferProgrammingService {

    List<ProgrammedTransfersResponse> getTransfers(String personId) throws IOException;

    List<PaymentsPlanResponse> getPaymentsPlan(String transferId) throws IOException;

    DeleteTransferResponse deleteTransfer(String personId, String transferId) throws IOException;

    SaveProgrammedTransferResponse saveTransfer(SaveTransferRequest request, String personId) throws IOException;

}
