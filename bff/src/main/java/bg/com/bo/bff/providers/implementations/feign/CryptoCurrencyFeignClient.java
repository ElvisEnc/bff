package bg.com.bo.bff.providers.implementations.feign;

import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyAccountExtractRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeOperationRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeRateRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyGenerateVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyNroPersonRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyAccountExtractResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeOperationResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGenerateVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAccountEmailResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAvailableBalanceResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "crypto-assets",
        url = "${loyalty.server.url}",
        configuration = CryptoCurrencyFeignConfig.class
)
public interface CryptoCurrencyFeignClient {

    String BASE_URL = "/empresas/criptoactivos-mdw/api/v1";

    @PostMapping(BASE_URL + "/authentication")
    ClientToken getTokenAuthentication(
            @RequestBody TokenAuthenticationRequestDto tokenAuthenticationRequestDto
    );

    @PostMapping(BASE_URL + "/account/account-create")
    CryptoCurrencyPostRegisterAccountResponse createAccount(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptoCurrencyPersonRequest requestServer
    );

    @PostMapping(BASE_URL + "/account/available-balance")
    CryptoCurrencyGetAvailableBalanceResponse availableBalance(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptoCurrencyNroPersonRequest requestServer
    );

    @PostMapping(BASE_URL + "/account/account-email")
    CryptoCurrencyGetAccountEmailResponse accountEmail(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptoCurrencyNroPersonRequest requestServer
    );

    @PostMapping(BASE_URL + "/divisa-exchange/account-extract")
    CryptoCurrencyAccountExtractResponse accountExtract(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptoCurrencyAccountExtractRequest requestServer
    );

    @PostMapping(BASE_URL + "/divisa-exchange/exchange-rate")
    CryptoCurrencyExchangeRateResponse exchangeRate(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptoCurrencyExchangeRateRequest requestServer
    );

    @PostMapping(BASE_URL + "/divisa-exchange/exchange-operation")
    CryptoCurrencyExchangeOperationResponse exchangeOperation(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptoCurrencyExchangeOperationRequest requestServer
    );

    @PostMapping(BASE_URL + "/divisa-exchange/generate-voucher")
    CryptoCurrencyGenerateVoucherResponse generateVoucher(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptoCurrencyGenerateVoucherRequest requestServer
    );
}
