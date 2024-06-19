package bg.com.bo.bff.application.dtos.request.qr;

public class PeriodRequestFixture {
    public static PeriodRequest withDefault() {
        return PeriodRequest.builder()
                .start("2024-06-13")
                .end("2024-06-17")
                .build();
    }
}