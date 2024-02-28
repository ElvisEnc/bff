package bg.com.bo.bff.providers;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.providers.mappings.login.LoginMWMapper;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.application.exceptions.NotHandledResponseException;
import bg.com.bo.bff.application.exceptions.UnauthorizedException;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.implementations.LoginMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWResponse;
import bg.com.bo.bff.providers.dtos.responses.login.UserMWResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class LoginMiddlewareProviderTests {
    static IHttpClientFactory httpClientFactoryMock;

    static LoginRequest loginRequest;

    static LoginMiddlewareProvider loginMiddlewareProvider;

    private String url = "http://login.fake.api";

    private String complementToken = "/oauth/token";

    private String clientSecret = "";

    private String urlComplement = "/bs/v1/users/validate-credentials";

    @BeforeAll
    public static void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setComplemento("a1");
        loginRequest.setPassword("1q01a");

        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
    }

    @BeforeEach
    public void init() {
        loginMiddlewareProvider = new LoginMiddlewareProvider(httpClientFactoryMock, LoginMWMapper.INSTANCE);

        //Propiedades cargadas por reflection dentro de LoginServices.
        ReflectionTestUtils.setField(loginMiddlewareProvider, "url", url);
        ReflectionTestUtils.setField(loginMiddlewareProvider, "complementToken", complementToken);
        ReflectionTestUtils.setField(loginMiddlewareProvider, "clientSecret", "");
        ReflectionTestUtils.setField(loginMiddlewareProvider, "complementLogin", urlComplement);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
    }

    @Test
    void givenCorrectCredentialsWhenLoginThenReturnSuccess() throws IOException {
        // Arrange
        String personId = "1";
        String accessTokenMW = UUID.randomUUID().toString();

        UserMWResponse user = new UserMWResponse();
        user.setPersonId(personId);
        LoginMWResponse loginMWResponse = new LoginMWResponse();
        loginMWResponse.setStatusCode("SUCCESS");
        loginMWResponse.setData(user);
        String jsonResponse = new ObjectMapper().writeValueAsString(loginMWResponse);

        stubFor(post(urlComplement).willReturn(okJson(jsonResponse)));

        // Act
        LoginValidationServiceResponse response = loginMiddlewareProvider.validateCredentials(accessTokenMW, loginRequest);

        // Assert
        assert response.getStatusCode().equals("SUCCESS");
        assert response.getPersonId().equals(personId);
    }

    @Test
    void givenIncorrectCredentialsWhenLoginThenReturnUnauthorized() throws IOException {
        // Arrange
        String accessToken = UUID.randomUUID().toString();
        stubFor(post(urlComplement).willReturn(unauthorized()));

        // Act
        UnauthorizedException unauthorizedException = assertThrows(UnauthorizedException.class, () -> loginMiddlewareProvider.validateCredentials(accessToken, loginRequest));

        // Assert
        assert unauthorizedException.getMessage().equals(HttpStatus.UNAUTHORIZED.name());
    }

    @Test
    void givenExceptionWhenRequestLoginServiceThenReturnsExceptionInternalServerError() throws IOException {
        // Arrange
        String accessTokenMW = UUID.randomUUID().toString();
        stubFor(post(urlComplement).willReturn(serverError()));

        // Act
        Exception exception = assertThrows(Exception.class, () -> loginMiddlewareProvider.validateCredentials(accessTokenMW, loginRequest));

        // Assert
        assertInstanceOf(NotHandledResponseException.class, exception);
    }
}
