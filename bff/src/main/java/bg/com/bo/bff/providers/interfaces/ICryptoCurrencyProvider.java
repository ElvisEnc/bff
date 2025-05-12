package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;

import java.io.IOException;

public interface ICryptoCurrencyProvider {

    CryptoCurrencyPostRegisterAccountResponse registerAccount (CryptCurrencyPersonRequest requestServer) throws IOException;


}
