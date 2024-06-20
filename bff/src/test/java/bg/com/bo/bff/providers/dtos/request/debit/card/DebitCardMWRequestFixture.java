package bg.com.bo.bff.providers.dtos.request.debit.card;

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

}
