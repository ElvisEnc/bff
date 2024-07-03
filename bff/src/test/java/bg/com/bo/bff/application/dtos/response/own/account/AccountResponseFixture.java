package bg.com.bo.bff.application.dtos.response.own.account;

public class AccountResponseFixture {
    public static GetTransactionLimitResponse withDefaultGetTransactionLimitResponse(){
        return new GetTransactionLimitResponse(
                TransactionLimitData.builder()
                        .amountLimit(1000)
                        .countLimit(10)
                        .build()
        );
    }
}