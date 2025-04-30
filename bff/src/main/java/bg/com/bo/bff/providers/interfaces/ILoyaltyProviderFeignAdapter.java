package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.loyalty.*;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface ILoyaltyProviderFeignAdapter {
    List<LoyaltyGetTradeCategoryResponse> getTradeCategories(Map<String, String> headers, String personId)
            throws IOException;

}
