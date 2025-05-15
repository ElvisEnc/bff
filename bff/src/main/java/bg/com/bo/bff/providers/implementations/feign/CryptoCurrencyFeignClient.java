package bg.com.bo.bff.providers.implementations.feign;

import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "crypto-assets",
        url = "${loyalty.server.url}",
        configuration = CryptoCurrencyFeignConfig.class
)
public interface CryptoCurrencyFeignClient {

    @GetMapping("/empresas/criptoactivos-mdw/api/v1/authentication")
    ClientToken getTokenAuthentication(
            @RequestBody TokenAuthenticationRequestDto tokenAuthenticationRequestDto
    );

    @PostMapping("/empresas/criptoactivos-mdw/api/v1/account/account-create")
    CryptoCurrencyPostRegisterAccountResponse createAccount(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptoCurrencyPersonRequest requestServer
    );
}
