package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.notification.config.NotificationConfigMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.NotificationConfigMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.notification.config.RegisterNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.SubscribeNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.response.notification.config.NotificationConfigMWResponse;
import bg.com.bo.bff.providers.dtos.response.notification.config.NotificationConfigMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.notification.config.SubscribeNotificationMWResponse;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class NotificationConfigProviderTest {
    private NotificationConfigProvider provider;
    HttpServletRequest httpServletRequest;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();

    @BeforeEach
    void setUp() throws IOException {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        TokenMiddlewareProvider tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
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

        provider = new NotificationConfigProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, httpServletRequest);
        setField(provider, "middlewareConfig", middlewareConfig);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
    }

    @Test
    void givenValidDataWhenSubscribeNotificationThenExpectResponse() throws IOException {
        // Arrange
        SubscribeNotificationMWRequest request = NotificationConfigMWRequestFixture.withDefaultSubscriptionNotification();
        SubscribeNotificationMWResponse expectedResponse = NotificationConfigMWResponseFixture.withDefaultSubscriptionNotification();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        SubscribeNotificationMWResponse response = provider.subscriptionNotification(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void givenValidDataWhenGetNotificationConfigThenExpectResponse() throws IOException {
        // Arrange
        NotificationConfigMWRequest request = NotificationConfigMWRequestFixture.withDefaultGetNotificationConfig();
        NotificationConfigMWResponse expectedResponse = NotificationConfigMWResponseFixture.withDefaultGetNotificationConfig();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        NotificationConfigMWResponse response = provider.getNotificationConfig(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void givenValidDataWhenEnableNotificationThenExpectResponse() throws IOException {
        // Arrange
        RegisterNotificationMWRequest request = NotificationConfigMWRequestFixture.withDefaultEnableNotification();
        SubscribeNotificationMWResponse expectedResponse = NotificationConfigMWResponseFixture.withDefaultSubscribeNotification();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        SubscribeNotificationMWResponse response = provider.enableNotification(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void givenValidDataWhenDisableNotificationThenExpectResponse() throws IOException {
        // Arrange
        RegisterNotificationMWRequest request = NotificationConfigMWRequestFixture.withDefaultDisableNotification();
        SubscribeNotificationMWResponse expectedResponse = NotificationConfigMWResponseFixture.withDefaultDisableNotification();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        SubscribeNotificationMWResponse response = provider.disableNotification(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }


}
