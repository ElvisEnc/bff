package bg.com.bo.bff.providers.dtos.request.debit.card.mw;

import java.math.BigDecimal;

public class DebitCardMWRequestFixture {
    public static DCLimitsMWRequest withDefault() {
        return DCLimitsMWRequest.builder()
                .pciId("1")
                .numberOperations("1")
                .amount(BigDecimal.valueOf(1000))
                .expirationDate("2024-06-06")
                .personId("1")
                .build();
    }

    public static DCLockStatusMWRequest withDefaultLockStatus() {
        return DCLockStatusMWRequest.builder()
                .pciId("1")
                .personId("1")
                .lockStatus("LOCKED")
                .comment("comment")
                .build();
    }

    public static DCAccountsOrderMWRequest withDefaultAccountsOrder() {
        return DCAccountsOrderMWRequest.builder()
                .pciId("1")
                .personId("1")
                .accountId1("1234567890")
                .accountId2("1234567890")
                .accountId3("1234567890")
                .accountId4("1234567890")
                .build();
    }

    public static DeleteAuthPurchaseMWRequest withDefaultDeleteAuthPurchaseMWRequest() {
        return DeleteAuthPurchaseMWRequest.builder()
                .identifierTD("123")
                .personId("123")
                .action("123")
                .idPci("123")
                .build();
    }

    public static UpdateDebitCardSecureMWRequest withDefaultUpdateDebitCardSecureMWRequest() {
        return UpdateDebitCardSecureMWRequest.builder()
                .personId("123")
                .debitCardNew("123")
                .pciId("123")
                .acceptInsurance("123")
                .email("123")
                .requestNumberOld("123")
                .build();
    }

    public static CreateAuthorizationOnlinePurchaseMWRequest withDefaultCreateAuthorizationOnlinePurchaseMWRequest() {
        return CreateAuthorizationOnlinePurchaseMWRequest.builder()
                .idPci("12121")
                .action("A")
                .initialDate("2024-06-01")
                .finalDate("2024-06-10")
                .amount(new BigDecimal("1000"))
                .intInitial(1)
                .intFinal(10)
                .build();
    }

    public static ActivateDebitCardMWRequest withDefaultActivateDebitCardMWRequest() {
        return ActivateDebitCardMWRequest.builder()
                .idPci("1234")
                .personId("1234")
                .build();
    }

    public static ChangePinMWRequest withDefaultChangePinCard() {
        return ChangePinMWRequest.builder()
                .personId("1")
                .idPci("1")
                .pinBlock("1")
                .build();
    }
}
