package bg.com.bo.bff.services;

import bg.com.bo.bff.mappings.services.LoginServiceMapper;
import bg.com.bo.bff.controllers.request.LoginRequest;
import bg.com.bo.bff.provider.interfaces.IJwtProvider;
import bg.com.bo.bff.provider.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.provider.LoginMiddlewareProvider;
import bg.com.bo.bff.services.v1.LoginService;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class LoginServicesTests {
    private static IJwtProvider jwtService;

    static ILoginMiddlewareProvider loginMiddlewareService;

    static LoginRequest loginRequest;

    static LoginService loginService;

    @BeforeAll
    public static void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setComplemento("a1");
        loginRequest.setPassword("1q01a");

        loginMiddlewareService = Mockito.mock(LoginMiddlewareProvider.class);
        jwtService = Mockito.mock(IJwtProvider.class);
    }

    @BeforeEach
    public void init() {
        loginService = new LoginService(loginMiddlewareService, jwtService, LoginServiceMapper.INSTANCE);
    }

//    @Test
//    void givenCorrectCredentialsWhenLoginThenReturnSuccess() throws IOException {
//        // Arrange
//        String personId = "1";
//        String statusCode = "SUCCESS";
//        String token = UUID.randomUUID().toString();
//
//        ClientToken clientToken = new ClientToken();
//        clientToken.setAccessToken(token);
//
//        LoginResult loginResult = new LoginResult();
//        loginResult.setStatusCode(statusCode);
//        loginResult.setPersonId(personId);
//
//        String accessToken = UUID.randomUUID().toString();
//        String refreshToken = UUID.randomUUID().toString();
//
//        TokenData tokenData = new TokenData();
//        tokenData.setAccessToken(accessToken);
//        tokenData.setRefreshToken(refreshToken);
//
//        Mockito.when(loginMiddlewareService.generateAccessToken()).thenReturn(clientToken);
//        Mockito.when(loginMiddlewareService.validateCredentials(token, loginRequest)).thenReturn(loginResult);
//        Mockito.when(jwtService.generateToken(loginResult.getPersonId(), UserRole.LOGGED_USER)).thenReturn(tokenData);
//
//        // Act
//        LoginResponse response = loginService.loginRequest(loginRequest);
//
//        // Assert
//        assert response.getStatusCode().equals("SUCCESS");
//        assert response.getData().getAccessToken().equals(accessToken);
//        assert response.getData().getRefreshToken().equals(refreshToken);
//        assert response.getData().getPersonId().equals(personId);
//    }
}
