package bg.com.bo.bff.providers.dtos.response.login;

public class LoginMWCredentialResponseFixture {
    public static LoginCredentialMWResponse withDefault() {
        LoginCredentialMWResponse response = new LoginCredentialMWResponse();
        response.setData(LoginMWCredentialDataResponseFixture.withDefault());
        return response;
    }
}