package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;

import java.io.IOException;

public interface ITransferProvider {
    Pcc01Response validateControl(Pcc01Request request) throws IOException;
}

