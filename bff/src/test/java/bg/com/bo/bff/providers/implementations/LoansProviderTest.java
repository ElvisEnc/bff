package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoansMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.loans.mw.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class LoansProviderTest {
    private LoansProvider provider;
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

        provider = new LoansProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, httpServletRequest);
        setField(provider, "middlewareConfig", middlewareConfig);
    }

    @Test
    @DisplayName("Get list loans for a user given PersonId")
    void givenPersonIdWhenGetAffiliationsServicesThenExpectResponse() throws IOException {
        // Arrange
        ListLoansMWResponse expectedResponse = LoansMWResponseFixture.withDefaultListLoansMWResponse();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        ListLoansMWResponse response = provider.getListLoansByPerson("123", "321");

        // Assert
        assertNotNull(response);
        assertEquals(response, expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Get list loan payments for a user given loanId and loanNumber")
    void givenLoanPaymentRequestWhenGetLoanPaymentsThenExpectResponse() throws IOException {
        // Arrange
        LoanPaymentsMWResponse expectedResponse = LoansMWResponseFixture.withDefaultListLoanPaymentsMWResponse();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoanPaymentsMWResponse response = provider.getListLoanPayments("123", "123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Get list loan insurance payments for a user given loanId and loanNumber")
    void givenLoanInsurancePaymentRequestWhenGetLoanInsurancePaymentsThenExpectResponse() throws IOException {
        // Arrange
        LoanInsurancePaymentsMWResponse expectedResponse = LoansMWResponseFixture.withDefaultLoanInsurancePaymentsMWResponse();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoanInsurancePaymentsMWResponse response = provider.getListLoanInsurancePayments("123", "123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Get list loan plan payments for a user given loanId")
    void givenLoanPlanRequestWhenGetLoanPlanPaymentsThenExpectResponse() throws IOException {
        // Arrange
        LoanPlanMWResponse expectedResponse = LoansMWResponseFixture.withDefaultLoanPlanMWResponse();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoanPlanMWResponse response = provider.getLoanPlansPayments("123", "123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Get loan payment for a user given loanId and personId")
    void givenLoandIdAndPersonIdWhenGetLoanDetailPaymentThenExpectResponse() throws IOException {
        // Arrange
        LoanDetailPaymentMWResponse expectedResponse = LoansMWResponseFixture.withDefaultLoanDetailPaymentMWResponse();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LoanDetailPaymentMWResponse response = provider.getLoanDetailPayment("123", "123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Validate money laudering PCC01")
    void givenValidDataWhenValidatePCC01ThenExpectResponse() throws IOException {
        // Arrange
        Pcc01MWRequest request = LoansMWRequestFixture.withDefaultPcc01MWRequest();
        Pcc01MWResponse expectedResponse = LoansMWResponseFixture.withDefaultPcc01MWResponse();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);

        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        Pcc01MWResponse response = provider.validateControl(request);

        // Assert
        assertNotNull(response);
        assertThat(response).isEqualTo(expectedResponse);
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }
}