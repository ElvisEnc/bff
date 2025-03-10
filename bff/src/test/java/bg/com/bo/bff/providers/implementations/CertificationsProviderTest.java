package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.response.certificates.CertificatesTypeListMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class CertificationsProviderTest {

    private CertificationsProvider provider;
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;

    HttpServletRequest httpServletRequest;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();

    @BeforeEach
    void setUp() {
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

        provider = new CertificationsProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, httpServletRequest);
        setField(provider, "middlewareConfig", middlewareConfig);
    }

    @Test
    void getCertsTypes() throws IOException {
        CertificatesTypeListMWResponse expected = CertificatesTypeListMWResponseFixture.withDefaults();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(expected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        CertificatesTypeListMWResponse response = provider.getCertificatesType("123", "123");
        String json = Util.objectToString(response);

        assertEquals(jsonResponse, json);
    }
}