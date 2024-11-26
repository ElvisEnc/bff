package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.login.LoginRequestFixture;
import bg.com.bo.bff.application.dtos.request.login.LogoutRequest;
import bg.com.bo.bff.application.dtos.request.login.RefreshSessionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.login.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.mappings.services.LoginServiceMapper;
import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtPayload;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtRefresh;
import bg.com.bo.bff.providers.dtos.response.jwt.keycloak.CreateTokenServiceResponse;
import bg.com.bo.bff.providers.dtos.response.keycloak.IntrospectTokenKCResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginFactorMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.response.RefreshControllerErrorResponse;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class LoginServicesTests {
    @InjectMocks
    private LoginService loginService;
    @Mock
    private ILoginMiddlewareProvider provider;
    @Mock
    private IJwtProvider jwtService;
    @Mock
    private LoginServiceMapper loginServiceMapper;
    Map<String, String> parameters;
    String personId = "123456";

    @BeforeEach
    void setUp() {
        parameters = new HashMap<>();
        parameters.put("json-data", "IntcIm5hbWVcIjpcIlwiLFwicGFnaW5hdGlvblwiOntcInBhZ2VcIjoxLFwicGFnZVNpemVcIjoxMH19Ig==");
    }

    // login
    @Test
    void shouldLoginSuccessfully() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        CreateTokenServiceResponse tokenResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        when(provider.validateCredentials(any(), any(), any())).thenReturn(loginValidationResponse);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(new LoginResult());

        // Act
        LoginResult result = loginService.login(loginRequest, parameters);

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
            loginService.login(loginRequest, parameters);
        });

        assertEquals("Password no debe estar vacío", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenBiometric() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .type("5")
                .user("123456")
                .password("")
                .tokenBiometric("1234")
                .build();
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        CreateTokenServiceResponse tokenResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        when(provider.validateCredentials(any(), any(), any())).thenReturn(loginValidationResponse);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(new LoginResult());

        // Act
        LoginResult result = loginService.login(loginRequest, parameters);

        // Assert
        assertNotNull(result);
        verify(jwtService).generateToken(anyString(), anyString(), any());
    }

    @Test
    void shouldThrowExceptionWhenTokenBiometricIsEmpty() {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .type("4")
                .user("123456")
                .tokenBiometric("")
                .build();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest, parameters);
        });

        // Assert
        assertEquals("Token Biometrico no debe estar vacío", exception.getMessage());
    }

    @Test
    void shouldLoginSuccessfullyDNI() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .type("3")
                .user("123456")
                .password("1234")
                .build();
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        LoginFactorMWResponse loginFactorMWResponse = LoginMWResponseFixture.withDefaultLoginFactorMWResponse();
        CreateTokenServiceResponse tokenResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        when(provider.validateFactorUser(any(), any())).thenReturn(loginFactorMWResponse);
        when(provider.validateCredentials(any(), any(), any())).thenReturn(loginValidationResponse);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(new LoginResult());

        // Act
        LoginResult result = loginService.login(loginRequest, parameters);

        // Assert
        assertNotNull(result);
        verify(jwtService).generateToken(anyString(), anyString(), Mockito.any());
    }

    @Test
    void shouldLoginSuccessfullyAlias() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .type("1")
                .user("alias")
                .password("1234")
                .build();
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        LoginFactorMWResponse loginFactorMWResponse = LoginMWResponseFixture.withDefaultLoginFactorMWResponse();
        CreateTokenServiceResponse tokenResponse = LoginResponseFixture.withDefaultCreateTokenServiceResponse();
        when(provider.validateFactorUser(any(), any())).thenReturn(loginFactorMWResponse);
        when(provider.validateCredentials(any(), any(), any())).thenReturn(loginValidationResponse);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);
        when(loginServiceMapper.convert(any(), any(), any())).thenReturn(new LoginResult());

        // Act
        LoginResult result = loginService.login(loginRequest, parameters);

        // Assert
        assertNotNull(result);
        verify(jwtService).generateToken(anyString(), anyString(), any());
    }

    @Test
    void shouldLoginSuccessfullyInvalidData() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        CreateTokenServiceResponse tokenResponse = new CreateTokenServiceResponse();
        tokenResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.INVALID_DATA);
        when(provider.validateCredentials(any(), any(), any())).thenReturn(loginValidationResponse);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest, parameters);
        });

        // Assert
        assertEquals("Estado no valido para Login. INVALID_DATA", exception.getMessage());
    }

    @Test
    void shouldLoginSuccessfullyFailed() throws IOException {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        LoginValidationServiceResponse loginValidationResponse = LoginResponseFixture.withDefaultLoginValidationServiceResponse();
        CreateTokenServiceResponse tokenResponse = new CreateTokenServiceResponse();
        tokenResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.FAILED);
        when(provider.validateCredentials(any(), any(), any())).thenReturn(loginValidationResponse);
        when(jwtService.generateToken(anyString(), anyString(), any())).thenReturn(tokenResponse);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest, parameters);
        });

        // Assert
        assertEquals("Error interno", exception.getMessage());
    }

    @Test
    void shouldLoginBadRequestJsonDataNull() {
        // Arrange
        LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
        Map<String, String> parameters = new HashMap<>();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest, parameters);
        });

        // Assert
        assertEquals("El header json-data es requerido", exception.getMessage());
    }

    @Test
    void shouldLoginBadRequestPersonCodeAlphanumeric() {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .type("2")
                .user("123abc")
                .password("1234")
                .build();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginService.login(loginRequest, parameters);
        });

        // Assert
        assertEquals("Se espera solo números en User", exception.getMessage());
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
        when(loginServiceMapper.convert(any())).thenReturn(new TokenDataResponse());

        // Act
        TokenDataResponse response = loginService.refreshSession(personId, refreshSessionRequest, bearerToken);

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
        HandledException exception = assertThrows(HandledException.class, () -> {
            loginService.refreshSession(personId, refreshSessionRequest, accessToken);
        });

        // Assert
        assertEquals(RefreshControllerErrorResponse.INVALID_DATA.getDescription(), exception.getMessage());
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
        HandledException exception = assertThrows(HandledException.class, () -> {
            loginService.refreshSession(personId, refreshSessionRequest, bearerToken);
        });

        // Assert
        assertEquals(RefreshControllerErrorResponse.INVALID_DATA.getDescription(), exception.getMessage());
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
        HandledException exception = assertThrows(HandledException.class, () -> {
            loginService.refreshSession(personId, refreshSessionRequest, bearerToken);
        });

        // Assert
        assertEquals("Internal server error.", exception.getMessage());
    }

    // Logout
    @Test
    void shouldLogoutSuccessfully() throws IOException {
        // Arrange
        LogoutRequest logoutRequest = LoginRequestFixture.withDefaultLogoutRequest();
        IntrospectTokenKCResponse introspectTokenKCResponse = new IntrospectTokenKCResponse(IntrospectTokenKCResponse.Result.SUCCESS, true);
        when(jwtService.introspect(anyString())).thenReturn(introspectTokenKCResponse);
        when(jwtService.revokeAccessToken(anyString())).thenReturn(true);
        when(jwtService.revokeRefreshToken(anyString())).thenReturn(true);
        when(provider.logout(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any())).thenReturn(new GenericResponse());

        // Act
        GenericResponse response = loginService.logout("deviceId", "deviceIp", "deviceName", "geoX", "geoY", "appVersion", "personId", "userDeviceId", "personRoleId", "Bearer some_access_token", logoutRequest);

        // Assert
        assertNotNull(response);
        verify(jwtService).introspect(anyString());
        verify(jwtService).revokeAccessToken(anyString());
        verify(jwtService).revokeRefreshToken(anyString());
        verify(provider).logout(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any());
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
                loginService.logout("deviceId", "deviceIp", "deviceName", "geoX", "geoY", "appVersion", "personId", "userDeviceId", "personRoleId", "Bearer some_access_token", logoutRequest)
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
                loginService.logout("deviceId", "deviceIp", "deviceName", "geoX", "geoY", "appVersion", "personId", "userDeviceId", "personRoleId", "Bearer some_access_token", logoutRequest)
        );

        // Assert
        assertEquals(AppError.INVALID_REFRESH_TOKEN.getCode(), exception.getCode());
        verify(jwtService).introspect(anyString());
    }

    // DeviceEnrolled
    @Test
    void shouldReturnEnrolledStatusWhenDeviceIsEnrolled() throws IOException {
        // Arrange
        DeviceEnrollmentMWResponse mockResponse = LoginResponseFixture.withDefaultDeviceEnrollmentMWResponse();
        when(provider.makeValidateDevice(anyMap())).thenReturn(mockResponse);

        // Act
        DeviceEnrollmentResponse response = loginService.validation(parameters);

        // Assert
        assertNotNull(response);
        verify(provider).makeValidateDevice(anyMap());
    }

    @Test
    void shouldReturnNotEnrolledStatusWhenDeviceIsNotEnrolled() throws IOException {
        // Arrange
        DeviceEnrollmentMWResponse mockResponse = LoginResponseFixture.withDefaultDeviceEnrollmentMWResponseNotEnrolled();
        when(provider.makeValidateDevice(anyMap())).thenReturn(mockResponse);

        // Act
        DeviceEnrollmentResponse response = loginService.validation(parameters);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getStatusCode());
        verify(provider).makeValidateDevice(anyMap());
    }
}
