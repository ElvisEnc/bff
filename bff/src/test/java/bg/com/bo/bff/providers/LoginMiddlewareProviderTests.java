package bg.com.bo.bff.providers;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.Device;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.implementations.LoginMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class LoginMiddlewareProviderTests {
    @Spy
    @InjectMocks
    LoginMiddlewareProvider loginMiddlewareProvider;
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
    @Mock
    MiddlewareConfig middlewareConfig;
    @Mock
    ITokenMiddlewareProvider tokenMiddlewareProvider;
    LoginRequest loginRequest;
    @Mock
    Device device;
    @Mock
    LoginMWFactorResponse loginMWFactorResponse;
    String ip;
    String accessTokenMW;

    @BeforeEach
    void init() {

        device = new Device();
        loginRequest = new LoginRequest();
        loginRequest.setType("sdf");
        loginRequest.setUser("dfsdf");
        loginRequest.setComplement("a1");
        loginRequest.setPassword("1q01a");
        loginRequest.setGeoReference("sdfdf");
        loginRequest.setDeviceIdentification(device);
        ip = "132.435.463.54";
        accessTokenMW = UUID.randomUUID().toString();
    }

    @Test
    void givenCorrectCredentialsWhenLoginThenReturnSuccess() throws IOException {
        // Arrange
        loginMWFactorResponse = new LoginMWFactorResponse();
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(loginMWFactorResponse);
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);

        // Act
        LoginMWFactorResponse response = loginMiddlewareProvider.validateUser(loginRequest, ip, accessTokenMW);

        // Assert
        assertNotNull(response);
    }
}
