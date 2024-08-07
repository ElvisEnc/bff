package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoansMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class LoansTransactionProviderTest {
    private LoansTransactionProvider provider;
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();
    ErrorMiddlewareProvider errorMiddlewareProvider;
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
        middlewareConfig = MiddlewareConfigFixture.withDefault();
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        provider = new LoansTransactionProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        setField(provider, "middlewareConfig", middlewareConfig);
    }

    @Test
    @DisplayName("Pay loan from a user given PersonId, AccountId and CorrelativeId")
    void givenPersonIdWhenGetAffiliationsServicesThenExpectResponse() throws IOException {
        // Arrange
        LoanPaymentMWResponse expectedResponse = LoansMWResponseFixture.withDefaultLoanPaymentMWResponse();
        LoanPaymentMWRequest mwRequest = LoansMWRequestFixture.withDefaultLoanPaymentMWRequest();

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoanPaymentMWResponse response = provider.payLoanInstallment(mwRequest, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }
}