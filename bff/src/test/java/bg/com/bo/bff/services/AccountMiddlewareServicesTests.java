package bg.com.bo.bff.services;

import bg.com.bo.bff.model.*;
import bg.com.bo.bff.model.exceptions.RequestException;
import bg.com.bo.bff.model.interfaces.AccountListMapper;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.services.v1.AccountMiddlewareService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AccountMiddlewareServicesTests {
    @Autowired
    private AccountMiddlewareService accountMiddlewareService;

    @Test
    void givenPersonIdWhenRequestGetAccountsThenListOwnAccounts() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountMiddlewareService = new AccountMiddlewareService(httpClientFactoryMock, AccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpPostResponseMock = Mockito.mock(CloseableHttpResponse.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        HttpEntity httpPostEntityMock = Mockito.mock(HttpEntity.class);
        HttpEntity httpGetEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        ClientToken clientTokenMock = new ClientToken();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientTokenMock);
        InputStream tokenResponseMock = new ByteArrayInputStream(json.getBytes());

        AccountListMWResponse accountListMWResponseMock = new AccountListMWResponse();
        Account account = new Account();
        List<Account> list = new ArrayList<>();
        list.add(account);
        AccountListMWMetadata mwMetadata = new AccountListMWMetadata();
        accountListMWResponseMock.setData(list);
        accountListMWResponseMock.setMeta(mwMetadata);
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
        AccountListResponse response = accountMiddlewareService.getAccounts("", personId, documenNumber);

        // Assert
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void givenThatErrorOcurredWhenCreatedClientThenRunTimeException()throws IOException{
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountMiddlewareService = new AccountMiddlewareService(httpClientFactoryMock, AccountListMapper.INSTANCE);

        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Test Catch General"));

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenThatErrorOcurredWhenClientTokenExecutedThenRequestException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountMiddlewareService = new AccountMiddlewareService(httpClientFactoryMock, AccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenThrow(new RequestException("Test RequestException"));

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenThatErrorOcurredWhenClientRequestExecutedThenRequestException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountMiddlewareService = new AccountMiddlewareService(httpClientFactoryMock, AccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);

        ClientToken clientTokenMock = new ClientToken();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientTokenMock);

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
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountMiddlewareService = new AccountMiddlewareService(httpClientFactoryMock, AccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        ClientToken clientTokenMock = new ClientToken();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientTokenMock);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(401);

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenStatusLineIs404WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountMiddlewareService = new AccountMiddlewareService(httpClientFactoryMock, AccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        ClientToken clientTokenMock = new ClientToken();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientTokenMock);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(404);

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenStatusLineIs406WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountMiddlewareService = new AccountMiddlewareService(httpClientFactoryMock, AccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        ClientToken clientTokenMock = new ClientToken();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientTokenMock);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(406);

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }

    @Test
    void givenStatusLineIs500WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        String personId = "123456789";
        String documenNumber = "1234";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountMiddlewareService = new AccountMiddlewareService(httpClientFactoryMock, AccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        ClientToken clientTokenMock = new ClientToken();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientTokenMock);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        assertThrows(RuntimeException.class, () -> accountMiddlewareService.getAccounts("", personId, documenNumber));
    }
}
