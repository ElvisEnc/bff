package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ITransferACHProvider {
    TransferMWResponse transferAchAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameter) throws IOException;
}
