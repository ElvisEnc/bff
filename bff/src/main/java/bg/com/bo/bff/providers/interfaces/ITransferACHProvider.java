package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;

import java.io.IOException;
import java.util.Map;

public interface ITransferACHProvider {
    TransferAchMwResponse transferAchAccount(TransferMWRequest request, Map<String, String> parameter) throws IOException;
}
