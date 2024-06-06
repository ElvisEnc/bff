package bg.com.bo.bff.application.dtos.request.debit.card;

import java.math.BigDecimal;

public class DCLimitsRequestFixture {
    public static DCLimitsRequest withDefault() {
        return DCLimitsRequest.builder()
                .dailyAmount(BigDecimal.valueOf(1000))
                .dailyCount("10")
                .period(DCLimitsPeriod.builder()
                        .start("2024-06-06")
                        .end("2024-06-06")
                        .build()
                )
                .build();
    }
}
