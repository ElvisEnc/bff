package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

import bg.com.bo.bff.providers.models.enums.external.services.FeignConfig;
import bg.com.bo.bff.providers.models.enums.external.services.FeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoyaltyFeignConfig extends FeignConfig<LoyaltyError> {
    public LoyaltyFeignConfig() {
        super(LoyaltyError.class);
    }
}

