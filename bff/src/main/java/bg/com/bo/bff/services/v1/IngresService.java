package bg.com.bo.bff.services.v1;

import org.springframework.stereotype.Service;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.IngresoRequest;
import bg.com.bo.bff.provider.interfaces.IIngresProvider;
import bg.com.bo.bff.provider.request.IngresoRequestDTO;
import bg.com.bo.bff.services.interfaces.IIngresService;

@Service
public class IngresService implements IIngresService {

    private final IIngresProvider provider;

    public IngresService(IIngresProvider iIngresProvider) {
        this.provider = iIngresProvider;
    }

    @Override
    public Object ingres(IngresoRequest request) throws IOException {
        return provider.ingres(IngresoRequestDTO.toParse(request));
    }
}
