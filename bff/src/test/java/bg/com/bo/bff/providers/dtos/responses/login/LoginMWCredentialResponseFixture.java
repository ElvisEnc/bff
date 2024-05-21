package bg.com.bo.bff.providers.dtos.responses.login;

public class LoginMWCredentialResponseFixture {
    public static LoginMWCredentialResponse withDefault() {
        LoginMWCredentialResponse response = new LoginMWCredentialResponse();
        response.setData(LoginMWCredentialDataResponseFixture.withDefault());
        return response;
    }
}