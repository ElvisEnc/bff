package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;

public interface IGenerateImage {
    String generateImage(TransferMWResponse response);
}
