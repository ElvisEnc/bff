package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyError;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyResponse;
import bg.com.bo.bff.services.interfaces.ICryptoCurrencyService;
import bg.com.bo.bff.providers.interfaces.ICryptoCurrencyProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CryptoCurrencyService implements ICryptoCurrencyService {

    private final ICryptoCurrencyProvider provider;

    public CryptoCurrencyService(ICryptoCurrencyProvider provider) {
        this.provider = provider;
    }

    @Override
    public GenericResponse registerAccount(String personId) throws IOException {
        CryptoCurrencyPostRegisterAccountResponse responseServer = provider.registerAccount(personId);
        if (responseServer.getStatusCode() == 200 && responseServer.getCodeError().equals("COD_000")) {
            return GenericResponse.instance(LoyaltyResponse.REGISTERED_EXIT);
        }
        throw new GenericException(LoyaltyError.REGISTER_ERROR);
    }
}
