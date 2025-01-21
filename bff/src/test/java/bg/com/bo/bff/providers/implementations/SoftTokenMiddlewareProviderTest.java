package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.softtoken.SoftTokenMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.SoftTokenMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenValidationEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenWelcomeMWResponse;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class SoftTokenMiddlewareProviderTest {
    private SoftTokenProvider provider;
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    HttpServletRequest httpServletRequest;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();

    @BeforeEach
    void setUp() throws IOException {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = MiddlewareConfigFixture.withDefault();
        httpServletRequest = Mockito.mock(HttpServletRequest.class);

        when(httpServletRequest.getHeader(DeviceMW.DEVICE_ID.getCode())).thenReturn("1234");
        when(httpServletRequest.getHeader(DeviceMW.DEVICE_NAME.getCode())).thenReturn("Android");
        when(httpServletRequest.getHeader(DeviceMW.GEO_POSITION_X.getCode())).thenReturn("12.2323232");
        when(httpServletRequest.getHeader(DeviceMW.GEO_POSITION_Y.getCode())).thenReturn("12.2323232");
        when(httpServletRequest.getHeader(DeviceMW.APP_VERSION.getCode())).thenReturn("1.0.0");
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");  // Mocking the remote IP address
        when(httpServletRequest.getHeaderNames()).thenReturn(Collections.enumeration(Arrays.asList(
                DeviceMW.DEVICE_ID.getCode(),
                DeviceMW.DEVICE_IP.getCode(),
                DeviceMW.DEVICE_NAME.getCode(),
                DeviceMW.GEO_POSITION_X.getCode(),
                DeviceMW.GEO_POSITION_Y.getCode(),
                DeviceMW.APP_VERSION.getCode()
        )));
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());

        provider = new SoftTokenProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, httpServletRequest);
        setField(provider, "middlewareConfig", middlewareConfig);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
    }

    @Test
    @DisplayName("message given PersonCode")
    void givenPersonCodeWhenGetWelcomeThenExpectResponse() throws IOException {
        // Arrange
        ApiDataResponse<SoftTokenWelcomeMWResponse> expectedResponse = ApiDataResponse.of(SoftTokenMWResponseFixture.withDefaultWelcome());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        SoftTokenWelcomeMWResponse response = provider.getWelcomeMessage(SoftTokenMWRequestFixture.withDefault());

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(SoftTokenMWResponseFixture.withDefaultWelcome());
    }

    @Test
    @DisplayName("Data enrollment given PersonCode")
    void givenPersonCodeWhenGetDataEnrollmentWhenExpectResponse() throws IOException {
        // Arrange
        ApiDataResponse<SoftTokenDataEnrollmentMWResponse> expectedResponse = ApiDataResponse.of(SoftTokenMWResponseFixture.withDefaultDataEnrollment());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        SoftTokenDataEnrollmentMWResponse response = provider.getDataEnrollment(SoftTokenMWRequestFixture.withDefault());

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(SoftTokenMWResponseFixture.withDefaultDataEnrollment());
    }

    @Test
    @DisplayName("question enrollment given PersonCode")
    void givenPersonCodeWhenGetQuestionEnrollmentWhenExpectResponse() throws IOException {
        // Arrange
        ApiDataResponse<SoftTokenQuestionEnrollmentMWResponse> expectedResponse = ApiDataResponse.of(SoftTokenMWResponseFixture.withDefaultQuestion());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        SoftTokenQuestionEnrollmentMWResponse response = provider.getQuestionEnrollment(SoftTokenMWRequestFixture.withDefault());

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(SoftTokenMWResponseFixture.withDefaultQuestion());
    }

    @Test
    @DisplayName("question enrollment given PersonCode")
    void givenPersonCodeWhenGetValidationEnrollmentWhenExpectResponse() throws IOException {
        // Arrange
        ApiDataResponse<SoftTokenValidationEnrollmentMWResponse> expectedResponse = ApiDataResponse.of(SoftTokenMWResponseFixture.withDefaultValidate());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        SoftTokenValidationEnrollmentMWResponse response = provider.getValidationEnrollment(SoftTokenMWRequestFixture.withDefault());

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(SoftTokenMWResponseFixture.withDefaultValidate());
    }
}
