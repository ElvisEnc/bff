package bg.com.bo.bff.application.dtos.response.own.account;

public class AccountResponseFixture {
    public static TransactionLimitsResponse withDefaultGetTransactionLimitResponse(){
        return  TransactionLimitsResponse.builder()
                .amountLimit(1000)
                .countLimit(10)
                .build();
    }
}