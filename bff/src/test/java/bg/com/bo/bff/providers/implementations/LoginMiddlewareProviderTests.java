package bg.com.bo.bff.providers.implementations;
import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.request.*;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.login.*;
import bg.com.bo.bff.providers.dtos.responses.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.responses.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.responses.LogoutMWRequestFixture;
import bg.com.bo.bff.providers.dtos.responses.login.*;
import bg.com.bo.bff.providers.mappings.login.ILoginMapper;
import bg.com.bo.bff.providers.mappings.login.LoginMWMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.Collections;

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
    private DeviceEnrollmentProvider deviceEnrollmentProvider;
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    private MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactoryMock;
    private LoginMWMapper loginMWMapper;
    private ILoginMapper mapper;
    private ClientToken clientTokenMock;
    private ErrorMiddlewareProvider errorMiddlewareProvider;

    @BeforeEach
    void init() {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = Mockito.mock(MiddlewareConfig.class);
        loginMWMapper = LoginMWMapper.INSTANCE;
        mapper = Mockito.mock(ILoginMapper.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        loginMiddlewareProvider = new LoginMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, loginMWMapper, mapper);
        deviceEnrollmentProvider = new DeviceEnrollmentProvider(httpClientFactoryMock, middlewareConfig, tokenMiddlewareProviderMock);

        clientTokenMock = new ClientToken();
        clientTokenMock.setAccessToken("34sd3f243sdf43");

        setField(loginMiddlewareProvider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
        setField(deviceEnrollmentProvider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    void givenCorrectCredentialsWhenLoginThenReturnSuccess() throws IOException {
        // Arrange
        LoginMWFactorResponse expectedResponse = new LoginMWFactorResponse();
        LoginMWFactorDataResponse loginMWFactorDataResponse = new LoginMWFactorDataResponse();
        loginMWFactorDataResponse.setPersonId("123");
        expectedResponse.setData(loginMWFactorDataResponse);
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        stubFor(post(anyUrl())
                .willReturn(okJson(new ObjectMapper().writeValueAsString(expectedResponse))));

        LoginRequest loginRequest = LoginRequestFixture.withDefault();
        String ip = "testIp";

        // Act
        LoginMWFactorResponse actualResponse = loginMiddlewareProvider.validateFactorUser(loginRequest, ip);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getData().getPersonId(), actualResponse.getData().getPersonId());
    }

    @Test
    void givenUniqueIdWhenValidateDeviceThenReturnSuccessfully() throws IOException {
        // Arrange
        DeviceEnrollmentMWResponse expectedResponse = new DeviceEnrollmentMWResponse();
        expectedResponse.setStatusCode("enrolled");
        expectedResponse.setPersonId("123");

        stubFor(get(anyUrl()).willReturn(okJson(new ObjectMapper().writeValueAsString(ApiDataResponse.builder().data(expectedResponse).build()))));

        Device request = DeviceFixture.withDefault();
        String token = "dfkjhsdkf";

        // Act
        DeviceEnrollmentMWResponse actualResponse = deviceEnrollmentProvider.makeValidateDevice(request, token);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getPersonId(), actualResponse.getPersonId());
    }

    @Test
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
        GenericResponse response = loginMiddlewareProvider.logout(generic, generic, generic, generic, generic, generic, LogoutMWRequestFixture.withDefault());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getCode(), response.getCode());
    }

    @Test
    void givenInvalidDataWhenLogoutThenReturnException() throws IOException {
        //Arrange
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
            loginMiddlewareProvider.logout("deviceId", "deviceIp", "deviceName", "geoX", "geoY", "appVersion", LogoutMWRequestFixture.withDefault());
        });

        //Assert
        assertEquals(expectedResponse.getCode(), (exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
        verify(1, postRequestedFor(anyUrl()));
    }

    @Test
    void givenValidDataWhenChangePasswordThenExpectedResponse() throws IOException {
        //Arrange
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("SUCCESS");
        String jsonResponse = Util.objectToString(expectedResponse);
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        stubFor(put(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        GenericResponse response = loginMiddlewareProvider.changePassword("123","123","123","123","123", ChangePasswordRequestFixture.withDefault());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getCode(), response.getCode());
    }

    @Test
    void givenInvalidDataWhenChangePasswordThenReturnException() throws IOException {
        //Arrange
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        //Act
        Exception exception = assertThrows(Exception.class, () -> {
            loginMiddlewareProvider.changePassword("123","123","123","123","123", ChangePasswordRequestFixture.withDefault());
        });

        //Assert
        assertEquals(ErrorResponseConverter.GenericErrorResponse.DEFAULT.getMessage(), exception.getMessage());
    }

    @Test
    void givePersonCodeWhenValidateCredentialsThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(mapper.mapperRequest(any(),any(),any())).thenReturn(LoginMWCredentialRequestFixture.withDefault());
        String jsonResponse = Util.objectToString(LoginMWCredentialResponseFixture.withDefault());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoginValidationServiceResponse response = loginMiddlewareProvider.validateCredentials(LoginRequestFixture.withDefault(),"123", LoginMWFactorDataResponseFixture.withDefault());

        // Assert
        assertNotNull(response);
    }

    @Test
    void giveUnexpectedErrorOccursWhenValidateCredentialsThenGenericException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(mapper.mapperRequest(any(),any(),any())).thenReturn(LoginMWCredentialRequestFixture.withDefault());
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse().withStatus(400).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            loginMiddlewareProvider.validateCredentials(LoginRequestFixture.withDefault(),"123", LoginMWFactorDataResponseFixture.withDefault());
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    void giveErrorWhenValidateCredentialsThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            loginMiddlewareProvider.validateCredentials(LoginRequestFixture.withDefault(),"123", LoginMWFactorDataResponseFixture.withDefault());
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }
}
