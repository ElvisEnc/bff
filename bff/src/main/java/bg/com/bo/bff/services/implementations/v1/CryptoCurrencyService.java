package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountEmailResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountExtractResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AvailableBalanceResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeRateResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.user.AppCodeResponseNet;
import bg.com.bo.bff.mappings.providers.crypto.currency.ICryptoCurrencyMapper;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyAccountExtractRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeRateRequest;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyAccountExtractResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAccountEmailResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAvailableBalanceResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.implementations.TokenExternalProvider;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyError;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyResponse;
import bg.com.bo.bff.services.interfaces.ICryptoCurrencyService;
import bg.com.bo.bff.providers.interfaces.ICryptoCurrencyProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CryptoCurrencyService implements ICryptoCurrencyService {

    private final ICryptoCurrencyProvider provider;
    private final TokenExternalProvider tokenExternalProvider;
    private final ICryptoCurrencyMapper mapper;

    public CryptoCurrencyService(ICryptoCurrencyProvider provider, TokenExternalProvider tokenExternalProvider,
                                 ICryptoCurrencyMapper idcMapper) {
        this.provider = provider;
        this.tokenExternalProvider = tokenExternalProvider;
        this.mapper = idcMapper;
    }

    @Override
    public GenericResponse registerAccount(String personId) throws IOException {
        CryptCurrencyPersonRequest requestServer = mapper.mapperRequest(personId);

        ClientToken token = this.tokenExternalProvider.generateAccountAccessToken(
                TokenAuthenticationRequestDto.builder().password("").username("").build()
        );
        CryptoCurrencyPostRegisterAccountResponse responseServer = provider.registerAccount(
                token.getAccessToken(), requestServer
        );
        if (responseServer.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(CryptoCurrencyResponse.REGISTERED_SUCCESS);
        }
        throw new GenericException(CryptoCurrencyError.USER_REGISTERED);
    }

}
