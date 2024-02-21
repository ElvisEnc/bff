package bg.com.bo.bff.services.interfaces;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.IngresoRequest;

public interface IIngresService {
    Object ingres(IngresoRequest request) throws IOException;

}
