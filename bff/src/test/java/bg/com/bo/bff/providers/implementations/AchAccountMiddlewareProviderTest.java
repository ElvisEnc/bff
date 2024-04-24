package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.requests.AddAchAccountBasicRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.commons.HttpDeleteWithBody;
import bg.com.bo.bff.commons.enums.response.DeleteThirdAccountResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.BanksMWResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.responses.BanksMWResponseFixture;
import bg.com.bo.bff.providers.mappings.ach.account.AchAccountMWtMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;


@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class AchAccountMiddlewareProviderTest {

    @Mock
    private  TokenMiddlewareProvider tokenMiddlewareProvider;
    @Mock
    private  MiddlewareConfig middlewareConfig;

    @Mock
    private  IHttpClientFactory httpClientFactoryMock;


    @Mock
    private ClientToken clientToken;

    @InjectMocks
    private AchAccountMiddlewareProvider provider;

    @Mock
    CloseableHttpClient closeableHttpClientMock;
    @Mock
    CloseableHttpResponse closeableHttpResponseMock;
    @Mock
    HttpEntity httpEntityMock;
    @Mock
    StatusLine statusLineMock;

    private String token;


    @Mock
    private AchAccountMWtMapper mapper;

    @BeforeEach
    void setUp() {


        ReflectionTestUtils.setField(provider, "url", "http://localhost");
        ReflectionTestUtils.setField(provider, "complementToken", "/ach-accounts-manager");
        ReflectionTestUtils.setField(provider, "complementAchAccounts", "/ach-accounts-manager/bs/v1");
        ReflectionTestUtils.setField(provider, "clientSecret", "db");
        this.token = "1212h1jk2h1k2h1kjh2121";
    }


    @Test
    void givenValidaDataWhenAddAchAccountThenReturnOk() throws IOException {
        // Arrange

        AddAchAccountBasicRequest request = AddAchAccountBasicRequestFixture.withDefault();

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        GenericResponse response = provider.addAchAccount(token, request, new HashMap<>());

        // Assert
        assertNotNull(response);
        Mockito.verify(httpClientFactoryMock).create();
        Mockito.verify(closeableHttpClientMock).execute(any(HttpPost.class));
        Mockito.verify(closeableHttpResponseMock).getEntity();
        Mockito.verify(closeableHttpResponseMock).getStatusLine();
        Mockito.verify(statusLineMock).getStatusCode();
    }

    @Test
    void givenUrlMDWhenRequestGetBanksThenBanksMWResponse() throws IOException {
        // ArrangeCloseableHttpClient
        BanksMWResponse expected = BanksMWResponseFixture.withDefault();
        ObjectMapper objectMapperMWResponse = new ObjectMapper();
        String jsonAccountsResponseMock = objectMapperMWResponse.writeValueAsString(expected);
        InputStream accountsResponseMock = new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());
        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientToken);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        HttpEntity httpGetEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getEntity()).thenReturn(httpGetEntityMock);
        Mockito.when(httpGetEntityMock.getContent()).thenReturn(accountsResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        BanksMWResponse actual = provider.getBanks();

        // Assert
        assertEquals(expected.getData().size(),actual.getData().size());
        assertEquals(expected.getData().get(0).getCode(),actual.getData().get(0).getCode());
        assertEquals(expected.getData().get(0).getDescription(),actual.getData().get(0).getDescription());
        assertEquals(expected.getData().get(0).getAcronym(),actual.getData().get(0).getAcronym());
    }
    @Test
    void givenValidaDataWhenDeleteAccountThenReturnOk() throws IOException {
        // Arrange

        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientToken);
        GenericResponse expectedResponse = GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
        ObjectMapper objectMapperMWResponse = new ObjectMapper();
        String jsonAccountsResponseMock = objectMapperMWResponse.writeValueAsString(expectedResponse);
        InputStream accountsResponseMock = new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());
        Mockito.when(tokenMiddlewareProvider.generateAccountAccessToken(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(clientToken);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        HttpEntity httpGetEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getEntity()).thenReturn(httpGetEntityMock);
        Mockito.when(httpGetEntityMock.getContent()).thenReturn(accountsResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        GenericResponse response = provider.deleteAchAccount("1", 1, "1", "127.0.0.1");

        // Assert
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
    }
}