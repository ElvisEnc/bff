package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.interfaces.ICryptoCurrencyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyServices;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyError;
import bg.com.bo.bff.providers.models.external.services.HttpClientExternalProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CryptoCurrencyProvider extends HttpClientExternalProvider<LoyaltyError> implements ICryptoCurrencyProvider {

    @Value("${loyalty.server.url}")
    private String baseUrl;

    public CryptoCurrencyProvider(IHttpClientFactory httpClientFactory, ObjectMapper objectMapper) {
        super(httpClientFactory, objectMapper, LoyaltyError.class);
    }

    @Override
    public CryptoCurrencyPostRegisterAccountResponse registerAccount(String personId) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_REGISTER_ACCOUNT.getServiceURL());
        return this.executeGetRequest(url, null, CryptoCurrencyPostRegisterAccountResponse.class);
    }
}
