package bg.com.bo.bff.application.dtos.request.login;

public class LoginRequestFixture {
    public static LoginRequest withDefaultLoginRequest() {
        return LoginRequest.builder()
                .type("2")
                .user("123456")
                .complement("test")
                .password("1234")
                .tokenBiometric("asdfgh")
                .build();
    }
    public static LoginRequest withDefaultLoginRequestBiometric() {
        return LoginRequest.builder()
                .type("5")
                .user("123456")
                .complement("test")
                .password("")
                .tokenBiometric("12345")
                .build();
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