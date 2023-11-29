package bg.com.bo.bff.services;

import bg.com.bo.bff.model.LoginRequest;
import bg.com.bo.bff.model.LoginResponse;
import bg.com.bo.bff.model.UnauthorizedException;
import bg.com.bo.bff.model.User;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
public class LoginServicesTests {
    @Autowired
    private LoginService loginService;

    static LoginRequest loginRequest;

    @BeforeAll
    public static void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setComplemento("a1");
        loginRequest.setPassword("1q01a");
        loginRequest.setCanal("GANAMOVIL");
    }

    @Test
    void givenCorrectCredentialsWhenLoginThenReturnSuccess() throws IOException {
        // Arrange
        loginRequest.setCedula("1324501");
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        loginService = new LoginService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        User user = new User(123456000);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setStatusCode("SUCCESS");
        loginResponse.setData(user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(loginResponse);
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        LoginResponse response = (LoginResponse) loginService.loginRequest(loginRequest);

        assert response.getStatusCode().equals("SUCCESS");
    }

    @Test
    void givenIncorrectCredentialsWhenLoginThenReturnUnauthorized() throws IOException {
        // Arrange
        loginRequest.setCedula("0000");
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        loginService = new LoginService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock=Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(401);

        // Act
        UnauthorizedException unauthorizedException = assertThrows(UnauthorizedException.class, () -> loginService.loginRequest(loginRequest));

        // Assert
        assert unauthorizedException.getMessage().equals(HttpStatus.UNAUTHORIZED.name());
    }
}
