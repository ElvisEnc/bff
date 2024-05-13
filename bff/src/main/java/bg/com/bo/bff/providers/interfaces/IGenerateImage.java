package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;

public interface IGenerateImage {
    String generateImage(TransferResponseMD response);
}
