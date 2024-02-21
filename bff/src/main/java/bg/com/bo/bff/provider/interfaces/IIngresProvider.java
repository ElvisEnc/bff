package bg.com.bo.bff.provider.interfaces;

import java.io.IOException;

import bg.com.bo.bff.provider.request.IngresoRequestDTO;

public interface IIngresProvider {
    Object ingres(IngresoRequestDTO request) throws IOException;

}
