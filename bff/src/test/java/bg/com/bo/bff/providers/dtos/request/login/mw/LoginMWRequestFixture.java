package bg.com.bo.bff.providers.dtos.request.login.mw;

public class LoginMWRequestFixture {
    public static LoginCredentialMWRequest withDefaultLoginCredentialMWRequest() {
        return new LoginCredentialMWRequest("123", "123", "123", "123", "123");
    }

    public static ChangePasswordMWRequest withDefaultChangePasswordMWRequest() {
        ChangePasswordMWRequest request = new ChangePasswordMWRequest();
        request.setPreviousPassword("1234");
        request.setNewPassword("1234567A");
        request.setOwnerAccount(withDefaultMWOwnerAccountRequest());
        return request;
    }

    public static MWOwnerAccountRequest withDefaultMWOwnerAccountRequest() {
        return MWOwnerAccountRequest.builder()
                .personRoleId("123")
                .personId("123")
                .userDeviceId("123")
                .build();
    }

    public static UpdateBiometricsMWRequest withDefaultUpdateBiometricsMWRequest() {
        return UpdateBiometricsMWRequest.builder()
                .statusBiometric("S")
                .typeAuthentication("5")
                .tokenBiometric("sd12fs1df32s1df")
                .build();
    }

    public static LogoutMWRequest withDefaultLogoutMWRequest() {
        return LogoutMWRequest.builder()
                .ownerAccount(LogoutMWRequest.OwnerAccount.builder()
                        .personId("21321")
                        .userDeviceId("21321")
                        .personRoleId("21321")
                        .build())
                .deviceData(LogoutMWRequest.LogoutDataMW.builder()
                        .deviceId("fake")
                        .deviceIp("fake")
                        .deviceName("fake")
                        .geoPositionX("fake")
                        .geoPositionY("fake")
                        .appVersion("fake")
                        .build())
                .build();
    }

    public static LoginFactorMWRequest withDefaultLoginFactorMWRequest() {
        return LoginFactorMWRequest.builder()
                .factor("12345")
                .codeTypeAuthentication("2")
                .build();
    }
}