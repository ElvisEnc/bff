package bg.com.bo.bff.providers.dtos.response.login;

import bg.com.bo.bff.application.dtos.response.UpdateBiometricsResponseFixture;

public class UpdateBiometricMWResponseFixture {
    public static UpdateBiometricMWResponse withDefault() {
        return UpdateBiometricMWResponse.builder()
                .data(UpdateBiometricsResponseFixture.withDefault())
                .build();
    }
}