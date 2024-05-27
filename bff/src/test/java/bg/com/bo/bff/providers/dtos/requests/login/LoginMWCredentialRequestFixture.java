package bg.com.bo.bff.providers.dtos.requests.login;

public class LoginMWCredentialRequestFixture {
    public static LoginCredentialMWRequest withDefault() {
        return new LoginCredentialMWRequest("123", "123",  "123", "123", "123");
    }
}