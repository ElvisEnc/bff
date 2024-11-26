package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.login.LoginRequestFixture;
import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.user.UserRequestFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.providers.login.LoginMapper;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.application.dtos.response.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginMWResponseFixture;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.login.mw.LoginMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.login.mw.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.UpdateBiometricsMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorMiddlewareProvider;
import bg.com.bo.bff.mappings.providers.login.ILoginMapper;
import bg.com.bo.bff.mappings.providers.login.LoginMWMapper;
import bg.com.bo.bff.providers.dtos.response.login.mw.*;
import bg.com.bo.bff.providers.dtos.response.personal.information.UserContactResponse;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class LoginMiddlewareProviderTests {
    private LoginMiddlewareProvider loginMiddlewareProvider;
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactoryMock;
    LoginMWMapper loginMWMapper;
    @Spy
    private ILoginMapper mapper = new LoginMapper();
    private ClientToken clientTokenMock;
    private ErrorMiddlewareProvider errorMiddlewareProvider;
    private static final String DEVICE_ID = "42ebffbd7c30307d";
    private static final String DEVICE_NAME = "Android";
    private static final String GEO_POSITION_X = "12.265656";
    private static final String GEO_POSITION_Y = "12.454545";
    private static final String APP_VERSION = "1.0.0";
    private static final String JSON_DATA = "IntcIm5hbWVcIjpcIlwiLFwicGFnaW5hdGlvblwiOntcInBhZ2VcIjoxLFwicGFnZVNpemVcIjoxMH19Ig==";
    Map<String, String> map;
    LoginRequest loginRequest = LoginRequestFixture.withDefaultLoginRequest();
    UpdateBiometricsRequest updateBiometricsRequest = UserRequestFixture.withDefaultUpdateBiometricsRequest();
    UpdateBiometricsMWRequest updateBiometricsMWRequest = LoginMWRequestFixture.withDefaultUpdateBiometricsMWRequest();

    @BeforeEach
    void init() {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = Mockito.mock(MiddlewareConfig.class);
        loginMWMapper = LoginMWMapper.INSTANCE;
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());

        loginMiddlewareProvider = new LoginMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, loginMWMapper, mapper);

        clientTokenMock = new ClientToken();
        clientTokenMock.setAccessToken("34sd3f243sdf43");

        map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), DEVICE_ID,
                DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME,
                DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X,
                DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y,
                DeviceMW.APP_VERSION.getCode(), APP_VERSION,
                "json-data", JSON_DATA
        );

        setField(loginMiddlewareProvider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    @DisplayName("Get user data on valid factor user.")
    void givenCorrectFactorUserWhenValidateFactorUserThenUserData() throws IOException {
        // Arrange
        LoginFactorMWResponse expectedResponse = new LoginFactorMWResponse();
        LoginFactorData loginMWFactorDataResponse = new LoginFactorData();
        loginMWFactorDataResponse.setPersonId("123");
        expectedResponse.setData(loginMWFactorDataResponse);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        stubFor(post(anyUrl())
                .willReturn(okJson(new ObjectMapper().writeValueAsString(expectedResponse))));

        // Act
        LoginFactorMWResponse actualResponse = loginMiddlewareProvider.validateFactorUser(loginRequest, map);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getData().getPersonId(), actualResponse.getData().getPersonId());
    }

    @ParameterizedTest
    @EnumSource(value = AppError.class, names = {"MDWLM_007"})
    @DisplayName("Getting exception given mapped MW error on ValidateFactorUse.")
    void givenMappedErrorWhenValidateFactorUserThenReturnGenericException(AppError error) throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(error.getCodeMiddleware())
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse().withStatus(400).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.validateFactorUser(loginRequest, map);
        });

        // Assert
        assertEquals(exception.getCode(), error.getCode());
        assertEquals(exception.getMessage(), error.getMessage());
    }

    @Test
    @DisplayName("Getting default exception given MW error on ValidateFactorUse.")
    void givenErrorWhenValidateFactorUserDefaultGenericException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.validateFactorUser(loginRequest, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on validate credentials.")
    void givePersonCodeWhenValidateCredentialsThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(LoginMWResponseFixture.withDefaultLoginCredentialMWResponse());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoginValidationServiceResponse response = loginMiddlewareProvider.validateCredentials(LoginRequestFixture.withDefaultLoginRequest(), LoginMWResponseFixture.withDefaultLoginFactorData(), map);

        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("Getting exception given mapped MW error on validate credentials.")
    void giveUnexpectedErrorOccursWhenValidateCredentialsThenGenericException() throws IOException {
        // Arrange
        LoginFactorData data = LoginMWResponseFixture.withDefaultLoginFactorData();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse().withStatus(400).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.validateCredentials(loginRequest, data, map);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    @DisplayName("Getting default exception given mapped MW error on validate credentials.")
    void giveErrorWhenValidateCredentialsThenRuntimeException() throws IOException {
        // Arrange
        LoginFactorData data = LoginMWResponseFixture.withDefaultLoginFactorData();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.validateCredentials(loginRequest, data, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on logout.")
    void givenValidDataWhenLogoutThenExpectedResponse() throws IOException {
        //Arrange
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("SUCCESS");
        String generic = "sdfgfsdfs";
        String jsonResponse = new ObjectMapper().writeValueAsString(expectedResponse);

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        GenericResponse response = loginMiddlewareProvider.logout(generic, generic, generic, generic, generic, generic, LoginMWRequestFixture.withDefaultLogoutMWRequest());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getCode(), response.getCode());
    }

    @Test
    @DisplayName("Getting default exception given MW error on logout.")
    void givenInvalidDataWhenLogoutThenReturnException() throws IOException {
        //Arrange
        LogoutMWRequest mwRequest = LoginMWRequestFixture.withDefaultLogoutMWRequest();
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("500")
                        .description("500")
                        .build()))
                .build();
        GenericException expectedResponse = new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        stubFor(post(anyUrl()).willReturn(aResponse()
                .withStatus(500)
                .withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        //Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.logout("deviceId", "deviceIp", "deviceName", "geoX", "geoY", "appVersion", mwRequest);
        });

        //Assert
        assertEquals(expectedResponse.getCode(), (exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
        verify(1, postRequestedFor(anyUrl()));
    }

    @Test
    @DisplayName("Getting exception given mapped MW error on logout.")
    void givenErrorWhenLogoutThenReturnException() throws IOException {
        // Arrange
        LogoutMWRequest mwRequest = LoginMWRequestFixture.withDefaultLogoutMWRequest();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.logout("deviceId", "deviceIp", "deviceName", "geoX", "geoY", "appVersion", mwRequest);
        });

        // Assert
        assertEquals(DefaultMiddlewareError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(DefaultMiddlewareError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on change password.")
    void givenValidDataWhenChangePasswordThenExpectedResponse() throws IOException {
        //Arrange
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("SUCCESS");
        String jsonResponse = Util.objectToString(expectedResponse);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        stubFor(put(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        GenericResponse response = loginMiddlewareProvider.changePassword("123", "123", UserRequestFixture.withDefaultChangePasswordRequest(), new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getCode(), response.getCode());
    }

    @ParameterizedTest
    @EnumSource(value = ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.class, names = {"MDWPWD_0099"})
    @DisplayName("Getting exception given mapped MW error on change password.")
    void givenMappedErrorWhenChangePasswordThenReturnGenericException(IErrorResponse error) throws IOException {
        // Arrange
        ChangePasswordRequest request = UserRequestFixture.withDefaultChangePasswordRequest();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(error.getMwCode())
                        .build()))
                .build();
        stubFor(put(anyUrl()).willReturn(aResponse().withStatus(406).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        HandledException exception = assertThrows(HandledException.class, () ->
                loginMiddlewareProvider.changePassword("123", "123", request, map)
        );

        // Assert
        assertEquals(error.getCode(), exception.getCode());
        assertEquals(error.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Getting default exception given MW error on change password.")
    void givenInvalidDataWhenChangePasswordThenReturnException() throws IOException {
        //Arrange
        ChangePasswordRequest request = UserRequestFixture.withDefaultChangePasswordRequest();
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                loginMiddlewareProvider.changePassword("123", "123", request, map)
        );

        //Assert
        assertEquals(DefaultMiddlewareError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(DefaultMiddlewareError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on validate device.")
    void givenUniqueIdWhenValidateDeviceThenReturnSuccessfully() throws IOException {
        // Arrange
        DeviceEnrollmentMWResponse expectedResponse = new DeviceEnrollmentMWResponse();
        expectedResponse.setStatusCode("enrolled");
        expectedResponse.setPersonId("123");

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);

        stubFor(get(anyUrl()).willReturn(okJson(new ObjectMapper().writeValueAsString(ApiDataResponse.builder().data(expectedResponse).build()))));

        // Act
        DeviceEnrollmentMWResponse actualResponse = loginMiddlewareProvider.makeValidateDevice(new HashMap<>());

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getPersonId(), actualResponse.getPersonId());
    }

    @ParameterizedTest
    @EnumSource(value = AppError.class, names = {"MDWPGL_400"})
    @DisplayName("Getting exception given mapped MW error on validate device.")
    void givenMappedErrorWhenValidateDeviceThenReturnMappedGenericException(AppError error) throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(error.getCodeMiddleware())
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse().withStatus(400).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                loginMiddlewareProvider.makeValidateDevice(map)
        );

        // Assert
        assertEquals(AppError.MDWPGL_400.getMessage(), exception.getMessage());
        assertEquals(AppError.MDWPGL_400.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting default exception given MW error on validate device.")
    void givenErrorWhenValidateDeviceThenReturnMappedDefaultException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(AppError.DEFAULT.getCodeMiddleware())
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse().withStatus(400).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                loginMiddlewareProvider.makeValidateDevice(map)
        );

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting middleware exception given MW error on validate device.")
    void givenErrorWhenValidateDeviceThenReturnMappedMiddlwareException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);

        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(
                        ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                                .code(AppError.MDWRLIB_0003.getCodeMiddleware())
                                .build()))
                .build();

        stubFor(get(anyUrl())
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        DeviceEnrollmentMWResponse response = loginMiddlewareProvider.makeValidateDevice(map);

        // Assert
        assertNotNull(response);
        assertEquals("NOT_ENROLLED", response.getStatusCode());
    }

    @Test
    @DisplayName("Getting default exception given MW error on validate device.")
    void givenExceptionWhenValidateDeviceThenThrowGenericException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.makeValidateDevice(map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
        assertEquals(AppError.DEFAULT.getCode(), exception.getCode());
    }

    @Test
    @DisplayName("Getting generic exception response on validate device.")
    void givenGenericExceptionWhenValidateDeviceThenRethrowGenericException() throws IOException {
        // Arrange
        GenericException simulatedException = new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(simulatedException);
        Mockito.reset(httpClientFactoryMock);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.makeValidateDevice(map);
        });

        // Verify
        assertEquals(simulatedException.getMessage(), exception.getMessage());
        assertEquals(simulatedException.getStatus(), exception.getStatus());
        assertEquals(simulatedException.getCode(), exception.getCode());
    }

    @Test
    @DisplayName("Getting expected response on get biometrics.")
    void givePersonIdWhenGetBiometricsThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(LoginMWResponseFixture.withDefaultBiometricStatusMWResponse());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        BiometricStatusMWResponse response = loginMiddlewareProvider.getBiometricsMW(123, map);

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), LoginMWResponseFixture.withDefaultBiometricStatusMWResponse().getData());
    }

    @Test
    @DisplayName("Getting exception given mapped MW error on get biometrics.")
    void giveUnexpectedErrorOccursWhenGetBiometricsThenGenericException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse()
                .withStatus(400)
                .withBody(Util.objectToString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                loginMiddlewareProvider.getBiometricsMW(123, map)
        );

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    @DisplayName("Getting default exception given MW error on get biometrics.")
    void giveErrorWhenGetBiometricsThenRuntimeException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                loginMiddlewareProvider.getBiometricsMW(123, map)
        );

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on update biometrics.")
    void givePersonIdWhenUpdateBiometricsMWThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        when(mapper.mapperUpdateBiometricRequest(updateBiometricsRequest)).thenReturn(updateBiometricsMWRequest);
        String jsonResponse = Util.objectToString(LoginMWResponseFixture.withDefaultUpdateBiometricMWResponse());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        UpdateBiometricsResponse response = loginMiddlewareProvider.updateBiometricsMW(123, updateBiometricsRequest, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, UserResponseFixture.withDefaultUpdateBiometricsResponse());
        Mockito.verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
        Mockito.verify(mapper).mapperUpdateBiometricRequest(updateBiometricsRequest);
        Mockito.verify(httpClientFactoryMock).create();
    }

    @Test
    void givePersonIdWhenUpdateBiometricsStatusFalseMWThenExpectResponse() throws IOException {
        // Arrange
        UpdateBiometricsRequest request = UpdateBiometricsRequest.builder()
                .status(false)
                .tokenBiometric("sd12fs1df32s1df")
                .typeAuthentication("5")
                .build();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(LoginMWResponseFixture.withDefaultUpdateBiometricMWResponse());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        UpdateBiometricsResponse response = loginMiddlewareProvider.updateBiometricsMW(123, request, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, UserResponseFixture.withDefaultUpdateBiometricsResponse());
        Mockito.verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
        Mockito.verify(mapper).mapperUpdateBiometricRequest(any());
        Mockito.verify(httpClientFactoryMock).create();
    }

    @Test
    @DisplayName("Getting exception given mapped MW error on update biometrics.")
    void giveUnexpectedErrorWhenUpdateBiometricsThenGenericException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        when(mapper.mapperUpdateBiometricRequest(updateBiometricsRequest)).thenReturn(updateBiometricsMWRequest);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse()
                .withStatus(400)
                .withBody(Util.objectToString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.updateBiometricsMW(123, updateBiometricsRequest, map);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
        Mockito.verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
        Mockito.verify(mapper).mapperUpdateBiometricRequest(updateBiometricsRequest);
        Mockito.verify(httpClientFactoryMock).create();
    }

    @Test
    @DisplayName("Getting default exception given MW error on update biometrics.")
    void giveErrorWhenUpdateBiometricsThenRuntimeException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        when(mapper.mapperUpdateBiometricRequest(updateBiometricsRequest)).thenReturn(updateBiometricsMWRequest);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.updateBiometricsMW(123, updateBiometricsRequest, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
        Mockito.verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
        Mockito.verify(mapper).mapperUpdateBiometricRequest(updateBiometricsRequest);
    }

    @Test
    @DisplayName("Getting contact response.")
    void givenPersonIdWhenGetContactThenExpectResponse() throws IOException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream file = getClass().getResourceAsStream("/files/ContactResponse.json");
        assertNotNull(file);
        String jsonContact = new String(file.readAllBytes());
        UserContactResponse userContact = objectMapper.readValue(jsonContact, UserContactResponse.class);
        UserContactResponse.CompanyContactDetail expectedResponse = userContact.getCompanyContactDetail();
        Mockito.reset(httpClientFactoryMock);

        // Act
        ContactResponse actualResponse = loginMiddlewareProvider.getContactInfo();

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getSocialNetworks().getFacebook(), actualResponse.getSocialNetworks().getFacebook());
        assertEquals(expectedResponse.getSocialNetworks().getLinkedin(), actualResponse.getSocialNetworks().getLinkedin());
        assertEquals(expectedResponse.getSocialNetworks().getYoutube(), actualResponse.getSocialNetworks().getYoutube());
        assertEquals(expectedResponse.getAttentionLines().getHelpNumber(), actualResponse.getAttentionLines().getHelpNumber());
    }
}
