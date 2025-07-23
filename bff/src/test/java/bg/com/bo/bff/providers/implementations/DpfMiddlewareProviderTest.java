package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.dpf.mw.DpfMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.dpf.mw.DpfMWResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorMiddlewareProvider;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class DpfMiddlewareProviderTest {
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    private MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactoryMock;
    private DPFMiddlewareProvider dpfMiddlewareProvider;
    private ClientToken clientTokenMock;
    private ErrorMiddlewareProvider errorMiddlewareProvider;
    private Map<String, String> map;

    @BeforeEach
    void setUp() {
        this.map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "12344",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = Mockito.mock(MiddlewareConfig.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        dpfMiddlewareProvider = new DPFMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        clientTokenMock = new ClientToken();
        clientTokenMock.setAccessToken(UUID.randomUUID().toString());
        setField(dpfMiddlewareProvider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    void giveValidPersonIdWhenGetListDPFsThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DpfMWResponseFixture.withDefaultDpfMWResponse());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        DpfMWResponse response = dpfMiddlewareProvider.getDPFsList("56412", "123", map);

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), DpfMWResponseFixture.withDefaultDpfMWResponse().getData());
    }
}
