package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.OwnAccountMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.*;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.models.enums.middleware.own.account.OwnAccountsMiddlewareResponse;
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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class OwnAccountMiddlewareProviderTest {
    private OwnAccountsMiddlewareProvider provider;
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();
    private Map<String, String> map;

    @BeforeEach
    void setUp() throws IOException {
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
        provider = new OwnAccountsMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        setField(provider, "middlewareConfig", middlewareConfig);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
    }

    // Get own accounts
    @Test
    void givenPersonIdWhenRequestGetAccountsThenListOwnAccounts() throws IOException {
        // Arrange
        OwnAccountsListMWResponse expectedResponse = OwnAccountMWResponseFixture.withDefaultOwnAccountsListMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        OwnAccountsListMWResponse response = provider.getAccounts("123", "123", map);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    // Update transaction limits
    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGenericResponseSuccess() throws IOException {
        // Arrange
        UpdateTransactionLimitMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultUpdateTransactionLimitMWRequest();
        UpdateLimitMWResponse responseMock = OwnAccountMWResponseFixture.withDefaultUpdateLimitMWResponse();
        String jsonResponse = Util.objectToString(responseMock);
        stubFor(put(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        GenericResponse response = provider.updateTransactionLimit("123", "123", requestMock, map);

        //Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(OwnAccountsMiddlewareResponse.SUCCESS_UPDATE_TRANSACTION_LIMIT));
    }

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenResponseError() throws IOException {
        // Arrange
        UpdateTransactionLimitMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultUpdateTransactionLimitMWRequest();
        UpdateLimitMWResponse responseMock = new UpdateLimitMWResponse(new UpdateLimitMWResponse.UpdateLimitMW());
        String jsonResponse = Util.objectToString(responseMock);
        stubFor(put(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        GenericResponse response = provider.updateTransactionLimit("123", "123", requestMock, map);

        //Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(OwnAccountsMiddlewareResponse.ERROR_UPDATE_TRANSACTION_LIMIT));
    }

    // Get Transaction Limits
    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGetTransactionLimitMWResponse() throws IOException {
        // Arrange
        TransactionLimitsMWResponse responseExpected = OwnAccountMWResponseFixture.withDefaultTransactionLimitsMWResponse();
        String jsonResponse = Util.objectToString(ApiDataResponse.of(responseExpected));
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        TransactionLimitsMWResponse response = provider.getTransactionLimit("123", "123", map);

        //Assert
        assertNotNull(response);
        assertEquals(response, responseExpected);
    }

    // Get Account Statement List
    @Test
    void givenAccountStatementRequestWhenGetAccountStatementsThenReturnAccountStatementList() throws IOException {
        // Arrange
        AccountStatementsMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultAccountStatementsMWRequest();
        AccountStatementsMWResponse responseExpected = OwnAccountMWResponseFixture.withDefaultAccountReportBasicResponse();
        String jsonResponse = Util.objectToString(responseExpected);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        AccountStatementsMWResponse response = provider.getAccountStatements(requestMock, map);

        //Assert
        assertNotNull(response);
        assertEquals(response, responseExpected);
    }
}
