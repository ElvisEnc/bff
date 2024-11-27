package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.login.LoginRequestFixture;
import bg.com.bo.bff.application.dtos.request.login.LogoutRequest;
import bg.com.bo.bff.application.dtos.request.login.RefreshSessionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.login.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.mappings.application.LoginMapper;
import bg.com.bo.bff.mappings.providers.login.ILoginMapper;
import bg.com.bo.bff.mappings.services.LoginServiceMapper;
import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.LoginCredentialMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.LoginMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.login.mw.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtPayload;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtRefresh;
import bg.com.bo.bff.providers.dtos.response.jwt.keycloak.CreateTokenServiceResponse;
import bg.com.bo.bff.providers.dtos.response.keycloak.IntrospectTokenKCResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.*;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.login.LoginMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class LoginServicesTests {
    @InjectMocks
    private LoginService loginService;
    @Mock
    private ILoginMiddlewareProvider provider;
    @Mock
    private ILoginMapper mapper;
    @Mock
    private LoginMapper loginMapper;
    @Mock
    private IJwtProvider jwtService;
    @Mock
    private LoginServiceMapper loginServiceMapper;

    // login
    @Test
    void shouldLoginSuccessfully() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        LoginResult loginResult = LoginResponseFixture.withDefaultLoginResult();
        LoginResponse loginResponse = LoginResponseFixture.withDefaultLoginResponse();
        LoginCredentialMWResponse resultExpected = LoginMWResponseFixture.withDefaultLoginCredentialMWResponse();
        LoginCredentialMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginCredentialMWRequest();
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        CreateTokenServiceResponse tokenResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        when(provider.validateCredentials(any())).thenReturn(resultExpected);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(loginResult);
        when(mapper.convertResponse(any(), any())).thenReturn(loginValidationResponse);
        when(mapper.mapperRequest((LoginRequest) any(), any())).thenReturn(mwRequest);
        when(loginMapper.convert(any())).thenReturn(loginResponse);

        // Act
        LoginResponse result = loginService.login(loginRequest);

        // Assert
        assertNotNull(result);
        verify(jwtService).generateToken(anyString(), anyString(), any());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .type("2")
                .user("123456")
                .password("")
                .build();

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest);
        });

        assertEquals(LoginMiddlewareError.PASSWORD_BLANK.getMessage(), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenBiometric() throws IOException {
        // Arrange
        LoginCredentialMWResponse resultExpected = LoginMWResponseFixture.withDefaultLoginCredentialMWResponse();
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequestBiometric();
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        CreateTokenServiceResponse tokenResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        LoginCredentialMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginCredentialMWRequest();
        LoginResult loginResult = LoginResponseFixture.withDefaultLoginResult();
        LoginResponse loginResponse = LoginResponseFixture.withDefaultLoginResponse();

        when(provider.validateCredentials(any())).thenReturn(resultExpected);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(new LoginResult());
        when(mapper.convertResponse(any(), any())).thenReturn(loginValidationResponse);
        when(mapper.mapperRequest((LoginRequest) any(), any())).thenReturn(mwRequest);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(loginResult);
        when(loginMapper.convert(any())).thenReturn(loginResponse);

        // Act
        LoginResponse result = loginService.login(loginRequest);

        // Assert
        assertNotNull(result);
        verify(jwtService).generateToken(anyString(), anyString(), any());
    }

    @Test
    void shouldThrowExceptionWhenTokenBiometricIsEmpty() {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequestBiometric();
        loginRequest.setTokenBiometric("");

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest);
        });

        // Assert
        assertEquals(LoginMiddlewareError.BIOMETRIC_TOKEN.getMessage(), exception.getMessage());
    }

    @Test
    void shouldLoginSuccessfullyDNI() throws IOException {
        // Arrange
        LoginCredentialMWResponse mwResponse = LoginMWResponseFixture.withDefaultLoginCredentialMWResponse();
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        loginRequest.setType("3");

        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        LoginFactorMWResponse loginFactorMWResponse = LoginMWResponseFixture.withDefaultLoginFactorMWResponse();
        CreateTokenServiceResponse tokenResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        LoginCredentialMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginCredentialMWRequest();
        LoginResult loginResult = LoginResponseFixture.withDefaultLoginResult();
        LoginResponse loginResponse = LoginResponseFixture.withDefaultLoginResponse();

        when(provider.validateFactorUser(any())).thenReturn(loginFactorMWResponse);
        when(provider.validateCredentials(any())).thenReturn(mwResponse);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);
        when(mapper.convertResponse(any(), any())).thenReturn(loginValidationResponse);
        when(mapper.mapperRequest((LoginRequest) any(), any())).thenReturn(mwRequest);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(loginResult);
        when(loginMapper.convert(any())).thenReturn(loginResponse);
        // Act
        LoginResponse result = loginService.login(loginRequest);

        // Assert
        assertNotNull(result);
        verify(jwtService).generateToken(anyString(), anyString(), Mockito.any());
    }

    @Test
    void shouldLoginSuccessfullyAlias() throws IOException {
        // Arrange
        LoginCredentialMWResponse mwResponse = LoginMWResponseFixture.withDefaultLoginCredentialMWResponse();
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        loginRequest.setType("1");
        loginRequest.setUser("alias");
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        LoginFactorMWResponse loginFactorMWResponse = LoginMWResponseFixture.withDefaultLoginFactorMWResponse();
        CreateTokenServiceResponse tokenResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        LoginCredentialMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginCredentialMWRequest();
        LoginResult loginResult = LoginResponseFixture.withDefaultLoginResult();
        LoginResponse loginResponse = LoginResponseFixture.withDefaultLoginResponse();

        when(provider.validateFactorUser(any())).thenReturn(loginFactorMWResponse);
        when(provider.validateCredentials(any())).thenReturn(mwResponse);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);
        when(mapper.convertResponse(any(), any())).thenReturn(loginValidationResponse);
        when(mapper.mapperRequest((LoginRequest) any(), any())).thenReturn(mwRequest);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(loginResult);
        when(loginMapper.convert(any())).thenReturn(loginResponse);
        // Act
        LoginResponse result = loginService.login(loginRequest);

        // Assert
        assertNotNull(result);
        verify(jwtService).generateToken(anyString(), anyString(), any());
    }

    @Test
    void shouldLoginSuccessfullyInvalidData() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        loginRequest.setType("6");

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest);
        });

        // Assert
        assertEquals(LoginMiddlewareError.INVALID_AUTH_TYPE.getMessage(), exception.getMessage());
    }

    @Test
    void shouldLoginSuccessfullyFailed() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        loginRequest.setPassword("");

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest);
        });

        // Assert
        assertEquals(LoginMiddlewareError.PASSWORD_BLANK.getMessage(), exception.getMessage());
    }

    @Test
    void shouldLoginBadRequestBiometricTypeLogin() {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        loginRequest.setType("5");
        loginRequest.setTokenBiometric("");

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest);
        });

        // Assert
        assertEquals(LoginMiddlewareError.BIOMETRIC_TOKEN.getMessage(), exception.getMessage());
    }

    // refreshSession
    @Test
    void shouldRefreshSessionSuccessfully() {
        // Arrange
        RefreshSessionRequest refreshSessionRequest = LoginRequestFixture.withDefaultRefreshSessionRequest();
        JwtRefresh jwtRefresh = LoginResponseFixture.withDefaultJwtRefresh();
        String accessToken = UUID.randomUUID().toString();
        String bearerToken = "Bearer " + accessToken;
        CreateTokenServiceResponse tokenServiceResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        RefreshSessionResult refreshSessionResult = LoginResponseFixture.withDefaultRefreshSessionResult();
        when(jwtService.parseJwtRefresh(anyString())).thenReturn(jwtRefresh);
        when(jwtService.refreshToken(anyString())).thenReturn(tokenServiceResponse);
        when(jwtService.revokeAccessToken(accessToken)).thenReturn(true);
        when(loginServiceMapper.convert(any(), any())).thenReturn(refreshSessionResult);
        when(loginServiceMapper.convert(any())).thenReturn(LoginResponseFixture.withDefaultTokenDataResponse());

        // Act
        TokenDataResponse response = loginService.refreshSession("123456", refreshSessionRequest, bearerToken);

        // Assert
        assertNotNull(response);
        verify(jwtService).refreshToken(anyString());
    }

    @Test
    void shouldThrowExceptionWhenPersonIdDoesNotMatch() {
        // Arrange
        RefreshSessionRequest refreshSessionRequest = LoginRequestFixture.withDefaultRefreshSessionRequest();
        String accessToken = UUID.randomUUID().toString();
        JwtRefresh jwtRefresh = new JwtRefresh();
        JwtPayload jwtPayload = new JwtPayload();
        jwtPayload.setPersonId("123");
        jwtRefresh.setPayload(jwtPayload);
        when(jwtService.parseJwtRefresh(anyString())).thenReturn(jwtRefresh);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.refreshSession("123456", refreshSessionRequest, accessToken);
        });

        // Assert
        assertEquals(DefaultMiddlewareError.INVALID_ACCESS_JWT.getMessage(), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenStatusCodeInvalidData() {
        // Arrange
        RefreshSessionRequest refreshSessionRequest = LoginRequestFixture.withDefaultRefreshSessionRequest();
        String accessToken = UUID.randomUUID().toString();
        String bearerToken = "Bearer " + accessToken;
        JwtRefresh jwtRefresh = LoginResponseFixture.withDefaultJwtRefresh();
        CreateTokenServiceResponse tokenServiceResponse = new CreateTokenServiceResponse();
        tokenServiceResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.INVALID_DATA);
        when(jwtService.parseJwtRefresh(Mockito.anyString())).thenReturn(jwtRefresh);
        when(jwtService.refreshToken(anyString())).thenReturn(tokenServiceResponse);
        when(jwtService.revokeAccessToken(accessToken)).thenReturn(true);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.refreshSession("123456", refreshSessionRequest, bearerToken);
        });

        // Assert
        assertEquals(DefaultMiddlewareError.INVALID_ACCESS_JWT.getMessage(), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenStatusCodeDefault() {
        // Arrange
        RefreshSessionRequest refreshSessionRequest = LoginRequestFixture.withDefaultRefreshSessionRequest();
        String accessToken = UUID.randomUUID().toString();
        String bearerToken = "Bearer " + accessToken;
        JwtRefresh jwtRefresh = LoginResponseFixture.withDefaultJwtRefresh();
        CreateTokenServiceResponse tokenServiceResponse = new CreateTokenServiceResponse();
        tokenServiceResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.FAILED);
        when(jwtService.parseJwtRefresh(Mockito.anyString())).thenReturn(jwtRefresh);
        when(jwtService.refreshToken(anyString())).thenReturn(tokenServiceResponse);
        when(jwtService.revokeAccessToken(accessToken)).thenReturn(true);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.refreshSession("123456", refreshSessionRequest, bearerToken);
        });

        // Assert
        assertEquals(DefaultMiddlewareError.AUTHENTICATION_FILTER_FAILURE.getMessage(), exception.getMessage());
    }

    // Logout
    @Test
    void shouldLogoutSuccessfully() throws IOException {
        // Arrange
        LogoutMWResponse mwResponse = LoginMWResponseFixture.withDefaultLogoutMWResponse();
        LogoutMWRequest mwRequest = LoginMWRequestFixture.withDefaultLogoutMWRequest();
        LogoutRequest logoutRequest = LoginRequestFixture.withDefaultLogoutRequest();
        IntrospectTokenKCResponse introspectTokenKCResponse = new IntrospectTokenKCResponse(IntrospectTokenKCResponse.Result.SUCCESS, true);
        when(jwtService.introspect(anyString())).thenReturn(introspectTokenKCResponse);
        when(jwtService.revokeAccessToken(anyString())).thenReturn(true);
        when(jwtService.revokeRefreshToken(anyString())).thenReturn(true);
        when(provider.logout(any())).thenReturn(mwResponse);
        when(mapper.mapperRequest((String) any(), (String) any())).thenReturn(mwRequest);

        // Act
        GenericResponse response = loginService.logout("21321", "Bearer some_access_token", "21321", logoutRequest);

        // Assert
        assertNotNull(response);
        verify(jwtService).introspect(anyString());
        verify(jwtService).revokeAccessToken(anyString());
        verify(jwtService).revokeRefreshToken(anyString());
        verify(provider).logout(any());
    }

    @Test
    void shouldThrowExceptionWhenRevokeAccessToken() {
        // Arrange
        LogoutRequest logoutRequest = LoginRequestFixture.withDefaultLogoutRequest();
        IntrospectTokenKCResponse introspectTokenKCResponse = new IntrospectTokenKCResponse(IntrospectTokenKCResponse.Result.SUCCESS, true);
        when(jwtService.introspect(anyString())).thenReturn(introspectTokenKCResponse);
        when(jwtService.revokeAccessToken(anyString())).thenReturn(true);
        when(jwtService.revokeRefreshToken(anyString())).thenReturn(false);

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                loginService.logout("personId", "Bearer some_access_token", "personRoleId", logoutRequest)
        );

        // Assert
        assertEquals(AppError.KEY_CLOAK_ERROR.getCode(), exception.getCode());
        verify(jwtService).revokeAccessToken(anyString());
        verify(jwtService).revokeRefreshToken(anyString());
        verify(jwtService).introspect(anyString());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsExpiredOrInvalid() {
        // Arrange
        LogoutRequest logoutRequest = LoginRequestFixture.withDefaultLogoutRequest();
        IntrospectTokenKCResponse introspectResponse = new IntrospectTokenKCResponse(IntrospectTokenKCResponse.Result.EXPIRED_TOKEN, false);
        when(jwtService.introspect(anyString())).thenReturn(introspectResponse);

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                loginService.logout("personId", "Bearer some_access_token", "personRoleId", logoutRequest)
        );

        // Assert
        assertEquals(DefaultMiddlewareError.INVALID_ACCESS_JWT.getCode(), exception.getCode());
        verify(jwtService).introspect(anyString());
    }

    // DeviceEnrolled
    @Test
    void shouldReturnEnrolledStatusWhenDeviceIsEnrolled() throws IOException {
        // Arrange
        DeviceEnrollmentMWResponse mockMWResponse = LoginResponseFixture.withDefaultDeviceEnrollmentMWResponse();
        DeviceEnrollmentResponse mockResponse = LoginResponseFixture.withDefaultDeviceEnrollmentResponse();
        when(provider.makeValidateDevice()).thenReturn(mockMWResponse);
        when(mapper.convertResponse((DeviceEnrollmentMWResponse) any())).thenReturn(mockResponse);

        // Act
        DeviceEnrollmentResponse response = loginService.validation();

        // Assert
        assertNotNull(response);
        verify(provider).makeValidateDevice();
    }

    @Test
    void shouldReturnNotEnrolledStatusWhenDeviceIsNotEnrolled() throws IOException {
        // Arrange
        DeviceEnrollmentMWResponse mockMWResponse = LoginResponseFixture.withDefaultDeviceEnrollmentMWResponseNotEnrolled();
        DeviceEnrollmentResponse mockResponse = LoginResponseFixture.withDefaultDeviceEnrollmentResponse();
        mockResponse.setStatusCode(2);
        when(provider.makeValidateDevice()).thenReturn(mockMWResponse);
        when(mapper.convertResponse((DeviceEnrollmentMWResponse) any())).thenReturn(mockResponse);

        // Act
        DeviceEnrollmentResponse response = loginService.validation();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getStatusCode());
        verify(provider).makeValidateDevice();
    }
}
