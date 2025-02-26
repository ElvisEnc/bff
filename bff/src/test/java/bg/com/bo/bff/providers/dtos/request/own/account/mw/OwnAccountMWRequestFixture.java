package bg.com.bo.bff.providers.dtos.request.own.account.mw;

public class OwnAccountMWRequestFixture {

    public static UpdateTransactionLimitMWRequest withDefaultUpdateTransactionLimitMWRequest() {
        return new UpdateTransactionLimitMWRequest("1000", "1");
    }

    public static AccountStatementsMWRequest withDefaultAccountStatementsMWRequest() {
        return AccountStatementsMWRequest.builder()
                .personId("123456")
                .companyId("123456")
                .accountId("123")
                .startDate("2024-08-08")
                .endDate("2024-08-08")
                .initCount("1")
                .totalCount("100")
                .build();
    }

    public static ReportTransfersMWRequest withDefaultTransferMovementsMWRequest() {
        return ReportTransfersMWRequest.builder()
                .accountId("123")
                .startDate("2024-08-08")
                .endDate("2024-08-08")
                .build();
    }
}
