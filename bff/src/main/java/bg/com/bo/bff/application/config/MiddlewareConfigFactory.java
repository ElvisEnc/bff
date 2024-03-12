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

    @Value("${client.secret.login}")
    private String client_secret_login;

    @Bean
    public MiddlewareConfig integrationProviderConfig() {
        return MiddlewareConfig.builder()
                .tokenPath(token_path)
                .clientTransfer(client_secret_transfer)
                .clientLogin(client_secret_login)
                .urlBase(url_base).build();
    }
}
