package bg.com.bo.bff.providers.dtos.requests.login;

public class LoginMWCredendialDeviceRequestFixture {
    public static LoginMWCredendialDeviceRequest withDefault() {
        return LoginMWCredendialDeviceRequest.builder()
                .deviceIp("123")
                .deviceId("123")
                .deviceName("123")
                .geoPositionX("123")
                .geoPositionY("123")
                .build();
    }
}