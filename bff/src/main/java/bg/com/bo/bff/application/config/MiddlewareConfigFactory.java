package bg.com.bo.bff.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiddlewareConfigFactory {
    @Value("${mw.token}")
    private String token_path;

    @Value("${middleware}")
    private String url_base;

    @Value("${mw.client_transfer}")
    private String client_secret_transfer;

    @Bean
    public MiddlewareConfig integrationProviderConfig() {
        return MiddlewareConfig.builder()
                .token_path(token_path)
                .client_transfer(client_secret_transfer)
                .url_base(url_base).build();
    }
}
