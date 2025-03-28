package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.remittance.RemittanceMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.CheckRemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ConsultWURemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.DepositRemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.MoneyOrderSentMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.UpdateWURemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.RemittanceMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.*;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class RemittanceMiddlewareProviderTest {
    private RemittanceProvider provider;
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

        provider = new RemittanceProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, httpServletRequest);
        setField(provider, "middlewareConfig", middlewareConfig);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
    }

    @Test
    @DisplayName("Get general parameters given PersonCode")
    void givenPersonCodeWhenGetGeneralParametersThenExpectResponse() throws IOException {
        // Arrange
        GeneralParametersMWRequest request = RemittanceMWRequestFixture.withDefaultGeneralParameters();
        ApiDataResponse<ListGeneralParametersMWResponse> expectedResponse = ApiDataResponse.of(RemittanceMWResponseFixture.withDefaultGeneralParameters());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        ListGeneralParametersMWResponse response = provider.getGeneralParameters(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(RemittanceMWResponseFixture.withDefaultGeneralParameters());
    }

    @Test
    @DisplayName("Validate account given personId and accountId")
    void givenValidDataWhenValidateAccountThenExpectResponse() throws IOException {
        // Arrange
        ValidateAccountMWResponse expectedResponse = RemittanceMWResponseFixture.withDefaultValidateAccount();
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        ValidateAccountMWResponse response = provider.validateAccount(RemittanceMWRequestFixture.withDefaultValidateAccount());

        // Assert
        assertNotNull(response);
        assertEquals(response.getCodeError(), expectedResponse.getCodeError());
    }

    @Test
    @DisplayName("Get money orders sent given personId")
    void givenValidDataWhenGetMoneyOrdersSentThenExpectResponse() throws IOException {
        // Arrange
        MoneyOrderSentMWRequest request = RemittanceMWRequestFixture.withDefaultMoneyOrdersSent();
        MoneyOrderSentMWResponse expectedResponse = RemittanceMWResponseFixture.withDefaultMoneyOrdersSent();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        MoneyOrderSentMWResponse response = provider.getMoneyOrdersSent(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Check remittance given personId and remittanceId")
    void givenValidDataWhenCheckRemittanceThenExpectResponse() throws IOException {
        // Arrange
        CheckRemittanceMWRequest request = RemittanceMWRequestFixture.withDefaultCheckRemittance();
        CheckRemittanceMWResponse expectedResponse = RemittanceMWResponseFixture.withDefaultCheckRemittance();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        CheckRemittanceMWResponse response = provider.checkRemittance(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Retorna una lista vacía cundo se intenta consultar una remesa con datos válidos")
    void givenValidDataWhenCheckRemittanceThenErrorRM001Response() throws IOException {
        // Arrange
        CheckRemittanceMWRequest request = RemittanceMWRequestFixture.withDefaultCheckRemittance();
        String jsonResponse = Util.objectToString(RemittanceMWResponseFixture.withErrorRM001());
        stubFor(post(anyUrl()).willReturn(badRequest().withBody(jsonResponse)));

        // Act
        CheckRemittanceMWResponse response = provider.checkRemittance(request);

        //Assert
        assertNull(response.getData());
    }

    @Test
    @DisplayName("Deposit remittance given personId and remittanceId")
    void givenValidDataWhenDepositRemittanceThenExpectResponse() throws IOException {
        // Arrange
        DepositRemittanceMWRequest request = RemittanceMWRequestFixture.withDefaultDepositRemittance();
        DepositRemittanceMWResponse expectedResponse = RemittanceMWResponseFixture.withDefaultDepositRemittance();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        DepositRemittanceMWResponse response = provider.depositRemittance(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("List remittances Wester Union.")
    void testConsultWURemittance() throws IOException {
        // Arrange
        ConsultWURemittanceMWRequest request = RemittanceMWRequestFixture.withDefaultConsultWURemittance();
        ConsultWURemittanceMWResponse  expectedResponse = RemittanceMWResponseFixture.withDefaultConsultWURemittance();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        ConsultWURemittanceMWResponse  response = provider.consultWURemittance(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Update remittance Wester Union.")
    void testUpdateWURemittance() throws IOException {
        // Arrange
        UpdateWURemittanceMWRequest request = RemittanceMWRequestFixture.withDefaultUpdateWURemittance();
        UpdateWURemittanceMWResponse  expectedResponse = RemittanceMWResponseFixture.withDefaultUpdateWURemittance();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        UpdateWURemittanceMWResponse  response = provider.updateWURemittance(request);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
