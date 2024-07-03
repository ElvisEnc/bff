package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.request.destination.account.DestinationAccountRequestFixture;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccountResponseFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccount;
import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccountListResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.ValidateAccountResponse;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.response.DeleteThirdAccountResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.*;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.mappings.providers.account.ThirdAccountListMapper;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountListMWResponse;
import bg.com.bo.bff.mappings.providers.account.ThirdAccountMWtMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class ThirdAccountMiddlewareProviderTests {
    @Mock
    private ITokenMiddlewareProvider tokenMiddlewareProvider;
    @Mock
    private MiddlewareConfig middlewareConfig;
    @Mock
    private IHttpClientFactory httpClientFactoryMock;
    @Autowired
    private ThirdAccountMiddlewareProvider thirdAccountMiddlewareService;
    @Mock
    private ThirdAccountMWtMapper thirdAccountMWtMapper;
    @Mock
    private ClientToken clientTokenMock;
    @Mock
    private ThirdAccountMWtMapper mapper;
    @InjectMocks
    private ThirdAccountMiddlewareProvider provider;
    @Mock
    CloseableHttpClient closeableHttpClientMock;
    @Mock
    CloseableHttpResponse closeableHttpResponseMock;
    @Mock
    HttpEntity httpEntityMock;
    @Mock
    StatusLine statusLineMock;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(provider, "url", "http://localhost:8080");
        ReflectionTestUtils.setField(provider, "complementThirdAccounts", "/third-accounts-manager/bs/v1");
    }

    @Test
    void givenPersonIdCompanyWhenRequestGetThirdAccountsThenListThirdAccounts() throws IOException {
        // Arrange
        String personId = "123456";
        String company = "123456";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE, middlewareConfig, tokenMiddlewareProvider, thirdAccountMWtMapper);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        HttpEntity httpGetEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        ThirdAccountListMWResponse accountListMWResponseMock = new ThirdAccountListMWResponse();
        ThirdAccount account = new ThirdAccount();
        List<ThirdAccount> list = new ArrayList<>();
        list.add(account);
        accountListMWResponseMock.setData(list);
        ObjectMapper objectMapperMWResponse = new ObjectMapper();
        String jsonAccountsResponseMock = objectMapperMWResponse.writeValueAsString(accountListMWResponseMock);
        InputStream accountsResponseMock = new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getEntity()).thenReturn(httpGetEntityMock);
        Mockito.when(httpGetEntityMock.getContent()).thenReturn(accountsResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        ThirdAccountListResponse response = thirdAccountMiddlewareService.getListThirdAccounts("", personId, company);

        // Assert
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void givenThatErrorOcurredWhenCreatedClientThenRunTimeException() {
        // Arrange
        String personId = "123456789";
        String company = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE, middlewareConfig, tokenMiddlewareProvider, thirdAccountMWtMapper);

        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Test Catch General"));

        // Act
        assertThrows(RuntimeException.class, () -> thirdAccountMiddlewareService.getListThirdAccounts("", personId, company));
    }

    @Test
    void givenThatErrorOcurredWhenClientTokenExecutedThenRequestException() throws IOException {
        // Arrange
        String personId = "123456789";
        String company = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE, middlewareConfig, tokenMiddlewareProvider, thirdAccountMWtMapper);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenThrow(new RequestException("Test RequestException"));

        // Act
        assertThrows(RuntimeException.class, () -> thirdAccountMiddlewareService.getListThirdAccounts("", personId, company));
    }

    @Test
    void givenStatusLineIs401WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String company = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE, middlewareConfig, tokenMiddlewareProvider, thirdAccountMWtMapper);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(401);

        // Act
        assertThrows(RuntimeException.class, () -> thirdAccountMiddlewareService.getListThirdAccounts("", personId, company));
    }

    @Test
    void givenStatusLineIs404WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String company = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE, middlewareConfig, tokenMiddlewareProvider, thirdAccountMWtMapper);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(404);

        // Act
        assertThrows(RuntimeException.class, () -> thirdAccountMiddlewareService.getListThirdAccounts("", personId, company));
    }

    @Test
    void givenStatusLineIs406WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String company = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE, middlewareConfig, tokenMiddlewareProvider, thirdAccountMWtMapper);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(406);

        // Act
        assertThrows(RuntimeException.class, () -> thirdAccountMiddlewareService.getListThirdAccounts("", personId, company));
    }

    @Test
    void givenStatusLineIs500WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String company = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE, middlewareConfig, tokenMiddlewareProvider, thirdAccountMWtMapper);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        assertThrows(RuntimeException.class, () -> thirdAccountMiddlewareService.getListThirdAccounts("", personId, company));
    }

    @Test
    void givenValidaDataWhenDeleteAccountThenReturnOk() throws IOException {
        // Arrange
        String personId = "1";
        int identifier = 1;
        int accountNumber = 1;
        String deviceId = "1";
        String ip = "127.0.0.1";

        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        Mockito.when(clientTokenMock.getAccessToken()).thenReturn("");
        Mockito.when(middlewareConfig.getUrlBase()).thenReturn(MiddlewareConfigFixture.withDefault().getUrlBase());

        DeleteThirdAccountMWRequest request = new DeleteThirdAccountMWRequest();
        request.setAccountNumber(String.valueOf(accountNumber));
        request.setIdentifier(String.valueOf(identifier));
        request.setPersonId(personId);

        GenericResponse expectedResponse = GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
        String jsonResponse = Util.objectToString(expectedResponse);

        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.deleteThirdAccount(personId, identifier, accountNumber, deviceId, ip);

        // Assert
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
    }

    @Test
    void givenValidaDataWhenAddThirdAccountThenReturnOk() throws IOException {
        // Arrange
        final String token = "1212121";
        final AddThirdAccountBasicRequest request = DestinationAccountRequestFixture.withDefaultOKAddThirdAccountBasicRequest();

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        GenericResponse response = provider.addThirdAccount(token, request, new HashMap<>());

        // Assert
        assertNotNull(response);
        Mockito.verify(httpClientFactoryMock).create();
        Mockito.verify(closeableHttpClientMock).execute(Mockito.any(HttpPost.class));
        Mockito.verify(closeableHttpResponseMock).getEntity();
        Mockito.verify(closeableHttpResponseMock).getStatusLine();
        Mockito.verify(statusLineMock).getStatusCode();
    }

    @Test
    void givenValidaDataWhenAddWalletAccountThenReturnOk() throws IOException {
        // Arrange
        final String token = "1212121";
        final AddWalletAccountBasicRequest request = DestinationAccountRequestFixture.withDefaultOKAddWalletAccountBasicRequest();

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        GenericResponse response = provider.addWalletAccount(token, request, new HashMap<>());

        // Assert
        assertNotNull(response);
        Mockito.verify(httpClientFactoryMock).create();
        Mockito.verify(closeableHttpClientMock).execute(Mockito.any(HttpPost.class));
        Mockito.verify(closeableHttpResponseMock).getEntity();
        Mockito.verify(closeableHttpResponseMock).getStatusLine();
        Mockito.verify(statusLineMock).getStatusCode();
    }

    @Test
    void givenValidaDataWhenDeleteWalletAccountThenReturnOk() throws IOException {
        // Arrange
        String personId = "169494";
        int identifier = 7289842;
        int accountNumber = 77887845;
        String deviceId = "123";
        String ip = "192.0.0.1";

        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        Mockito.when(clientTokenMock.getAccessToken()).thenReturn("");
        Mockito.when(middlewareConfig.getUrlBase()).thenReturn(MiddlewareConfigFixture.withDefault().getUrlBase());

        DeleteThirdAccountMWRequest request = new DeleteThirdAccountMWRequest();
        request.setAccountNumber(String.valueOf(accountNumber));
        request.setIdentifier(String.valueOf(identifier));
        request.setPersonId(personId);

        GenericResponse expectedResponse = GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
        String jsonResponse = Util.objectToString(expectedResponse);

        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.deleteWalletAccount(personId, identifier, accountNumber, deviceId, ip);

        // Assert
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
    }

    @Test
    void givePersonCodeWhenGetThirdAccountsThenSuccess() throws IOException {
        // Arrange
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        Mockito.when(middlewareConfig.getUrlBase()).thenReturn(MiddlewareConfigFixture.withDefault().getUrlBase());
        String jsonExpected = Util.objectToString(DestinationAccountResponseFixture.withDefaultThirdAccountListResponse());
        stubFor(get(anyUrl()).willReturn(okJson(jsonExpected)));

        // Act
        ThirdAccountListResponse response = provider.getThirdAccounts(12345, "token", new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), DestinationAccountResponseFixture.withDefaultThirdAccountListResponse().getData());
    }

    @Test
    void givePersonCodeWhenGetWalletsAccountsThenSuccess() throws IOException {
        // Arrange
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        Mockito.when(middlewareConfig.getUrlBase()).thenReturn(MiddlewareConfigFixture.withDefault().getUrlBase());
        String jsonExpected = Util.objectToString(DestinationAccountResponseFixture.withDefaultThirdAccountListResponse());
        stubFor(get(anyUrl()).willReturn(okJson(jsonExpected)));

        // Act
        ThirdAccountListResponse response = provider.getWalletAccounts(12345, "token", new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), DestinationAccountResponseFixture.withDefaultThirdAccountListResponse().getData());
    }

    @Test
    void giveValidBankCodeWhenUnexpectedErrorOccursThenGenericException() throws IOException {
        // Arrange
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        Mockito.when(middlewareConfig.getUrlBase()).thenReturn(MiddlewareConfigFixture.withDefault().getUrlBase());
        ErrorMiddlewareProvider errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("MDWHDR-01")
                        .description("MDWHDR-01")
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse()
                .withStatus(400)
                .withBody(Util.objectToString(errorMiddlewareProvider))));

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            provider.getWalletAccounts(12345, "token", new HashMap<>());
        });

        // Assert
        assertEquals(exception.getMessage(), AppError.MDWHDR_01.getMessage());
    }

    @Test
    void giveValidBankCodeWhenUnexpectedErrorOccursThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));
        Mockito.when(middlewareConfig.getUrlBase()).thenReturn(MiddlewareConfigFixture.withDefault().getUrlBase());

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            provider.getWalletAccounts(12345, "token", new HashMap<>());
        });

        // Assert
        assertEquals("Internal server error.", exception.getMessage());
    }

    @Test
    void givenAccountNumberAndClientNameWhenGetValidateDestinationAccountThenValidateAccountResponse() throws IOException {
        // Arrange
        final String accountNumber = "1310766620";
        final String clientName = "BANCO";
        final ValidateAccountResponse expected = DestinationAccountResponseFixture.withDefaultValidateAccountResponse();
        final String jsonResponse = Util.objectToString(expected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));
        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientTokenMock);
        Mockito.when(clientTokenMock.getAccessToken()).thenReturn("");
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());

        // Act
        ValidateAccountResponse actual = provider.validateAccount(accountNumber, clientName, new HashMap<>());

        // Assert
        assertEquals(expected.getData(), actual.getData());
    }
}
