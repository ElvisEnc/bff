package bg.com.bo.bff.application.dtos.response.own.account;

public class AccountResponseFixture {
    public static TransactionLimitsResponse withDefaultGetTransactionLimitResponse() {
        return TransactionLimitsResponse.builder()
                .amountLimit(1000)
                .countLimit(10)
                .build();
    }

    public static TransactionLimitsResponse withDefaultTransactionLimitsResponse() {
        return TransactionLimitsResponse.builder()
                .countLimit(123)
                .countLimitRegistered(10)
                .amountLimit(10)
                .amountLimitGroup(10)
                .currencyCode("068")
                .type("I")
                .build();
    }
}