package bg.com.bo.bff.application.dtos.response;

public class BiometricsResponseFixture {
    public static BiometricsResponse withDefault() {
        return BiometricsResponse.builder()
                .status(true)
                .authenticationType("5")
                .build();
    }
}