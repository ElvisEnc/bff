package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import bg.com.bo.bff.providers.implementations.GenerateImageProvider;
import bg.com.bo.bff.providers.interfaces.IGenerateImage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.providers.interfaces.ITransferOwnAccountProvider;
import bg.com.bo.bff.services.interfaces.IOwnAccountTransferService;

@Service
public class OwnAccountTransferService implements IOwnAccountTransferService {

    private final ITransferOwnAccountProvider provider;
    private final IGenerateImage generateImageProvider;

    public OwnAccountTransferService(ITransferOwnAccountProvider provider, IGenerateImage generateImageProvider) {
        this.provider = provider;
        this.generateImageProvider = generateImageProvider;
    }

    @Override
    public TransferResponse transfer(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferResponseMD response = provider.transfer(personId, accountId, transferRequest, parameter);
        String newImage = generateImageProvider.generateImage(response);
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setData(newImage);
        transferResponse.setFormat(transferRequest.getFormat());
        return transferResponse;
    }
}
