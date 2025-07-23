package bg.com.bo.bff.providers.dtos.request.credit.card;

import java.math.BigDecimal;

public class CreditCardMWRequestFixture {
    public static BlockCreditCardMWRequest withDefaultBlockCreditCardMWRequest() {
        return BlockCreditCardMWRequest.builder()
                .cmsCardNumber("123")
                .personId("123")
                .codBlockType("123")
                .codBlockReason("123")
                .build();
    }

    public static CashAdvanceFeeMWRequest withDefaultCashAdvanceFeeMWRequest() {
        return CashAdvanceFeeMWRequest.builder()
                .amount(BigDecimal.valueOf(1000))
                .personId("1")
                .cmsAccountNumber("13-01-10-034599")
                .build();
    }

    public static CashAdvanceMWRequest withDefaultCashAdvanceMWRequest() {
        return CashAdvanceMWRequest.builder()
                .personId("987654321")
                .companyId("123456789")
                .cmsAccountNumber("13-45-10-123456")
                .cmsCardNumber("13-45-10-1234567890")
                .accountId("123456")
                .amount(new BigDecimal("100.00"))
                .description("Cash advance for travel")
                .build();
    }

    public static PayCreditCardMWRequest defaultPayCreditCardMWRequest() {
        return PayCreditCardMWRequest.builder()
                .ownerAccount(TransOwnerAccount.builder()
                        .schemeName("PersonId")
                        .personId("123")
                        .companyId("123")
                        .build())
                .debtorAccount(TransAccount.builder()
                        .schemeName("AccountId")
                        .identification("987654321")
                        .build())
                .creditorAccount(TransAccount.builder()
                        .schemeName("TcAccountId")
                        .identification("123456789")
                        .build())
                .instructedAmount(TransAmount.builder()
                        .currency("068")
                        .amount(new BigDecimal("100.00"))
                        .build())
                .supplementaryData(PayCreditCardMWRequest.SupplementaryData.builder()
                        .transactionType("2")
                        .description("Pago de tarjeta de crédito")
                        .sourceOfFunds("Cuenta de ahorros")
                        .destinationOfFunds("Tarjeta de crédito")
                        .build())
                .build();
    }

    public static AuthorizationCreditCardMWRequest withDefaultAuthorizationCreditCardMWRequest() {
        return AuthorizationCreditCardMWRequest.builder()
                .personId("123")
                .cmsAccountNumber("123")
                .cmsCardNumber("123")
                .initDate("2021-01-01")
                .endDate("2021-01-01")
                .amount("100.00")
                .type("H")
                .requestType("L")
                .build();
    }

    public static FeePrepaidCardMWRequest withDefaultComissionPrepaidCardMWRequest() {
        return FeePrepaidCardMWRequest.builder()
                .accountMaster("123")
                .cmsAccountNumber("123")
                .amount(new BigDecimal("100.00"))
                .build();
    }
}
