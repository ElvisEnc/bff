package bg.com.bo.bff.providers.dtos.response.debit.card;

import java.math.BigDecimal;
import java.util.Arrays;

public class AccountsDebitCardMWResponseFixture {
    public static AccountsDebitCardMWResponse withDefault() {
        return AccountsDebitCardMWResponse.builder()
                .data(Arrays.asList(withDefaultAccountDC(), withDefaultAccountDC()))
                .build();
    }

    public static AccountsDebitCardMWResponse.AccountDebitCard withDefaultAccountDC() {
        return AccountsDebitCardMWResponse.AccountDebitCard.builder()
                .jtsOid(123)
                .accountNumber(BigDecimal.valueOf(123))
                .productType("123")
                .statusDescription("123")
                .currency("123")
                .pledgeFund(123)
                .currencyCode("123")
                .accountType("123")
                .ordinalPreference(123)
                .build();
    }
}