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

    @Value("${client.secret.tranfer-ach}")
    private String clientSecretTransferACH;

    @Value("${client.secret.login}")
    private String client_secret_login;

    @Value("${client.secret.accounts}")
    private String client_secret_own;

    @Value("${client.secret.third-accounts}")
    private String clientSecretThirdAccounts;

    @Value("${client.secret.ach-accounts}")
    private String client_secret_ach_accounts;

    @Value("${client.secret.dpf}")
    private String clientSecretDPFManager;

    @Value("${client.secret.generate.qr.manager}")
    private String clientSecretGenerateQrManager;

    @Value("${client.secret.qr.transaction.manager}")
    private String clientQrTransactionManager;

    @Bean
    public MiddlewareConfig integrationProviderConfig() {
        return MiddlewareConfig.builder()
                .urlBase(url_base)
                .tokenPath(token_path)
                .clientLogin(client_secret_login)
                .clientOwnManager(client_secret_own)
                .clientTransfer(client_secret_transfer)
                .clientTransferACH(clientSecretTransferACH)
                .clientThirdAccount(clientSecretThirdAccounts)
                .clientAchAccount(client_secret_ach_accounts)
                .dpfManager(clientSecretDPFManager)
                .clientGenerateQrManager(clientSecretGenerateQrManager)
                .clientQrTransactionManager(clientQrTransactionManager)
                .build();
    }
}
