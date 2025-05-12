package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;

import java.io.IOException;

public interface ICryptoCurrencyService {

    GenericResponse registerAccount (String personId) throws IOException;

}
