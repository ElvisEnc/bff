package bg.com.bo.bff.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalServiceConfigFactory {
    @Value("${loyalty.server.url}")
    private String urlExternalBase;

    @Bean
    public ExternalServiceConfig integrationExternalProviderConfig() {
        return ExternalServiceConfig.builder()
                .urlExternalBase(urlExternalBase)
                .build();
    }
}
