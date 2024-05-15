package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import bg.com.bo.bff.providers.interfaces.IGenerateImage;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.services.interfaces.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TransferService implements ITransferService {

    @Autowired
    private final ITransferProvider provider;
    private final IGenerateImage generateImageProvider;

    public TransferService(ITransferProvider provider, IGenerateImage generateImageProvider) {
        this.provider = provider;
        this.generateImageProvider = generateImageProvider;
    }

    public Pcc01Response makeControl(Pcc01Request request) throws IOException {
        return provider.validateControl(request);
    }

    @Override
    public TransferResponse transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferResponseMD response = provider.transferOwnAccount(personId, accountId, transferRequest, parameter);
        String newImage = generateImageProvider.generateImage(response);
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setData(newImage);
        transferResponse.setFormat(transferRequest.getFormat());
        return transferResponse;
    }

    @Override
    public TransferResponse transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferResponseMD response = provider.transferThirdAccount(personId, accountId, transferRequest, parameter);
        String newImage = generateImageProvider.generateImage(response);
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setData(newImage);
        transferResponse.setFormat(transferRequest.getFormat());
        return transferResponse;
    }
}
