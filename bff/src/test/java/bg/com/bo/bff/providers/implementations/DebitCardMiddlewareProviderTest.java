package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.debit.card.DebitCardMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.debit.card.DebitCardMWErrorResponseFixture;
import bg.com.bo.bff.providers.dtos.response.debit.card.DebitCardMWResponseFixture;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
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
class DebitCardMiddlewareProviderTest {
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    private MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactoryMock;
    private DebitCardMiddlewareProvider debitCardMiddlewareProvider;
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
        debitCardMiddlewareProvider = new DebitCardMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        clientTokenMock = new ClientToken();
        clientTokenMock.setAccessToken(UUID.randomUUID().toString());
        setField(debitCardMiddlewareProvider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    void givenValidDataWhenChangeAmountThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DebitCardMWResponseFixture.withDefault());
        stubFor(patch(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = debitCardMiddlewareProvider.changeAmount(
                DebitCardMWRequestFixture.withDefault(), map
        );

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT));
    }

    @Test
    void givenInvalidResponseWhenGetListDPFsThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DebitCardMWErrorResponseFixture.withDefault());
        stubFor(patch(anyUrl()).willReturn(badRequest().withBody(jsonResponse)));

        // Act
        Exception exception = assertThrows(Exception.class, () ->  debitCardMiddlewareProvider.changeAmount(
                DebitCardMWRequestFixture.withDefault(), map
        ));

        // Assert
        assertEquals(GenericException.class, exception.getClass());
        assertEquals(DebitCardMiddlewareError.MDWTJD_002.getHttpCode(), ((GenericException) exception).getStatus());
        assertEquals(DebitCardMiddlewareError.MDWTJD_002.getCode(), ((GenericException) exception).getCode());
        assertEquals(DebitCardMiddlewareError.MDWTJD_002.getMessage(), ((GenericException) exception).getMessage());
    }
}
