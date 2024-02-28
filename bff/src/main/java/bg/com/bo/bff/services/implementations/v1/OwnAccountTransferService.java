package bg.com.bo.bff.services.implementations.v1;

import org.springframework.stereotype.Service;

import java.io.IOException;

import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.providers.interfaces.ITransferOwnAccountProvider;
import bg.com.bo.bff.services.interfaces.IOwnAccountTransferService;

@Service
public class OwnAccountTransferService implements IOwnAccountTransferService {

    private final ITransferOwnAccountProvider provider;

    public OwnAccountTransferService(ITransferOwnAccountProvider provider) {
        this.provider = provider;
    }
    @Override
    public TransferResponse transfer(String personId, TransferRequest transferRequest) throws IOException {

        return provider.transfer(personId, transferRequest);
    }
}
