package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;

import java.io.IOException;

public interface ITransferService {
    Pcc01Response makeControl(Pcc01Request request) throws IOException;
}
