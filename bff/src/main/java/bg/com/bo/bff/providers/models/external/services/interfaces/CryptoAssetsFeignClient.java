package bg.com.bo.bff.providers.models.external.services.interfaces;

import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTradeCategoryResponse;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(
        name = "crypto-assets",
        url = "http://api.stg.bg.com.bo/empresas/criptoactivos-mdw/api/v1",
        configuration = LoyaltyFeignConfig.class
)
public interface CryptoAssetsFeignClient {

    @GetMapping("/authentication") // esto debe coincidir con LoyaltyServices.GET_TRADE_CATEGORIES.getServiceURL()
    ClientToken getTokenAuthentication(@RequestBody TokenAuthenticationRequestDto tokenAuthenticationRequestDto);
}