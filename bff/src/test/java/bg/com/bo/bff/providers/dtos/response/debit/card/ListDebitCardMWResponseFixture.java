package bg.com.bo.bff.providers.dtos.response.debit.card;

import java.util.Arrays;

public class ListDebitCardMWResponseFixture {
    public static ListDebitCardMWResponse withDefault() {
        return ListDebitCardMWResponse.builder()
                .data(Arrays.asList(debitCardMWDefault(), debitCardMWDefault()))
                .build();
    }

    public static ListDebitCardMWResponse.DebitCardMW debitCardMWDefault() {
        return ListDebitCardMWResponse.DebitCardMW.builder()
                .idPci("123")
                .cardId("123")
                .department("123")
                .nroClient("123")
                .nroPerson("123")
                .cardHolder("123")
                .cardName("123")
                .deliveryDate("123")
                .expirationDate("123")
                .statusDescription("123")
                .status("123")
                .protectionInsurance("123")
                .insuranceAccountId("123")
                .insuranceAccountNumber("123")
                .statusCurrier("123")
                .build();
    }
}