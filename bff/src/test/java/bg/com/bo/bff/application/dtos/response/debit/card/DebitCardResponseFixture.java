package bg.com.bo.bff.application.dtos.response.debit.card;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsPeriod;

import bg.com.bo.bff.commons.utils.Util;
import java.util.Arrays;
import java.util.List;

public class DebitCardResponseFixture {
    public static DebitCard withDefaultDebitCard() {
        return DebitCard.builder()
                .id("123")
                .cardNumber("**** **** **** 1234")
                .holderName("123")
                .expiryDate("12/12/2024")
                .status(Util.getStatusDebitCard("Undefined"))
                .build();
    }

    public static AccountTD withDefaultAccountTD() {
        return AccountTD.builder()
                .accountId("123")
                .accountNumber("123")
                .description("123")
                .build();
    }

    public static DCDetailResponse withDefaultDCDetailResponse() {
        return DCDetailResponse.builder()
                .cardNumber("1234567890")
                .holderName("John Doe")
                .expirationDate("2024-08-04")
                .status(Util.getStatusDebitCard("A"))
                .assured(true)
                .build();
    }

    public static ListAccountTDResponse withDefaultListAccountTDResponse() {
        return ListAccountTDResponse.builder()
                .data(Arrays.asList(withDefaultAccountTD(), withDefaultAccountTD()))
                .build();
    }

    public static ListDebitCardResponse withDefaultListDebitCardResponse() {
        return ListDebitCardResponse.builder()
                .data(Arrays.asList(withDefaultDebitCard(), withDefaultDebitCard()))
                .build();
    }

    public static InternetAuthorizationResponse withDefaultInternetAuthorizationResponse(){
        DCLimitsPeriod period = DCLimitsPeriod.builder()
                .start("07/06/2026")
                .end("10/06/2026")
                .build();
        DCInternetAuthorization dcInternetAuthorization = DCInternetAuthorization.builder()
                .id("12078954")
                .amount("100")
                .period(period)
                .build();
        return InternetAuthorizationResponse.builder()
                .data( List.of(dcInternetAuthorization))
                .build();
    }
}