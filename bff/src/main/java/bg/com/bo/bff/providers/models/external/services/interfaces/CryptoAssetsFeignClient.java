package bg.com.bo.bff.providers.models.external.services.interfaces;

import bg.com.bo.bff.commons.enums.config.provider.external.ProjectName;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTradeCategoryResponse;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(
        name = "crypto-assets",
        url = "${loyalty.server.url}",
        configuration = LoyaltyFeignConfig.class
)
public interface CryptoAssetsFeignClient {

    public static final String BASE_URI = "/empresas/criptoactivos-mdw/api/v1";

    @GetMapping(BASE_URI + "/authentication")
    ClientToken getTokenAuthentication(
            @RequestBody TokenAuthenticationRequestDto tokenAuthenticationRequestDto
    );


    @RequestMapping(
            path = "/account/account-create",
            method = RequestMethod.POST)
    CryptoCurrencyPostRegisterAccountResponse createAccount(
            @RequestHeader("Authorization") String token,
            @RequestBody CryptCurrencyPersonRequest requestServer
    );
}