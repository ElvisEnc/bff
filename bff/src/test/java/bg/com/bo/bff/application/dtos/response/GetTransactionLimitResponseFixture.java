package bg.com.bo.bff.application.dtos.response;

public class GetTransactionLimitResponseFixture {
    public static GetTransactionLimitResponse withDefault(){
        return new GetTransactionLimitResponse(
                TransactionLimitData.builder()
                        .amountLimit(1000)
                        .countLimit(10)
                        .build()
        );
    }
}
