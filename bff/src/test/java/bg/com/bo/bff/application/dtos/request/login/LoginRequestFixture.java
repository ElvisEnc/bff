package bg.com.bo.bff.application.dtos.request.login;

public class LoginRequestFixture {
    public static LoginRequest withDefaultLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setType("2");
        loginRequest.setUser("123456");
        loginRequest.setComplement("test");
        loginRequest.setPassword("1234");
        return loginRequest;
    }

    public static RefreshSessionRequest withDefaultRefreshSessionRequest() {
        RefreshSessionRequest refreshSessionRequest = new RefreshSessionRequest();
        refreshSessionRequest.setRefreshToken("some_refresh_token");
        return refreshSessionRequest;
    }

    public static LogoutRequest withDefaultLogoutRequest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRefreshToken("some_refresh_token");
        return logoutRequest;
    }
}