package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;

import java.io.IOException;

public interface ICryptoCurrencyProvider {

    CryptoCurrencyPostRegisterAccountResponse registerAccount (String personId) throws IOException;
}
