package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.Pcc01Data;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.implementations.TransferMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.pcc01.Pcc01Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Pcc01MiddlewareProviderTest {
    @Spy
    @InjectMocks
    TransferMiddlewareProvider pcc01MiddlewareProvider;

    @Mock
    ITokenMiddlewareProvider tokenMiddlewareProviderMock;

    @Mock
    MiddlewareConfig middlewareConfigMock;

    @Mock
    IHttpClientFactory httpClientFactoryMock;

    @Mock
    CloseableHttpClient closeableHttpClientMock;

    @Mock
    CloseableHttpResponse closeableHttpResponseMock;

    @Mock
    HttpEntity httpEntityMock;

    @Mock
    StatusLine statusLineMock;

    private static Pcc01Request pcc01Request;

    private static ClientToken clientToken;

    static String project;
    static String clientSecret;
    static String headerKeyToke;

    @BeforeEach
    void setup() {
        pcc01MiddlewareProvider = new TransferMiddlewareProvider(tokenMiddlewareProviderMock, httpClientFactoryMock, Pcc01Mapper.INSTANCE, middlewareConfigMock);
        pcc01Request = new Pcc01Request();
        pcc01Request.setCurrency("840");
        pcc01Request.setAmount(10.00);
        clientToken = new ClientToken();
        clientToken.setAccessToken("fglkjhdfg9d87fgd98f09gd");
        clientToken.setExpiresIn(1234);
        project = "/manager";
        clientSecret = "2s4f2s4d3f4sd";
        headerKeyToke = "secret";
    }


    @Test
    void givenPersonIdWhenRequestGetAccountsThenListOwnAccounts() throws IOException {
        // Arrange
        Pcc01Response pcc01Response = new Pcc01Response();
        Pcc01Data pcc01Data = new Pcc01Data();
        pcc01Response.setData(pcc01Data);
        ObjectMapper objectMapperMWResponse = new ObjectMapper();
        String jsonAccountsResponseMock = objectMapperMWResponse.writeValueAsString(pcc01Response);
        InputStream accountsResponseMock = new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(),any(),any())).thenReturn(clientToken);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(accountsResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        Pcc01Response response = pcc01MiddlewareProvider.validateControl(pcc01Request);

        // Assert
        Assertions.assertNotNull(response.getData());
    }


    @Test
    void givenStatusLineIs406WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(),any(),any())).thenReturn(clientToken);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(406);

        // Act
        assertThrows(RuntimeException.class, () -> pcc01MiddlewareProvider.validateControl(pcc01Request));
    }

    @Test
    void givenStatusLineIs500WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(),any(),any())).thenReturn(clientToken);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        assertThrows(RuntimeException.class, () -> pcc01MiddlewareProvider.validateControl(pcc01Request));
    }
}