package bg.com.bo.bff.application.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MiddlewareConfig {

    private String privateKey;

    private String publicKey;

    private String urlBase;

    private String tokenPath;

    private String clientLogin;

    private String clientOwnManager;

    private String clientTransfer;

    private String clientTransferACH;

    private String clientThirdAccount;

    private String clientAchAccount;

    private String dpfManager;

    private String clientGenerateQrManager;

    private String clientQrTransactionManager;

    private String clientDebitCardManager;

    private String clientPaymentServicesManager;

    private String clientLoansManager;

    private String clientLoansTransactionManager;

    private String clientRemittanceManager;

    private String clientCreditCardManager;

    private String clientCreditCardTransactionManager;

    private String clientPointAttentionManager;

    private String clientAccountStatementManager;

    private String clientSoftTokenManager;
}
