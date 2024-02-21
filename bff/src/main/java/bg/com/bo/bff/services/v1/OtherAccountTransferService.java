package bg.com.bo.bff.services.v1;

import org.springframework.stereotype.Service;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.TransferRequest;
import bg.com.bo.bff.controllers.response.TransferResponse;
import bg.com.bo.bff.provider.interfaces.ITransferOtherAccountProvider;
import bg.com.bo.bff.services.interfaces.IOtherAccountTransferService;
@Service
public class OtherAccountTransferService implements IOtherAccountTransferService {

    private final ITransferOtherAccountProvider provider;

    public OtherAccountTransferService(ITransferOtherAccountProvider provider) {
        this.provider = provider;
    }
    @Override
    public TransferResponse transfer(String personId, TransferRequest transferRequest) throws IOException {

        return provider.transfer(personId, transferRequest);
    }
}
