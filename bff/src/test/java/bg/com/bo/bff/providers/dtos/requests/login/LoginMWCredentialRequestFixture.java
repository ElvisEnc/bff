package bg.com.bo.bff.providers.dtos.requests.login;

public class LoginMWCredentialRequestFixture {
    public static LoginMWCredentialRequest withDefault() {
        return new LoginMWCredentialRequest("123", "123", LoginMWCredendialDeviceRequestFixture.withDefault(), "123", "123", "123", "123");
    }
}