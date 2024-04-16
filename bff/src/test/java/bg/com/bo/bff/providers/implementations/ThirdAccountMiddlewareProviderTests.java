package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.commons.enums.response.DeleteThirdAccountResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.*;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.third.account.ThirdAccountListMapper;
import bg.com.bo.bff.providers.implementations.ThirdAccountMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.responses.ThirdAccountListMWResponse;
import bg.com.bo.bff.providers.mappings.third.account.ThirdAccountMWtMapper;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
public class ThirdAccountMiddlewareProviderTests {
    @Autowired
    private ThirdAccountMiddlewareProvider thirdAccountMiddlewareService;

    @Mock
    private MiddlewareConfig middlewareConfig;

    @Mock
    private ITokenMiddlewareProvider tokenMiddlewareProvider;

    @Mock
    private ThirdAccountMWtMapper thirdAccountMWtMapper;
    @Mock
    private IHttpClientFactory httpClientFactoryMock;
    @Mock
    private ClientToken clientToken;
    @Mock
    private ThirdAccountMWtMapper mapper;
    @InjectMocks
    private ThirdAccountMiddlewareProvider provider;

    @Test
    void givenPersonIdCompanyWhenRequestGetThirdAccountsThenListThirdAccounts()throws IOException{
        // Arrange
        String personId="123456";
        String company="123456";
        IHttpClientFactory httpClientFactoryMock= Mockito.mock(IHttpClientFactory.class);
        thirdAccountMiddlewareService=new ThirdAccountMiddlewareProvider(httpClientFactoryMock, ThirdAccountListMapper.INSTANCE, middlewareConfig, tokenMiddlewareProvider, thirdAccountMWtMapper);
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
        int accountId = 1;
        String deviceId = "1";
        String ip = "127.0.0.1";

        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientToken);
        Mockito.when(clientToken.getAccessToken()).thenReturn("");
        Mockito.when(middlewareConfig.getUrlBase()).thenReturn(MiddlewareConfigFixture.withDefault().getUrlBase());

        DeleteThirdAccountMWRequest request = new DeleteThirdAccountMWRequest();
        request.setAccountNumber(String.valueOf(accountId));
        request.setIdentifier(String.valueOf(identifier));
        request.setPersonId(personId);

        GenericResponse expectedResponse = GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
        String jsonResponse = Util.objectToString(expectedResponse);

        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.delete(personId, identifier, accountId, deviceId, ip);

        // Assert
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
    }
}
