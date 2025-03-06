package bg.com.bo.bff.providers.dtos.request.loans.mw;

import java.math.BigDecimal;

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
                        .identification("123456")
                        .build())
                .creditorAccountRequest(LoanPaymentMWRequest.CreditorAccount.builder()
                        .schemaName("schemaName")
                        .sessionId("12345")
                        .build())
                .supplementaryData(LoanPaymentMWRequest.SupplementaryData.builder()
                        .sourceOfFunds("Fuente de fondos para la transferencia")
                        .destinationOfFunds("Destino de los fondos para la transferencia")
                        .build())
                .build();
    }

    public static Pcc01MWRequest withDefaultPcc01MWRequest() {
        return Pcc01MWRequest.builder()
                .currency("068")
                .amount(new BigDecimal("10000"))
                .accountId("123456")
                .personId("12345689")
                .build();
    }
}
