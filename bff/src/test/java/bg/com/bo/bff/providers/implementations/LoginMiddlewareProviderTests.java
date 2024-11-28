package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.login.mw.*;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginMWResponseFixture;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.login.mw.*;
import bg.com.bo.bff.providers.dtos.response.personal.information.UserContactResponse;
import bg.com.bo.bff.providers.models.enums.middleware.login.LoginMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

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
    HttpServletRequest httpServletRequest;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();
    private LoginMiddlewareProvider provider;
    TokenMiddlewareProvider tokenMiddlewareProviderMock;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private ErrorMiddlewareProvider errorMiddlewareProvider;

    @BeforeEach
    void setup(TestInfo testInfo) throws IOException {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = MiddlewareConfigFixture.withDefault();
        httpServletRequest = Mockito.mock(HttpServletRequest.class);

        if (!testInfo.getDisplayName().contains("Getting contact response.")) {
            when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
            when(httpServletRequest.getHeader(DeviceMW.DEVICE_ID.getCode())).thenReturn("1234");
            when(httpServletRequest.getHeader(DeviceMW.DEVICE_NAME.getCode())).thenReturn("Android");
            when(httpServletRequest.getHeader(DeviceMW.GEO_POSITION_X.getCode())).thenReturn("12.2323232");
            when(httpServletRequest.getHeader(DeviceMW.GEO_POSITION_Y.getCode())).thenReturn("12.2323232");
            when(httpServletRequest.getHeader(DeviceMW.APP_VERSION.getCode())).thenReturn("1.0.0");
            when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
            when(httpServletRequest.getHeaderNames()).thenReturn(Collections.enumeration(Arrays.asList(
                    DeviceMW.DEVICE_ID.getCode(),
                    DeviceMW.DEVICE_IP.getCode(),
                    DeviceMW.DEVICE_NAME.getCode(),
                    DeviceMW.GEO_POSITION_X.getCode(),
                    DeviceMW.GEO_POSITION_Y.getCode(),
                    DeviceMW.APP_VERSION.getCode()
            )));
            when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        }
        provider = new LoginMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, httpServletRequest);
        setField(provider, "middlewareConfig", middlewareConfig);
    }

    @Test
    @DisplayName("Get user data on valid factor user.")
    void givenCorrectFactorUserWhenValidateFactorUserThenUserData() throws IOException {
        // Arrange
        LoginFactorMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginFactorMWRequest();
        LoginFactorMWResponse expectedResponse = LoginMWResponseFixture.withDefaultLoginFactorMWResponse();

        stubFor(post(anyUrl()).willReturn(okJson(Util.objectToString(expectedResponse))));
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);

        // Act
        LoginFactorMWResponse response = provider.validateFactorUser(mwRequest);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @ParameterizedTest
    @EnumSource(value = AppError.class, names = {"MDWLM_007"})
    @DisplayName("Getting exception given mapped MW error on ValidateFactorUse.")
    void givenMappedErrorWhenValidateFactorUserThenReturnGenericException(AppError error) throws IOException {
        // Arrange
        LoginFactorMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginFactorMWRequest();

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
            provider.validateFactorUser(mwRequest);
        });

        // Assert
        assertEquals(exception.getCode(), error.getCode());
        assertEquals(exception.getMessage(), error.getMessage());
    }

    @Test
    @DisplayName("Getting default exception given MW error on ValidateFactorUse.")
    void givenErrorWhenValidateFactorUserDefaultGenericException() throws IOException {
        // Arrange
        LoginFactorMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginFactorMWRequest();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            provider.validateFactorUser(mwRequest);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on validate credentials.")
    void givePersonCodeWhenValidateCredentialsThenExpectResponse() throws IOException {
        // Arrange
        LoginCredentialMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginCredentialMWRequest();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(LoginMWResponseFixture.withDefaultLoginCredentialMWResponse());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoginCredentialMWResponse response = provider.validateCredentials(mwRequest);

        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("Getting exception given mapped MW error on validate credentials.")
    void giveUnexpectedErrorOccursWhenValidateCredentialsThenGenericException() throws IOException {
        // Arrange
        LoginCredentialMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginCredentialMWRequest();
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
            provider.validateCredentials(mwRequest);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    @DisplayName("Getting default exception given mapped MW error on validate credentials.")
    void giveErrorWhenValidateCredentialsThenRuntimeException() throws IOException {
        // Arrange
        LoginCredentialMWRequest mwRequest = LoginMWRequestFixture.withDefaultLoginCredentialMWRequest();
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            provider.validateCredentials(mwRequest);
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
        String jsonResponse = new ObjectMapper().writeValueAsString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        LogoutMWResponse response = provider.logout(LoginMWRequestFixture.withDefaultLogoutMWRequest());

        //Assert
        assertNotNull(response);
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
        GenericException expectedResponse = new GenericException(DefaultMiddlewareError.MW_SERVICE_UNAVAILABLE.getMessage(), DefaultMiddlewareError.MW_SERVICE_UNAVAILABLE.getHttpCode(), DefaultMiddlewareError.MW_SERVICE_UNAVAILABLE.getCode());
        stubFor(post(anyUrl()).willReturn(aResponse()
                .withStatus(500)
                .withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        //Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            provider.logout(mwRequest);
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
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            provider.logout(mwRequest);
        });

        // Assert
        assertEquals(DefaultMiddlewareError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(DefaultMiddlewareError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on change password.")
    void givenValidDataWhenChangePasswordThenExpectedResponse() throws IOException {
        //Arrange
        ChangePasswordMWRequest mwRequest = LoginMWRequestFixture.withDefaultChangePasswordMWRequest();
        ChangePasswordMWResponse mwResponse = ChangePasswordMWResponse.builder().personId("123456").build();

        String jsonResponse = Util.objectToString(ApiDataResponse.of(mwResponse));
        stubFor(put(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        ChangePasswordMWResponse response = provider.changePassword(mwRequest);

        //Assert
        assertNotNull(response);
        assertEquals(mwResponse.getPersonId(), response.getPersonId());
    }

    @ParameterizedTest
    @EnumSource(value = ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.class, names = {"MDWPWD_0099"})
    @DisplayName("Getting exception given mapped MW error on change password.")
    void givenMappedErrorWhenChangePasswordThenReturnGenericException(IErrorResponse error) throws IOException {
        // Arrange
        ChangePasswordMWRequest mwRequest = LoginMWRequestFixture.withDefaultChangePasswordMWRequest();
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(error.getMwCode())
                        .build()))
                .build();
        stubFor(put(anyUrl()).willReturn(aResponse().withStatus(406).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                provider.changePassword(mwRequest)
        );

        // Assert
        assertEquals(error.getCode(), exception.getCode());
        assertEquals(error.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Getting default exception given MW error on change password.")
    void givenInvalidDataWhenChangePasswordThenReturnException() throws IOException {
        //Arrange
        ChangePasswordMWRequest mwRequest = LoginMWRequestFixture.withDefaultChangePasswordMWRequest();
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                provider.changePassword(mwRequest)
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
        stubFor(get(anyUrl()).willReturn(okJson(new ObjectMapper().writeValueAsString(ApiDataResponse.builder().data(expectedResponse).build()))));

        // Act
        DeviceEnrollmentMWResponse actualResponse = provider.makeValidateDevice();

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getPersonId(), actualResponse.getPersonId());
    }

    @ParameterizedTest
    @EnumSource(value = AppError.class, names = {"MDWPGL_400"})
    @DisplayName("Getting exception given mapped MW error on validate device.")
    void givenMappedErrorWhenValidateDeviceThenReturnMappedGenericException(AppError error) throws IOException {
        // Arrange
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code(error.getCodeMiddleware())
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse().withStatus(400).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                provider.makeValidateDevice()
        );

        // Assert
        assertEquals(LoginMiddlewareError.MDWPGL_400.getMessage(), exception.getMessage());
        assertEquals(LoginMiddlewareError.MDWPGL_400.getHttpCode(), exception.getStatus());
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
                provider.makeValidateDevice()
        );

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting middleware exception given MW error on validate device.")
    void givenErrorWhenValidateDeviceThenReturnMappedMiddlwareException() throws IOException {
        // Arrange
        stubFor(get(anyUrl()).willReturn(okJson(new ObjectMapper().writeValueAsString(ApiDataResponse.builder().data(null).build()))));

        // Act
        DeviceEnrollmentMWResponse response = provider.makeValidateDevice();

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
            provider.makeValidateDevice();
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
            provider.makeValidateDevice();
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
        BiometricStatusMWResponse response = provider.getBiometricsMW(123);

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
                provider.getBiometricsMW(123)
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
                provider.getBiometricsMW(123)
        );

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
    }

    @Test
    @DisplayName("Getting expected response on update biometrics.")
    void givePersonIdWhenUpdateBiometricsMWThenExpectResponse() throws IOException {
        // Arrange
        UpdateBiometricsMWRequest mwRequest = LoginMWRequestFixture.withDefaultUpdateBiometricsMWRequest();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(LoginMWResponseFixture.withDefaultUpdateBiometricMWResponse());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        UpdateBiometricsResponse response = provider.updateBiometricsMW(123, mwRequest);

        // Assert
        assertNotNull(response);
        assertEquals(response, UserResponseFixture.withDefaultUpdateBiometricsResponse());
        Mockito.verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
        Mockito.verify(httpClientFactoryMock).create();
    }

    @Test
    void givePersonIdWhenUpdateBiometricsStatusFalseMWThenExpectResponse() throws IOException {
        // Arrange
        UpdateBiometricsMWRequest mwRequest = LoginMWRequestFixture.withDefaultUpdateBiometricsMWRequest();
        mwRequest.setStatusBiometric("N");

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(LoginMWResponseFixture.withDefaultUpdateBiometricMWResponse());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        UpdateBiometricsResponse response = provider.updateBiometricsMW(123, mwRequest);

        // Assert
        assertNotNull(response);
        assertEquals(response, UserResponseFixture.withDefaultUpdateBiometricsResponse());
        Mockito.verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
        Mockito.verify(httpClientFactoryMock).create();
    }

    @Test
    @DisplayName("Getting exception given mapped MW error on update biometrics.")
    void giveUnexpectedErrorWhenUpdateBiometricsThenGenericException() throws IOException {
        // Arrange
        UpdateBiometricsMWRequest mwRequest = LoginMWRequestFixture.withDefaultUpdateBiometricsMWRequest();

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
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
            provider.updateBiometricsMW(123, mwRequest);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
        Mockito.verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
        Mockito.verify(httpClientFactoryMock).create();
    }

    @Test
    @DisplayName("Getting default exception given MW error on update biometrics.")
    void giveErrorWhenUpdateBiometricsThenRuntimeException() throws IOException {
        // Arrange
        UpdateBiometricsMWRequest mwRequest = LoginMWRequestFixture.withDefaultUpdateBiometricsMWRequest();

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            provider.updateBiometricsMW(123, mwRequest);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        assertEquals(AppError.DEFAULT.getHttpCode(), exception.getStatus());
        Mockito.verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Getting contact response.")
    void givenPersonIdWhenGetContactThenExpectResponse() throws IOException {
        // Arrange
        InputStream file = getClass().getResourceAsStream("/files/ContactResponse.json");
        assert file != null;
        String jsonContact = new String(file.readAllBytes());
        UserContactResponse userContact = Util.stringToObject(jsonContact, UserContactResponse.class);
        UserContactResponse.CompanyContactDetail expectedResponse = userContact.getCompanyContactDetail();

        // Act
        ContactResponse actualResponse = provider.getContactInfo();

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getSocialNetworks().getFacebook(), actualResponse.getSocialNetworks().getFacebook());
        assertEquals(expectedResponse.getSocialNetworks().getLinkedin(), actualResponse.getSocialNetworks().getLinkedin());
        assertEquals(expectedResponse.getSocialNetworks().getYoutube(), actualResponse.getSocialNetworks().getYoutube());
        assertEquals(expectedResponse.getAttentionLines().getHelpNumber(), actualResponse.getAttentionLines().getHelpNumber());
    }
}
