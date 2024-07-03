package bg.com.bo.bff.providers.dtos.request.login.mw;

public class LoginMWRequestFixture {
    public static LoginCredentialMWRequest withDefaultLoginCredentialMWRequest() {
        return new LoginCredentialMWRequest("123", "123", "123", "123", "123");
    }

    public static ChangePasswordMWRequest withDefaultChangePasswordMWRequest() {
        ChangePasswordMWRequest request = new ChangePasswordMWRequest();
        request.setPreviousPassword("1233");
        request.setNewPassword("1234");
        request.setOwnerAccount(withDefaultMWOwnerAccountRequest());
        return request;
    }

    public static MWOwnerAccountRequest withDefaultMWOwnerAccountRequest() {
        MWOwnerAccountRequest ownerAccountRequest = new MWOwnerAccountRequest();
        ownerAccountRequest.setPersonId("123");
        ownerAccountRequest.setPersonRoleId("123");
        ownerAccountRequest.setUserDeviceId("123");
        return ownerAccountRequest;
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

}