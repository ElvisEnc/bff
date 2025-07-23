package bg.com.bo.bff.providers.models.enums.external.services.crypto.currency;

import bg.com.bo.bff.providers.models.enums.external.services.FeignConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoCurrencyFeignConfig extends FeignConfig<CryptoCurrencyError> {
    public CryptoCurrencyFeignConfig() {
        super(CryptoCurrencyError.class);
    }
}
