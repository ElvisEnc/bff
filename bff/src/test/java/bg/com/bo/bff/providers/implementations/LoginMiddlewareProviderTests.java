package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.login.LoginRequestFixture;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.user.UserRequestFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.UserResponseFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
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
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
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
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private ILoginMapper mapper;
    private ClientToken clientTokenMock;
    private ErrorMiddlewareProvider errorMiddlewareProvider;
    private static final String DEVICE_ID = "42ebffbd7c30307d";
    private static final String DEVICE_NAME = "Android";
    private static final String GEO_POSITION_X = "12.265656";
    private static final String GEO_POSITION_Y = "12.454545";
    private static final String APP_VERSION = "1.0.0";
    private static final String JSON_DATA = "IntcIm5hbWVcIjpcIlwiLFwicGFnaW5hdGlvblwiOntcInBhZ2VcIjoxLFwicGFnZVNpemVcIjoxMH19Ig==";
    Map<String, String> map;
    LoginRequest loginRequest = LoginRequestFixture.withDefault();
    UpdateBiometricsRequest updateBiometricsRequest = UserRequestFixture.withDefaultUpdateBiometricsRequest();
    UpdateBiometricsMWRequest updateBiometricsMWRequest = LoginMWRequestFixture.withDefaultUpdateBiometricsMWRequest();

    @BeforeEach
    void init() {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = Mockito.mock(MiddlewareConfig.class);
        loginMWMapper = LoginMWMapper.INSTANCE;
        mapper = Mockito.mock(ILoginMapper.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(mapper.mapperRequest(any(), any())).thenReturn(LoginMWRequestFixture.withDefaultLoginCredentialMWRequest());
        Mockito.when(mapper.converResponse(any(), any())).thenReturn(LoginMWResponseFixture.withDefaultLoginValidationServiceResponse());
        String jsonResponse = Util.objectToString(LoginMWResponseFixture.withDefaultLoginCredentialMWResponse());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoginValidationServiceResponse response = loginMiddlewareProvider.validateCredentials(LoginRequestFixture.withDefault(), LoginMWResponseFixture.withDefaultLoginFactorData(), map);

        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("Getting exception given mapped MW error on validate credentials.")
    void giveUnexpectedErrorOccursWhenValidateCredentialsThenGenericException() throws IOException {
        // Arrange

        LoginFactorData data = LoginMWResponseFixture.withDefaultLoginFactorData();
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(mapper.mapperRequest(any(), any())).thenReturn(LoginMWRequestFixture.withDefaultLoginCredentialMWRequest());
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

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

        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        HandledException exception = assertThrows(HandledException.class, () -> {
            loginMiddlewareProvider.logout("deviceId", "deviceIp", "deviceName", "geoX", "geoY", "appVersion", mwRequest);
        });

        // Assert
        assertEquals(ErrorResponseConverter.GenericErrorResponse.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(ErrorResponseConverter.GenericErrorResponse.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on change password.")
    void givenValidDataWhenChangePasswordThenExpectedResponse() throws IOException {
        //Arrange
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("SUCCESS");
        String jsonResponse = Util.objectToString(expectedResponse);
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(error.getMwCode())
                        .build()))
                .build();
        stubFor(put(anyUrl()).willReturn(aResponse().withStatus(406).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        HandledException exception = assertThrows(HandledException.class, () -> {
            loginMiddlewareProvider.changePassword("123", "123", UserRequestFixture.withDefaultChangePasswordRequest(), new HashMap<>());
        });

        // Assert
        assertEquals(error.getCode(), exception.getCode());
        assertEquals(error.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Getting default exception given MW error on change password.")
    void givenInvalidDataWhenChangePasswordThenReturnException() throws IOException {
        //Arrange
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        //Act
        HandledException exception = assertThrows(HandledException.class, () -> {
            loginMiddlewareProvider.changePassword("123", "123", UserRequestFixture.withDefaultChangePasswordRequest(), new HashMap<>());
        });

        //Assert
        assertEquals(ErrorResponseConverter.GenericErrorResponse.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(ErrorResponseConverter.GenericErrorResponse.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on validate device.")
    void givenUniqueIdWhenValidateDeviceThenReturnSuccessfully() throws IOException {
        // Arrange
        DeviceEnrollmentMWResponse expectedResponse = new DeviceEnrollmentMWResponse();
        expectedResponse.setStatusCode("enrolled");
        expectedResponse.setPersonId("123");

        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);

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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(error.getCodeMiddleware())
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse().withStatus(400).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.makeValidateDevice(new HashMap<>());
        });

        // Assert
        assertEquals(AppError.MDWPGL_400.getMessage(), exception.getMessage());
        assertEquals(AppError.MDWPGL_400.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting default exception given MW error on validate device.")
    void givenErrorWhenValidateDeviceThenReturnMappedDefaultException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(AppError.DEFAULT.getCodeMiddleware())
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse().withStatus(400).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.makeValidateDevice(new HashMap<>());
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on get biometrics.")
    void givePersonIdWhenGetBiometricsThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
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
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.getBiometricsMW(123, map);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    @DisplayName("Getting default exception given MW error on get biometrics.")
    void giveErrorWhenGetBiometricsThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.getBiometricsMW(123, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on update biometrics.")
    void givePersonIdWhenUpdateBiometricsMWThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(mapper.mapperUpdateBiometricRequest(updateBiometricsRequest)).thenReturn(updateBiometricsMWRequest);
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
    @DisplayName("Getting exception given mapped MW error on update biometrics.")
    void giveUnexpectedErrorWhenUpdateBiometricsThenGenericException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(mapper.mapperUpdateBiometricRequest(updateBiometricsRequest)).thenReturn(updateBiometricsMWRequest);
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
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(mapper.mapperUpdateBiometricRequest(updateBiometricsRequest)).thenReturn(updateBiometricsMWRequest);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

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
}
