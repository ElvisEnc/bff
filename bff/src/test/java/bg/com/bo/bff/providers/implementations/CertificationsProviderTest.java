package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.certifications.*;
import bg.com.bo.bff.providers.dtos.response.certificates.*;
import bg.com.bo.bff.providers.dtos.response.certifications.*;
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
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        provider = new CertificationsProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, httpServletRequest);
        setField(provider, "middlewareConfig", middlewareConfig);
    }

    @Test
    void getCertsTypes() throws IOException {
        CertificatesTypeListMWResponse expected = CertificatesTypeListMWResponseFixture.withDefaults();
        String jsonResponse = Util.objectToString(expected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        CertificatesTypeListMWResponse response = provider.getCertificatesType("123");
        String json = Util.objectToString(response);

        assertEquals(jsonResponse, json);
    }

    @Test
    void getAccountsOK() throws IOException {
        CertificatesAccountsListMWResponse expected = CertificatesAccountsListMWResponseFixture.withDefaults();
        String jsonResponse = Util.objectToString(expected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        CertificatesAccountsListMWResponse response = provider.getAccountsList("1234");
        String json = Util.objectToString(response);

        assertEquals(jsonResponse, json);
    }

    @Test
    void getPreferredExchangeRateOK() throws IOException {
        CertificationsPreferredExchMWResponse expected = CertificationsPreferredExchMWResponseFixture.withDefaults();
        String jsonExpected = Util.objectToString(expected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonExpected)));

        CertificationsPreferredExchMWResponse response = provider.getPreferredExRate("1234");
        String json = Util.objectToString(response);

        assertEquals(jsonExpected, json);
    }

    @Test
    void getCertificationsHistoryOK() throws IOException {
        CertificationsHistoryMWResponse expected = CertificationsHistoryMWResponseFixture.withDefaults();
        String jsonExpected = Util.objectToString(expected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonExpected)));

        CertificationsHistoryMWResponse response = provider.getCertificationsHistory("1234");
        String json = Util.objectToString(response);

        assertEquals(jsonExpected, json);
    }

    @Test
    void getConfigOK() throws IOException {
        CertificationConfigMWRequest request = CertificationConfigMWRequestFixture.withDefaults();
        CertificationConfigMWResponse expected = CertificationConfigMWResponseFixture.withDefaults();
        String jsonExpected = Util.objectToString(expected);
        stubFor(post(anyUrl()).willReturn(okJson(jsonExpected)));

        CertificationConfigMWResponse response = provider.getConfig(request);
        String json = Util.objectToString(response);

        assertEquals(jsonExpected, json);
    }

    @Test
    void getPriceOK() throws IOException {
        CertificationPriceMWRequest request = CertificationPriceMWRequestFixture.withDefaults();
        CertificationPriceMWResponse expected = CertificationPriceMWResponseFixture.withDefaults();
        String jsonExpected = Util.objectToString(expected);
        stubFor(post(anyUrl()).willReturn(okJson(jsonExpected)));

        CertificationPriceMWResponse response = provider.getCertificationPrice(request);
        String json = Util.objectToString(response);

        assertEquals(jsonExpected, json);
    }

    @Test
    void saveRequestOK() throws IOException {
        SaveCertificationMWRequest request = SaveCertificationMWRequestFixture.withDefaults();
        CertificationSaveRequestMWResponse expected = CertificationSaveRequestMWResponseFixture.withDefaults();
        String jsonExpected = Util.objectToString(expected);
        stubFor(post(anyUrl()).willReturn(okJson(jsonExpected)));

        CertificationSaveRequestMWResponse  response = provider.saveCertificateRequest(request);
        String json = Util.objectToString(response);

        assertEquals(jsonExpected, json);
    }

}