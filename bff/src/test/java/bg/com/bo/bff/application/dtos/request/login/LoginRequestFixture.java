package bg.com.bo.bff.application.dtos.request.login;

public class LoginRequestFixture {
    public static LoginRequest withDefault() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setType("2");
        loginRequest.setUser("123456");
        loginRequest.setComplement("test");
        loginRequest.setPassword("1234");
        return loginRequest;
    }
}