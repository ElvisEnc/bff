package bg.com.bo.bff.application.config;

public class MiddlewareConfigFixture {

    public static MiddlewareConfig withDefault() {
        return MiddlewareConfig.builder()
                .privateKey("privateKey")
                .publicKey("publicKey")
                .urlBase("http://localhost:8080/")
                .tokenPath("/path")
                .clientLogin("clientLogin")
                .clientOwnManager("clientOwnManager")
                .clientTransfer("clientTransfer")
                .clientTransferACH("clientTransferACH")
                .clientThirdAccount("clientThirdAccount")
                .clientAchAccount("clientAchAccount")
                .dpfManager("dpfManager")
                .clientGenerateQrManager("clientGenerateQrManager")
                .clientQrTransactionManager("clientQrTransactionManager")
                .clientDebitCardManager("clientDebitCardManager")
                .clientPaymentServicesManager("clientPaymentServicesManager")
                .build();
    }
}