package bg.com.bo.bff.application.dtos.response.debit.card;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsPeriod;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;

import java.math.BigDecimal;

public class DCDetailResponseFixture {
    public static DCDetailResponse withDefault() {
        return DCDetailResponse.builder()
                .cardNumber("1234567890")
                .holderName("John Doe")
                .expirationDate("2024-08-04")
                .status("A")
                .assured(true)
                .build();
    }
}
