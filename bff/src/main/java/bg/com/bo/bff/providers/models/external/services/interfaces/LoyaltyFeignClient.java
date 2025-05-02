package bg.com.bo.bff.providers.models.external.services.interfaces;

import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTradeCategoryResponse;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(
        name = "loyalty-client",
        url = "${loyalty.server.url}",
        configuration = LoyaltyFeignConfig.class
)
public interface LoyaltyFeignClient {

    @GetMapping("/lealtad/beneficios/api/v1/comercios-ganamovil/categorias-comercios") // esto debe coincidir con LoyaltyServices.GET_TRADE_CATEGORIES.getServiceURL()
    LoyaltyGetTradeCategoryResponse[] getTradeCategories(@RequestHeader Map<String, String> headers);
}