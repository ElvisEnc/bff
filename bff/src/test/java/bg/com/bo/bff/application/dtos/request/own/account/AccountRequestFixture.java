package bg.com.bo.bff.application.dtos.request.own.account;

public class AccountRequestFixture {
    public static UpdateTransactionLimitRequest withDefaultUpdateTransactionLimitRequest() {
        return new UpdateTransactionLimitRequest("1000", "1");
    }
}