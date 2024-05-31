package bg.com.bo.bff.providers.implementations;


import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentMWRequestFixture;
import bg.com.bo.bff.application.dtos.response.qr.QRPaymentMWResponseFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.qr.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.QRPaymentMWResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class QrTransactionProviderTest {
    @Spy
    @InjectMocks
    private QrTransactionProvider provider;

    @Mock
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;

    @Mock
    private MiddlewareConfig middlewareConfig;

    @Mock
    private IHttpClientFactory httpClientFactoryMock;

    @Mock
    private ClientToken clientTokenMock;

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
    }

    @Test
    void givenQRPaymentRequestWhenQrPaymentThenQRPaymentMWResponse() throws IOException {
        // Arrange
        QRPaymentMWRequest request = QRPaymentMWRequestFixture.withDefault();
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken(UUID.randomUUID().toString());
      
        QRPaymentMWResponse expected = QRPaymentMWResponseFixture.withDefault();
        String jsonResponse = new ObjectMapper().writeValueAsString(expected);
       
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientToken);
        when(middlewareConfig.getUrlBase()).thenReturn("http://localhost:8080");
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());

        //Act
        QRPaymentMWResponse actual = provider.qrPayment(request, this.map);

        //Asserts
        assertEquals(new ObjectMapper().writeValueAsString(expected), new ObjectMapper().writeValueAsString(actual));
        
    }
}