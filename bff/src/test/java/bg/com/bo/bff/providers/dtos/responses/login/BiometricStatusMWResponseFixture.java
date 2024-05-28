package bg.com.bo.bff.providers.dtos.responses.login;

public class BiometricStatusMWResponseFixture {
    public static BiometricStatusMWResponse withDefault() {
        return BiometricStatusMWResponse.builder()
                .data(BiometricStatusMWResponse.BiometricStatusData.builder()
                        .statusBiometric("S")
                        .authenticationType("5")
                        .build())
                .build();
    }
}