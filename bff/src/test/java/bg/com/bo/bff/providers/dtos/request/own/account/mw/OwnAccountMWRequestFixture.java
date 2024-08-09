package bg.com.bo.bff.providers.dtos.request.own.account.mw;

public class OwnAccountMWRequestFixture {

    public static UpdateTransactionLimitMWRequest withDefaultUpdateTransactionLimitMWRequest() {
        return new UpdateTransactionLimitMWRequest("1000", "1");
    }

    public static AccountStatementsMWRequest withDefaultAccountStatementsMWRequest() {
        return AccountStatementsMWRequest.builder()
                .accountId("123")
                .startDate("2024-08-08")
                .endDate("2024-08-08")
                .initCount("1")
                .totalCount("100")
                .build();
    }
}
