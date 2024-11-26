package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.providers.dtos.request.transfer.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.Pcc01MWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferWalletMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ITransferProvider {
    TransferMWResponse transferOwnAccount(TransferMWRequest request, Map<String, String> parameter) throws IOException;

    TransferMWResponse transferThirdAccount(TransferMWRequest request, Map<String, String> parameter) throws IOException;

    TransferWalletMWResponse transferWalletAccount(TransferMWRequest request, Map<String, String> parameter) throws IOException;

    Pcc01Response validateControl(Pcc01MWRequest request, Map<String, String> parameter) throws IOException;
}
