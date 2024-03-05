package bg.com.bo.bff.services.v1;

import bg.com.bo.bff.models.*;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.mappings.third.account.ThirdAccountListMapper;
import bg.com.bo.bff.providers.implementations.ThirdAccountMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.responses.ThirdAccountListMWResponse;
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
public class ThirdAccountMiddlewareProviderTests {
    @Autowired
    private ThirdAccountMiddlewareProvider thirdAccountMiddlewareService;

    @Test
    void givenPersonIdCompanyWhenRequestGetThirdAccountsThenListThirdAccounts()throws IOException{
        // Arrange
        String personId="123456";
        String company="123456";
        IHttpClientFactory httpClientFactoryMock= Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService=new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock=Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        HttpEntity httpGetEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        ThirdAccountListMWResponse accountListMWResponseMock=new ThirdAccountListMWResponse();
        ThirdAccount account= new ThirdAccount();
        List<ThirdAccount> list =new ArrayList<>();
        list.add(account);
        accountListMWResponseMock.setData(list);
        ObjectMapper objectMapperMWResponse=new ObjectMapper();
        String jsonAccountsResponseMock=objectMapperMWResponse.writeValueAsString(accountListMWResponseMock);
        InputStream accountsResponseMock=new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());

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
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE);

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
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE);
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
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE);
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
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE);
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
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE);
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
        thirdAccountMiddlewareService = new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE);
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
}
