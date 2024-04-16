package bg.com.bo.bff.application.dtos.response;

import java.util.Collections;

public class ExtractDataResponseFixture {
    public static ExtractDataResponse withDefault() {
        return ExtractDataResponse.builder()
                .data(Collections.singletonList(ExtractDataResponse.ExtractResponse.builder()
                        .status("status")
                        .type("type")
                        .amount(100.00)
                        .currency("currency")
                        .channel("channel")
                        .dateMov("dateMov")
                        .timeMov("timeMov")
                        .movBalance(987.99)
                        .seatNumber("seatNumber")
                        .build()))
                .build();
    }
}