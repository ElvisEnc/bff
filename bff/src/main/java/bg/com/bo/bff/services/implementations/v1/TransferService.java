package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import bg.com.bo.bff.providers.interfaces.IGenerateImage;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.interfaces.ITransferYoloNetProvider;
import bg.com.bo.bff.services.interfaces.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TransferService implements ITransferService {
    private ITransferProvider transferProvider;
    private final IGenerateImage generateImageProvider;
    private final ITransferYoloNetProvider transferYoloNetProvider;

    public TransferService(ITransferProvider transferProvider, IGenerateImage generateImageProvider, ITransferYoloNetProvider transferYoloNetProvider) {
        this.transferProvider = transferProvider;
        this.generateImageProvider = generateImageProvider;
        this.transferYoloNetProvider = transferYoloNetProvider;
    }


    @Override
    public TransferResponse transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferResponseMD response = transferProvider.transferOwnAccount(personId, accountId, transferRequest, parameter);
        String newImage = generateImageProvider.generateImage(response);
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setData(newImage);
        transferResponse.setFormat(transferRequest.getFormat());
        return transferResponse;
    }

    @Override
    public TransferResponse transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferResponseMD response = transferProvider.transferThirdAccount(personId, accountId, transferRequest, parameter);
        String newImage = generateImageProvider.generateImage(response);
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setData(newImage);
        transferResponse.setFormat(transferRequest.getFormat());
        return transferResponse;
    }

    @Override
    public TransferResponse transferWallet(Integer personId, Integer accountId, Integer accountNumber, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferResponseMD responseMD = transferYoloNetProvider.transferToYolo(personId, accountId, accountNumber, transferRequest);
        String newImage = generateImageProvider.generateImage(responseMD);
        return TransferResponse.builder()
                .data(newImage)
                .format(transferRequest.getFormat())
                .build();
    }

    @Override
    public Pcc01Response makeControl(Pcc01Request request) throws IOException {
        return transferProvider.validateControl(request);
    }
}
