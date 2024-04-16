package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.requests.AddThirdAccountBasicRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.implementations.DestinationAccountProvider;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class DestinationAccountProviderTest {

    @Spy
    @InjectMocks
    private  DestinationAccountProvider provider;

    @Mock
    private  IHttpClientFactory httpClientFactory;

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

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(provider, "url", "http://localhost");
        ReflectionTestUtils.setField(provider, "complementToken", "/third-accounts-manager");
        ReflectionTestUtils.setField(provider, "complementThirdAccounts", "/third-accounts-manager/bs/v1");
        ReflectionTestUtils.setField(provider, "clientSecret", "db");
    }
    @Test
    void addThirdAccount() throws IOException {
        final String token= "1212121";
        final AddThirdAccountBasicRequest request = AddThirdAccountBasicRequestFixture.withDefaultOK();

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);



        GenericResponse response = provider.addThirdAccount(token, request, new HashMap<>());
        assertNotNull(response);

    }
}