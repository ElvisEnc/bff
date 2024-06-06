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
}
