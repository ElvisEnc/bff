package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccountResponseFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.ValidateAccountResponse;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.*;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.ThirdAccountMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AddAccountMWResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;
import bg.com.bo.bff.providers.models.enums.middleware.third.account.ThirdAccountMiddlewareResponse;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class ThirdAccountMiddlewareProviderTests {
    private ThirdAccountMiddlewareProvider provider;
    TokenMiddlewareProvider tokenMiddlewareProviderMock;
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
        provider = new ThirdAccountMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        setField(provider, "middlewareConfig", middlewareConfig);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
    }

    // Validate Third Account
    @Test
    void givenAccountNumberAndClientNameWhenGetValidateDestinationAccountThenValidateAccountResponse() throws IOException {
        // Arrange
        ValidateAccountResponse responseExpected = DestinationAccountResponseFixture.withDefaultValidateAccountResponse();
        String jsonResponse = Util.objectToString(responseExpected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        ValidateAccountResponse response = provider.validateAccount("123", "persona", map);

        // Assert
        assertNotNull(response);
        assertEquals(response, responseExpected);
    }

    // Add Accounts
    @Test
    void givenValidaDataWhenAddThirdAccountThenReturnOk() throws IOException {
        // Arrange
        AddThirdAccountBasicRequest request = ThirdAccountMWRequestFixture.withDefaultOKAddThirdAccountBasicRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ThirdAccountMWResponseFixture.withDefaultAddAccountMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.addThirdAccount(request, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(ThirdAccountMiddlewareResponse.SUCCESS_ADD_ACCOUNT));
    }

    @Test
    void givenValidaDataWhenAddWalletAccountThenReturnOk() throws IOException {
        // Arrange
        AddWalletAccountBasicRequest request = ThirdAccountMWRequestFixture.withDefaultOKAddWalletAccountBasicRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ThirdAccountMWResponseFixture.withDefaultAddAccountMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.addWalletAccount(request, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(ThirdAccountMiddlewareResponse.SUCCESS_ADD_ACCOUNT));
    }

    @Test
    void givenValidaDataWhenAddThirdAccountThenReturnError() throws IOException {
        // Arrange
        AddThirdAccountBasicRequest request = ThirdAccountMWRequestFixture.withDefaultOKAddThirdAccountBasicRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ApiDataResponse.of(new AddAccountMWResponse());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.addThirdAccount(request, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(ThirdAccountMiddlewareResponse.ERROR_ADD_ACCOUNT));
    }

    @Test
    void givenValidaDataWhenAddWalletAccountThenReturnError() throws IOException {
        // Arrange
        AddWalletAccountBasicRequest request = ThirdAccountMWRequestFixture.withDefaultOKAddWalletAccountBasicRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ApiDataResponse.of(new AddAccountMWResponse());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.addWalletAccount(request, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(ThirdAccountMiddlewareResponse.ERROR_ADD_ACCOUNT));
    }

    // Delete Accounts
    @Test
    void givenValidaDataWhenDeleteThirdAccountThenReturnOk() throws IOException {
        // Arrange
        DeleteThirdAccountMWRequest request = ThirdAccountMWRequestFixture.withDefaultDeleteThirdAccountMWRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ThirdAccountMWResponseFixture.withDefaultAddAccountMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.deleteThirdAccount(request, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(ThirdAccountMiddlewareResponse.SUCCESS_DELETE_ACCOUNT));
    }

    @Test
    void givenValidaDataWhenDeleteWalletAccountThenReturnOk() throws IOException {
        // Arrange
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ThirdAccountMWResponseFixture.withDefaultAddAccountMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.deleteWalletAccount("123", 123, 123, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(ThirdAccountMiddlewareResponse.SUCCESS_DELETE_ACCOUNT));
    }

    @Test
    void givenValidaDataWhenDeleteThirdAccountThenReturnError() throws IOException {
        // Arrange
        DeleteThirdAccountMWRequest request = ThirdAccountMWRequestFixture.withDefaultDeleteThirdAccountMWRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ApiDataResponse.of(new AddAccountMWResponse());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.deleteThirdAccount(request, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(ThirdAccountMiddlewareResponse.ERROR_DELETE_ACCOUNT));
    }

    @Test
    void givenValidaDataWhenDeleteWalletAccountThenReturnError() throws IOException {
        // Arrange
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ApiDataResponse.of(new AddAccountMWResponse());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.deleteWalletAccount("123", 123, 123, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(ThirdAccountMiddlewareResponse.ERROR_DELETE_ACCOUNT));
    }

    // Get Third Accounts
    @Test
    void givePersonCodeWhenGetThirdAccountsThenSuccess() throws IOException {
        // Arrange
        ThirdAccountsMWResponse responseExpected = ThirdAccountMWResponseFixture.withDefaultThirdAccountListMWResponse();
        String jsonExpected = Util.objectToString(responseExpected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonExpected)));

        // Act
        ThirdAccountsMWResponse response = provider.getThirdAccounts("123", new HashMap<>());

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(responseExpected);
    }

    // Get Wallet Accounts
    @Test
    void givePersonCodeWhenGetWalletsAccountsThenSuccess() throws IOException {
        // Arrange
        ThirdAccountsMWResponse responseExpected = ThirdAccountMWResponseFixture.withDefaultThirdAccountListMWResponse();
        String jsonExpected = Util.objectToString(responseExpected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonExpected)));

        // Act
        ThirdAccountsMWResponse response = provider.getWalletAccounts("123", new HashMap<>());

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(responseExpected);
    }
}