package bg.com.bo.bff.application.dtos.request;

public class LoginRequestFixture {
    public static LoginRequest withDefault() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setType("test");
        loginRequest.setUser("test");
        loginRequest.setComplement("test");
        loginRequest.setPassword("test");
        loginRequest.setAppVersion("test");
        loginRequest.setDeviceIdentification(DeviceFixture.withDefault());
        return loginRequest;
    }
}