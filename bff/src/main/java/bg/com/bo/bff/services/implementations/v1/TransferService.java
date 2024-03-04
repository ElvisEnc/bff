package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.services.interfaces.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransferService implements ITransferService {

    @Autowired
    private ITransferProvider iPcc01MiddlewareService;

    public Pcc01Response makeControl(Pcc01Request request) throws IOException {
        return iPcc01MiddlewareService.validateControl(request);
    }
}
