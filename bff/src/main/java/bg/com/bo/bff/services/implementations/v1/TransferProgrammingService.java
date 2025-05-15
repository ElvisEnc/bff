package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.mappings.providers.transfers.programming.TransferProgrammingMapper;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponse;
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

    private final TransferProgrammingMapper mapper;

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
}
