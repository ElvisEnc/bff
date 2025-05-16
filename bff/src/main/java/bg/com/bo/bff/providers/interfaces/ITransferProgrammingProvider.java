package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.transfers.programming.DeleteTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponse;

import java.io.IOException;

public interface ITransferProgrammingProvider {

    ProgrammedTransferMDWResponse getProgrammedTransfer(String personId) throws IOException;

    PaymentsPlanMDWResponse getPaymentsPlan(String transferId) throws IOException;

    DeleteTransferMDWResponse deleteTransfer(String personId, String transferId) throws IOException;

}
