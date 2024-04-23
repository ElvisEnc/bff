package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.LogoutRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.mappings.services.LoginServiceMapper;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.providers.dtos.requests.login.LogoutMWRequest;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.implementations.LoginMiddlewareProvider;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.IOException;

import static org.mockito.Mockito.*;

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
        loginRequest.setComplement("a1");
        loginRequest.setPassword("1q01a");

        loginMiddlewareService = Mockito.mock(LoginMiddlewareProvider.class);
        jwtService = Mockito.mock(IJwtProvider.class);
    }

    @BeforeEach
    public void init() {
        loginService = new LoginService(loginMiddlewareService, jwtService, LoginServiceMapper.INSTANCE);
    }

    @Test
    void giveTokensWhenLogoutThenReturnSuccess() throws IOException {
        // Arrange
        GenericResponse expect = new GenericResponse();
        expect.setCode("SUCCESS");
        expect.setMessage("SUCCESS");
        String generic = "asdf123qwer123zxc";
        LogoutRequest requestMock = new LogoutRequest();
        requestMock.setRefreshToken("ss4f32s4d3f54s3d5f");
        when(jwtService.revokeAccessToken(anyString())).thenReturn(true);
        when(jwtService.revokeRefreshToken(any(LogoutRequest.class))).thenReturn(true);
        when(loginMiddlewareService.logout(any(), any(), any(), any(), any(), any(), any(LogoutMWRequest.class))).thenReturn(expect);

        // Act
        GenericResponse response = loginService.logout(generic, generic, generic, generic, generic, generic, generic, generic, generic, generic, requestMock);

        // Assert
        assertEquals("SUCCESS", response.getCode());
        assertNotNull(response);
        verify(loginMiddlewareService).logout(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(LogoutMWRequest.class));
    }

    @Test
    void giveLogoutFailureWhenTokenRevokeFailsThenReturnException() throws IOException {
        //Arrange
        String generic = "asdf123qwer123zxc";
        when(jwtService.revokeAccessToken(anyString())).thenReturn(false);
        when(jwtService.revokeRefreshToken(any(LogoutRequest.class))).thenReturn(true);
        GenericException expectedResponse = new GenericException(AppError.KEY_CLOAK_ERROR.getMessage(), AppError.KEY_CLOAK_ERROR.getHttpCode(), AppError.KEY_CLOAK_ERROR.getCode());

        //Act
        GenericException exception = assertThrows(GenericException.class, () -> loginService.logout(generic, generic, generic, generic, generic, generic, generic, generic, generic, generic, new LogoutRequest()));

        //Assert
        assertEquals(expectedResponse.getCode(), (exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
        verify(loginMiddlewareService, never()).logout(any(), any(), any(), any(), any(), any(), any());
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
