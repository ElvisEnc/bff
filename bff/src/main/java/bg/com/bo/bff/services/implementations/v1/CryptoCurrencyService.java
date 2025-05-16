package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.ExchangeOperationRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.GenerateVoucherRequest;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountEmailResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountExtractResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AvailableBalanceResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeOperationResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeRateResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.GenerateVoucherResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.user.AppCodeResponseNet;
import bg.com.bo.bff.mappings.providers.crypto.currency.ICryptoCurrencyMapper;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeOperationRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyGenerateVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyNroPersonRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyAccountExtractRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeRateRequest;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyAccountExtractResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeOperationResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGenerateVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAccountEmailResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAvailableBalanceResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.implementations.TokenExternalProvider;
import bg.com.bo.bff.providers.implementations.feign.CryptoCurrencyFeignClient;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyError;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyResponse;
import bg.com.bo.bff.services.interfaces.ICryptoCurrencyService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CryptoCurrencyService implements ICryptoCurrencyService {

    private final TokenExternalProvider tokenExternalProvider;
    private final ICryptoCurrencyMapper mapper;
    private final CryptoCurrencyFeignClient cryptoFeignClient;
    private static final String USER = "JBANK1";
    private static final String PWD = "SYSTEMS1";

    public CryptoCurrencyService(TokenExternalProvider tokenExternalProvider,
                                 ICryptoCurrencyMapper idcMapper, CryptoCurrencyFeignClient cryptoFeignClient) {
        this.tokenExternalProvider = tokenExternalProvider;
        this.mapper = idcMapper;
        this.cryptoFeignClient = cryptoFeignClient;
    }

    @Override
    public GenericResponse registerAccount(String personId) throws IOException {
        CryptoCurrencyPersonRequest requestServer = mapper.mapperRequest(personId);

        CryptoCurrencyPostRegisterAccountResponse responseServer = cryptoFeignClient.createAccount(
                getToken().getAuthorizationHeader(), requestServer
        );
        if (responseServer.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(CryptoCurrencyResponse.REGISTERED_SUCCESS);
        }
        throw new GenericException(CryptoCurrencyError.USER_REGISTERED);
    }

    @Override
    public AvailableBalanceResponse getAvailableBalance(String personId) throws IOException {
        CryptoCurrencyNroPersonRequest requestServer = mapper.mapperRequestPerson(personId);

        CryptoCurrencyGetAvailableBalanceResponse responseServer = cryptoFeignClient.availableBalance(
                getToken().getAuthorizationHeader(), requestServer
        );
        if (responseServer.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return mapper.convertResponse(responseServer);
        }
        throw new GenericException(CryptoCurrencyError.ACCOUNT_NOT_FOUND);
    }

    @Override
    public AccountEmailResponse getAccountEmail(String personId) throws IOException {
        CryptoCurrencyNroPersonRequest requestServer = mapper.mapperRequestPerson(personId);

        CryptoCurrencyGetAccountEmailResponse responseServer = cryptoFeignClient.accountEmail(
                getToken().getAuthorizationHeader(), requestServer
        );
        if (responseServer.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return mapper.convertResponse(responseServer);
        }
        throw new GenericException(CryptoCurrencyError.EMAIL_NOT_FOUND);
    }

    @Override
    public List<AccountExtractResponse> getAccountExtract(String personId, String accountId, AccountExtractRequest request) throws IOException {
        CryptoCurrencyAccountExtractRequest requestServer = mapper.mapperRequest(accountId, request);

        CryptoCurrencyAccountExtractResponse responseServer = cryptoFeignClient.accountExtract(
                getToken().getAuthorizationHeader(), requestServer
        );
        if (responseServer.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return mapper.convertResponse(responseServer);
        }
        throw new GenericException(CryptoCurrencyError.EXTRACT_NOT_FOUND);
    }

    @Override
    public ExchangeRateResponse getExchangeRate(String personId, String currencyId) throws IOException {
        CryptoCurrencyExchangeRateRequest requestServer = mapper.mapperRequest(personId, currencyId);

        CryptoCurrencyExchangeRateResponse responseServer = cryptoFeignClient.exchangeRate(
                getToken().getAuthorizationHeader(), requestServer
        );
        if (responseServer.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return mapper.convertResponse(responseServer);
        }
        throw new GenericException(CryptoCurrencyError.ERROR_EXCHANGE);
    }

    @Override
    public ExchangeOperationResponse exchangeOperation(String personId, String accountId, ExchangeOperationRequest request) throws IOException {
        CryptoCurrencyExchangeOperationRequest requestServer = mapper.mapperRequest(personId, accountId, request);

        CryptoCurrencyExchangeOperationResponse responseServer = cryptoFeignClient.exchangeOperation(
                getToken().getAuthorizationHeader(), requestServer
        );
        if (responseServer.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return mapper.convertResponse(responseServer);
        }
        throw new GenericException(CryptoCurrencyError.ERROR_EXCHANGE_OPERATION);
    }

    @Override
    public GenerateVoucherResponse postGenerateVoucher(String personId, GenerateVoucherRequest request) throws IOException {
        CryptoCurrencyGenerateVoucherRequest requestServer = mapper.mapperRequest(request);

        CryptoCurrencyGenerateVoucherResponse responseServer = cryptoFeignClient.generateVoucher(
                getToken().getAuthorizationHeader(), requestServer
        );
        if (responseServer.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return mapper.convertResponse(responseServer);
        }
        throw new GenericException(CryptoCurrencyError.ERROR_VOUCHER);
    }

    private ClientToken getToken(){
        return tokenExternalProvider.generateAccountAccessToken(
                TokenAuthenticationRequestDto.builder()
                        .username(USER)
                        .password(PWD)
                        .build()
        );
    }
}
