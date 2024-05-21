package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.interfaces.ITransferYoloNetProvider;
import bg.com.bo.bff.services.interfaces.ITransferService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TransferService implements ITransferService {
    private final ITransferProvider transferProvider;
    private final ITransferACHProvider transferACHProvider;
    private final ITransferYoloNetProvider transferYoloNetProvider;

    public TransferService(ITransferProvider transferProvider, ITransferACHProvider transferACHProvider, ITransferYoloNetProvider transferYoloNetProvider) {
        this.transferProvider = transferProvider;
        this.transferACHProvider = transferACHProvider;
        this.transferYoloNetProvider = transferYoloNetProvider;
    }


    @Override
    public TransferResponseMD transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        return transferProvider.transferOwnAccount(personId, accountId, transferRequest, parameter);
    }

    @Override
    public TransferResponseMD transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        return transferProvider.transferThirdAccount(personId, accountId, transferRequest, parameter);
    }

    @Override
    public TransferResponseMD transferWallet(Integer personId, Integer accountId, Integer accountNumber, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        return transferYoloNetProvider.transferToYolo(personId, accountId, accountNumber, transferRequest);
    }

    @Override
    public TransferResponseMD transferAchAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        return transferACHProvider.transferAchAccount(personId, accountId, transferRequest, parameter);
    }

    @Override
    public Pcc01Response makeControl(Pcc01Request request) throws IOException {
        return transferProvider.validateControl(request);
    }
}
