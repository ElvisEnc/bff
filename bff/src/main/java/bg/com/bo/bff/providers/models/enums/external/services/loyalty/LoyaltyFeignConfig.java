package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoyaltyFeignConfig {
    @Bean
    public ErrorDecoder loyaltyErrorDecoder() {
        return new LoyaltyFeignErrorDecoder();
    }
}
