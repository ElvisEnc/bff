package bg.com.bo.bff.application.dtos.response;

public class UpdateBiometricsResponseFixture {
    public static UpdateBiometricsResponse withDefault() {
        return UpdateBiometricsResponse.builder()
                .personId("123")
                .build();
    }
}