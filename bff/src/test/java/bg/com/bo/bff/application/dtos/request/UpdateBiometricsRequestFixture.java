package bg.com.bo.bff.application.dtos.request;

public class UpdateBiometricsRequestFixture {
    public static UpdateBiometricsRequest withDefault() {
        return UpdateBiometricsRequest.builder()
                .status(true)
                .tokenBiometric("sd12fs1df32s1df")
                .typeAuthentication("5")
                .build();
    }
}