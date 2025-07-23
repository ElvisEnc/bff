package bg.com.bo.bff.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiddlewareConfigFactory {

    @Value("${encryption.private.key}")
    private String privateKey;

    @Value("${encryption.public.key}")
    private String publicKey;

    @Value("${mw.token}")
    private String tokenPath;

    @Value("${middleware}")
    private String urlBase;

    @Value("${mw.client_transfer}")
    private String clientSecretTransfer;

    @Value("${client.secret.tranfer-ach}")
    private String clientSecretTransferACH;

    @Value("${client.secret.login}")
    private String clientSecretLogin;

    @Value("${client.secret.accounts}")
    private String clientSecretOwn;

    @Value("${client.secret.third-accounts}")
    private String clientSecretThirdAccounts;

    @Value("${client.secret.ach-accounts}")
    private String clientSecretAchAccounts;

    @Value("${client.secret.dpf}")
    private String clientSecretDPFManager;

    @Value("${client.secret.generate.qr.manager}")
    private String clientSecretGenerateQrManager;

    @Value("${client.secret.qr.transaction.manager}")
    private String clientQrTransactionManager;

    @Value("${client.secret.debit.card.manager}")
    private String clientDebitCardManager;

    @Value("${client.secret.payment.services.manager}")
    private String clientPaymentServicesManager;

    @Value("${client.secret.loans.manager}")
    private String clientLoansManager;

    @Value("${client.secret.loans.transaction.manager}")
    private String clientLoansTransactionManager;

    @Value("${client.secret.credit.card.manager}")
    private String clientCreditCardManager;

    @Value("${client.secret.credit.card.transaction.manager}")
    private String clientCreditCardTransactionManager;

    @Value("${client.secret.point.attention.manager}")
    private String clientPointAttentionManager;

    @Value("${client.secret.account.statement.manager}")
    private String clientAccountStatementManager;

    @Value("${client.secret.remittance.manager}")
    private String clientRemittanceManager;

    @Value("${client.secret.softtoken.manager}")
    private String clientSoftTokenManager;

    @Value("${client.secret.certifications.manager}")
    private String clientCertificationsManager;

    @Value("${client.secret.users.questions.manager}")
    private String clientUsersQuestionsManager;

    @Value("${client.secret.onboarding.manager}")
    private String clientOnboardingManager;

    @Value("${client.secret.transfer.programming.manager}")
    private String clientTransferProgramming;

    @Value("${client.secret.notification.config.manager}")
    private String clientNotificationConfigManager;

    @Bean
    public MiddlewareConfig integrationProviderConfig() {
        return MiddlewareConfig.builder()
                .privateKey(privateKey)
                .publicKey(publicKey)
                .urlBase(urlBase)
                .tokenPath(tokenPath)
                .clientLogin(clientSecretLogin)
                .clientOwnManager(clientSecretOwn)
                .clientTransfer(clientSecretTransfer)
                .clientTransferACH(clientSecretTransferACH)
                .clientThirdAccount(clientSecretThirdAccounts)
                .clientAchAccount(clientSecretAchAccounts)
                .dpfManager(clientSecretDPFManager)
                .clientGenerateQrManager(clientSecretGenerateQrManager)
                .clientQrTransactionManager(clientQrTransactionManager)
                .clientDebitCardManager(clientDebitCardManager)
                .clientPaymentServicesManager(clientPaymentServicesManager)
                .clientLoansManager(clientLoansManager)
                .clientLoansTransactionManager(clientLoansTransactionManager)
                .clientCreditCardManager(clientCreditCardManager)
                .clientCreditCardTransactionManager(clientCreditCardTransactionManager)
                .clientPointAttentionManager(clientPointAttentionManager)
                .clientAccountStatementManager(clientAccountStatementManager)
                .clientRemittanceManager(clientRemittanceManager)
                .clientSoftTokenManager(clientSoftTokenManager)
                .clientCertificationsManager(clientCertificationsManager)
                .clientUsersQuestionsManager(clientUsersQuestionsManager)
                .clientOnboardingManager(clientOnboardingManager)
                .clientTransferProgramming(clientTransferProgramming)
                .clientNotificationConfigManager(clientNotificationConfigManager)
                .build();
    }
}
