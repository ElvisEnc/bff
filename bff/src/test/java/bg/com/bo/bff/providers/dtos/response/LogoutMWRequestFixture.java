package bg.com.bo.bff.providers.dtos.response;

import bg.com.bo.bff.providers.dtos.request.login.LogoutMWRequest;

public class LogoutMWRequestFixture {
    public static LogoutMWRequest withDefault() {
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