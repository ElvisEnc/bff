package bg.com.bo.bff.providers.dtos.request.login;

public class UpdateBiometricsMWRequestFixture {
    public static UpdateBiometricsMWRequest withDefault() {
        return UpdateBiometricsMWRequest.builder()
                .statusBiometric("S")
                .typeAuthentication("5")
                .tokenBiometric("sd12fs1df32s1df")
                .build();
    }
}