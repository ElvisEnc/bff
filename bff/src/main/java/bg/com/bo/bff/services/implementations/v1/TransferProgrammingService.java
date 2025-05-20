package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.transfers.programming.SaveTransferRequest;
import bg.com.bo.bff.application.dtos.response.transfers.programming.DeleteTransferResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.SaveProgrammedTransferResponse;
import bg.com.bo.bff.mappings.providers.transfers.programming.ITransferProgrammingMapper;
import bg.com.bo.bff.mappings.providers.transfers.programming.TransferProgrammingMapper;
import bg.com.bo.bff.providers.dtos.request.transfers.programming.SaveTransferMDWRequest;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.DeleteTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.SaveTransferMDWResponse;
import bg.com.bo.bff.providers.interfaces.ITransferProgrammingProvider;
import bg.com.bo.bff.services.interfaces.ITransferProgrammingService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TransferProgrammingService implements ITransferProgrammingService {
    private final ITransferProgrammingProvider provider;

    private final ITransferProgrammingMapper mapper;

    public TransferProgrammingService(ITransferProgrammingProvider provider, TransferProgrammingMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public List<ProgrammedTransfersResponse> getTransfers(String personId) throws IOException {
        ProgrammedTransferMDWResponse response = provider.getProgrammedTransfer(personId);
        return mapper.convertTransferListResponse(response);
    }

    @Override
    public List<PaymentsPlanResponse> getPaymentsPlan(String transferId) throws IOException {
        PaymentsPlanMDWResponse response = provider.getPaymentsPlan(transferId);
        return mapper.convertPaymentsPlanResponse(response);
    }

    @Override
    public DeleteTransferResponse deleteTransfer(String personId, String transferId) throws IOException {
        DeleteTransferMDWResponse response = provider.deleteTransfer(personId, transferId);
        return mapper.convertDeleteResponse(response);
    }

    @Override
    public SaveProgrammedTransferResponse saveTransfer(SaveTransferRequest request, String personId) throws IOException {
        SaveTransferMDWRequest mdwRequest = mapper.convertSaveRequest(request, personId);
        SaveTransferMDWResponse mdwResponse = provider.saveTransfer(mdwRequest);
        return mapper.convertSaveResponse(mdwResponse);
    }
}
