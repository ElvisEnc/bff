package bg.com.bo.bff.providers.dtos.request.loans.mw;

public class LoansMWRequestFixture {
    public static LoanPaymentMWRequest withDefaultLoanPaymentMWRequest() {
        return LoanPaymentMWRequest.builder()
                .ownerAccountRequest(LoanPaymentMWRequest.OwnAccount.builder()
                        .schemaName("schemaName")
                        .personId("123")
                        .companyId("0")
                        .build())
                .debtorAccountRequest(LoanPaymentMWRequest.DebtorAccount.builder()
                        .schemaName("schemaName")
                        .identification("312")
                        .build())
                .creditorAccountRequest(LoanPaymentMWRequest.CreditorAccount.builder()
                        .schemaName("schemaName")
                        .sessionId("123")
                        .build())
                .build();
    }
}
