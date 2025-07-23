package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.credit.card.*;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.*;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardMiddlewareResponse;
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
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class CreditCardProviderTest {
    private CreditCardProvider provider;
    TokenMiddlewareProvider tokenMiddlewareProviderMock;
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
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        when(httpServletRequest.getHeaderNames()).thenReturn(Collections.enumeration(Arrays.asList(
                DeviceMW.DEVICE_ID.getCode(),
                DeviceMW.DEVICE_IP.getCode(),
                DeviceMW.DEVICE_NAME.getCode(),
                DeviceMW.GEO_POSITION_X.getCode(),
                DeviceMW.GEO_POSITION_Y.getCode(),
                DeviceMW.APP_VERSION.getCode()
        )));
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        provider = new CreditCardProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, httpServletRequest);
        setField(provider, "middlewareConfig", middlewareConfig);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
    }

    @Test
    @DisplayName("Get list credit card and prepaid card for a user given PersonId")
    void givenPersonIdWhenGetCreditCardsAndPrepaidCardsThenExpectResponse() throws IOException {
        // Arrange
        ListCreditCardMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultListCreditCardMWResponse();
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        ListCreditCardMWResponse response = provider.getListCreditCard("123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Get details credit card for a user given PersonId and CardId")
    void givenPersonIdAndCardIdWhenGetDetailsCreditCardsThenExpectResponse() throws IOException {
        // Arrange
        DetailsCreditCardMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultDetailsCreditCardMWResponse();
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        DetailsCreditCardMWResponse response = provider.getDetailCreditCard("123", "123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Get details prepaid card for a user given PersonId and CardId")
    void givenPersonIdAndCardIdWhenGetDetailsPrepaidCardsThenExpectResponse() throws IOException {
        // Arrange
        DetailPrepaidCardMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultDetailPrepaidCardResponse();
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        DetailPrepaidCardMWResponse response = provider.getDetailPrepaidCard("123", "123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Change credit card status for a given cmsCardNumber")
    void givenRequestBlockCreditCardWhenBlockCreditCardsThenExpectResponse() throws IOException {
        // Arrange
        BlockCreditCardMWRequest mwRequest = CreditCardMWRequestFixture.withDefaultBlockCreditCardMWRequest();
        BlockCreditCardMWResponse responseMock = CreditCardMWResponseFixture.withDefaultBlockCreditCardMWResponse();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);
        String jsonResponse = Util.objectToString(ApiDataResponse.of(responseMock));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.blockCreditCard(mwRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenRequestBlockCreditCardWhenBlockCreditCardsThenErrorResponse() throws IOException {
        // Arrange
        BlockCreditCardMWRequest mwRequest = CreditCardMWRequestFixture.withDefaultBlockCreditCardMWRequest();
        BlockCreditCardMWResponse responseMock = CreditCardMWResponseFixture.withDefaultBlockCreditCardMWResponseError();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.ERROR_UPDATE_STATUS_LOCK);
        String jsonResponse = Util.objectToString(ApiDataResponse.of(responseMock));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.blockCreditCard(mwRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Get available credit card for a user given cmsCard")
    void givenCmsCardWhenGetAvailableCreditCardsThenExpectResponse() throws IOException {
        // Arrange
        AvailableCreditCardMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultAvailableCreditCardMWResponse();
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        AvailableCreditCardMWResponse response = provider.getAvailable("123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Get payment periods credit card")
    void getPaymentPeriodsCreditCardsThenExpectResponse() throws IOException {
        // Arrange
        PeriodCreditCardMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultPeriods();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        PeriodCreditCardMWResponse response = provider.getPaymentPeriods();

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void givenCmsAccountNumberAndAmountWhenGetCashAdvanceFeeThenExpectResponse() throws IOException {
        // Arrange
        CashAdvanceFeeMWRequest mwRequest = CreditCardMWRequestFixture.withDefaultCashAdvanceFeeMWRequest();
        CashAdvanceFeeMWResponse expectedResponse = CreditCardMWResponseFixture.createCashAdvanceFee();
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        CashAdvanceFeeMWResponse response = provider.getCashAdvanceFee(mwRequest);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Get list credit card from linkser for a user given cmsAccount")
    void givenPersonIdAndCmsAccountWhenGetListCreditCardThenExpectResponse() throws IOException {
        // Arrange
        LinkserCreditCardMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultLinkserCreditCardMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        LinkserCreditCardMWResponse response = provider.getCreditCardsFromLinkser("123","123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Make the cash advance for a given account number")
    void givenCashAdvanceRequestWhenCashAdvanceThenExpectResponse() throws IOException {
        // Arrange
        CashAdvanceMWRequest mwRequest = CreditCardMWRequestFixture.withDefaultCashAdvanceMWRequest();
        CashAdvanceMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultCashAdvanceMWResponse();
        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        CashAdvanceMWResponse response = provider.cashAdvance(mwRequest);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Get credit card statements")
    void givenCmsCardWhenGetListStatementsThenExpectResponse() throws IOException {
        // Arrange
        CreditCardStatementsMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultCreditCardStatementsMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        CreditCardStatementsMWResponse response = provider.getStatements("123","123","123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Get list purchase authorizations for a given cmsCard and personId")
    void givenCmsCardAndPersonIdWhenGetListPurchaseAuthThenExpectResponse() throws IOException {
        // Arrange
        PurchaseAuthMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultPurchaseAuthMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        PurchaseAuthMWResponse response = provider.getListPurchaseAuth("123","123");

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Authorization credit card for purchases online")
    void givenRequestAuthorizationCreditCardWhenAuthorizationCreditCardsThenExpectResponse() throws IOException {
        // Arrange
        AuthorizationCreditCardMWRequest mwRequest = CreditCardMWRequestFixture.withDefaultAuthorizationCreditCardMWRequest();
        AuthorizationCreditCardMWResponse responseMock = CreditCardMWResponseFixture.withDefaultAuthorizationCreditCardMWResponse();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_AUTHORIZATION_ONLINE);

        String jsonResponse = Util.objectToString(ApiDataResponse.of(responseMock));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.authorizationCreditCard(mwRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenRequestAuthorizationCreditCardWhenAuthorizationCreditCardsThenErrorResponse() throws IOException {
        // Arrange
        AuthorizationCreditCardMWRequest mwRequest = CreditCardMWRequestFixture.withDefaultAuthorizationCreditCardMWRequest();
        AuthorizationCreditCardMWResponse responseMock = CreditCardMWResponseFixture.withDefaultAuthorizationCreditCardMWErrorResponse();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.ERROR_AUTHORIZATION_ONLINE);

        String jsonResponse = Util.objectToString(ApiDataResponse.of(responseMock));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.authorizationCreditCard(mwRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Comission prepaid card")
    void givenRequestComissionPrepaidCardWhenComissionPrepaidCardThenExpectResponse() throws IOException {
        // Arrange
        FeePrepaidCardMWRequest mwRequest = CreditCardMWRequestFixture.withDefaultComissionPrepaidCardMWRequest();
        FeePrepaidCardMWResponse expectedResponse = CreditCardMWResponseFixture.withDefaultComissionPrepaidCardMWResponse();

        String jsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        FeePrepaidCardMWResponse response = provider.getFeePrepaidCard(mwRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getInsuranceAmount(), response.getInsuranceAmount());
    }
}