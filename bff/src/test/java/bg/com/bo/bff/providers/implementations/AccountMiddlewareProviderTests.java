package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.UpdateTransactionLimitMWRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.models.Account;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.models.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.TransactionLimitListMWResponse;
import bg.com.bo.bff.providers.dtos.request.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.dtos.response.TransactionLimitListMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.accounts.AccountListMWMetadata;
import bg.com.bo.bff.providers.dtos.response.accounts.AccountListMWResponse;
import bg.com.bo.bff.providers.dtos.response.accounts.TransactionLimitUpdateAccountResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.own.account.AccountListMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AccountMiddlewareProviderTests {

    @InjectMocks
    private AccountMiddlewareProvider accountMiddlewareService;
    @Mock
    private MiddlewareConfig middlewareConfig;

    @Mock
    private ITokenMiddlewareProvider tokenMiddlewareProvider;

    @Mock
    private IHttpClientFactory httpClientFactoryMock;

    @Mock
    private ClientToken clientToken;

    private  CloseableHttpClient closeableHttpClientMock;

    private CloseableHttpResponse closeableHttpResponseMock;
    private HttpEntity httpEntityMock;
    private StatusLine statusLineMock;
    private Map<String, String> parameters;

    private ObjectMapper objectMapperMWResponse = new ObjectMapper();

    @BeforeEach
    void setUp() {
         httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
         accountMiddlewareService = new AccountMiddlewareProvider(middlewareConfig,tokenMiddlewareProvider, httpClientFactoryMock, AccountListMapper.INSTANCE);
         closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
         closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
         httpEntityMock = Mockito.mock(HttpEntity.class);
         statusLineMock = Mockito.mock(StatusLine.class);

          parameters = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "127.0.0.1",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );
    }

    @Test
    void givenPersonIdWhenRequestGetAccountsThenListOwnAccounts() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";

        ClientMWToken clientTokenMock = new ClientMWToken();

        String json = objectMapperMWResponse.writeValueAsString(clientTokenMock);
        InputStream tokenResponseMock = new ByteArrayInputStream(json.getBytes());
        AccountListMWResponse accountListMWResponseMock = new AccountListMWResponse();
        Account account = new Account();
        List<Account> list = new ArrayList<>();
        list.add(account);
        AccountListMWMetadata mwMetadata = new AccountListMWMetadata();
        accountListMWResponseMock.setData(list);
        accountListMWResponseMock.setMeta(mwMetadata);

        String jsonAccountsResponseMock = objectMapperMWResponse.writeValueAsString(accountListMWResponseMock);
        InputStream accountsResponseMock = new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(accountsResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        AccountListResponse response = accountMiddlewareService.getAccounts("", personId, documenNumber);

        // Assert
        assertNotNull(response.getData());
    }

    @Test
    void givenThatErrorOcurredWhenCreatedClientThenRunTimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";

        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Test Catch General"));

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenThatErrorOcurredWhenClientRequestExecutedThenRequestException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenThrow(new RequestException("Test Request"));

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenStatusLineIs401WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(401);

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenStatusLineIs404WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(404);

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenStatusLineIs406WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(406);

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenStatusLineIs500WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGenericResponseSuccess() throws IOException {
        // Arrange
        String personId = "123456789";
        String accountId = "1234";
        UpdateTransactionLimitMWRequest request = UpdateTransactionLimitMWRequestFixture.withDefault();
        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientToken);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(any(HttpPut.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        //Act
        GenericResponse actual = accountMiddlewareService.updateTransactionLimit(personId, accountId, request, parameters);

        //Assert
        assertEquals(TransactionLimitUpdateAccountResponse.SUCCESS.getCode(),actual.getCode());
        assertEquals(TransactionLimitUpdateAccountResponse.SUCCESS.getMessage(),actual.getMessage());

    }

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGetTransactionLimitMWResponse() throws IOException {
        // Arrange
        String personId = "123456789";
        String accountId = "1234";
        TransactionLimitListMWResponse expected = TransactionLimitListMWResponseFixture.withDefault();
        String jsonAccountsResponseMock = objectMapperMWResponse.writeValueAsString(expected);
        InputStream accountsResponseMock = new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());
        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientToken);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(accountsResponseMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        //Act
        TransactionLimitListMWResponse actual = accountMiddlewareService.getTransactionLimit(personId, accountId, parameters);

        //Assert
        assertEquals(expected.getData().getType(),actual.getData().getType());
        assertEquals(expected.getData().getIdentifier(),actual.getData().getIdentifier());
        assertEquals(expected.getData().getTransactionPermitDay(),actual.getData().getTransactionPermitDay());
        assertEquals(expected.getData().getCurrencyCod(),actual.getData().getCurrencyCod());
        assertEquals(expected.getData().getAvailableTransaction(),actual.getData().getAvailableTransaction());
        assertEquals(expected.getData().getAvailableTransactionGroup(),actual.getData().getAvailableTransactionGroup());

    }

}
