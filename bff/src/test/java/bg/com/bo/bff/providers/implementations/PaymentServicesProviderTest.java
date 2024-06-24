package bg.com.bo.bff.providers.implementations;


import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.debit.card.ListDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.ListDebitCardMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.services.SubcategoriesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.services.SubcategoriesMWResponseFixture;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class PaymentServicesProviderTest {

    private PaymentServicesProvider provider;

    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();
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
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        provider = new PaymentServicesProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        setField(provider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }
    @Test
    void givenCategoryIdWhenGetSubcategoriesThenSubcategoriesMWResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String expected = Util.objectToString(SubcategoriesMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(expected)));

        // Act
        SubcategoriesMWResponse actual = provider.getSubcategories(2, map);

        // Assert
        assertNotNull(actual);
        assertEquals( expected, Util.objectToString(actual));
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void givenCategoryIdWhenGetSubcategoriesThenErrorNotAccetable() throws IOException{
        // Arrange

        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = SubcategoriesMWResponseFixture.errorMDWPSM_003();
        stubFor(get(anyUrl()).willReturn(jsonResponse(jsonResponse, HttpStatus.SC_NOT_ACCEPTABLE)));

        // Act
        try {
            SubcategoriesMWResponse actual = provider.getSubcategories(500, map);
        } catch (GenericException ex) {
            // Assert
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_003.getCode(), ex.getCode());
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_003.getHttpCode(), ex.getStatus());
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_003.getMessage(), ex.getMessage());
        }
    }
}