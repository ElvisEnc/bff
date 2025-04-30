package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTradeCategoryResponse;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProviderFeignAdapter;
import bg.com.bo.bff.providers.models.external.services.interfaces.LoyaltyFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoyaltyProviderFeignAdapter implements ILoyaltyProviderFeignAdapter {
    private final LoyaltyFeignClient loyaltyFeignClient;


    @Override
    public List<LoyaltyGetTradeCategoryResponse> getTradeCategories(Map<String, String> headers, String personId) {
        // personId no se usa directamente porque la URL no lo requiere
        return Arrays.asList(loyaltyFeignClient.getTradeCategories(headers));
    }
}
