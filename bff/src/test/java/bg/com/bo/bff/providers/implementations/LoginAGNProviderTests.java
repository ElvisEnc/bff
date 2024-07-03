package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryRequestFixture;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.CredentialsType;
import bg.com.bo.bff.commons.enums.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class LoginAGNProviderTests {

    private LoginAGNProvider loginAGNProvider;
    private IHttpClientFactory httpClientFactoryMock;
    private String agnLoginUrlServer = "http://login.fake.api";

    @BeforeEach
    public void init(){
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        loginAGNProvider = new LoginAGNProvider(httpClientFactoryMock);
        ReflectionTestUtils.setField(loginAGNProvider, "agnLoginUrlServer", agnLoginUrlServer);
    }

    @Test
    void givenValidPasswordCredentialsWhenLoginThenReturnTrue() throws IOException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();

        String xmlResponse = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/files/login.agn/ValidCredentialsByPasswordResponse.xml")), StandardCharsets.UTF_8);

        stubFor(get(urlPathEqualTo("/Service1.svc/mtdValidarClave4_N")).willReturn(okXml(xmlResponse)));

        // Act
        Boolean result = loginAGNProvider.login(request);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void givenValidBiometricCredentialsWhenLoginThenReturnTrue() throws IOException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();
        request.getCredentials().setType(CredentialsType.BIOMETRIC.getValue());

        String xmlResponse = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/files/login.agn/ValidCredentialsByBiometricsResponse.xml")), StandardCharsets.UTF_8);

        stubFor(get(urlPathEqualTo("/Service1.svc/mtdValidarIngresoxHD_N")).willReturn(okXml(xmlResponse)));

        // Act
        Boolean result = loginAGNProvider.login(request);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void givenNoHandledResponseWhenLoginThenReturnInternalServer() throws IOException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();

        stubFor(get(urlPathEqualTo("/Service1.svc/mtdValidarClave4_N")).willReturn(badRequest()));

        // Act
        Exception exception = assertThrows(Exception.class, () ->  loginAGNProvider.login(request));

        // Assert
        assertEquals(HandledException.class, exception.getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((HandledException) exception).getStatus());
        assertEquals(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR.getCode(), ((HandledException) exception).getCode());
        assertEquals(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR.getDescription(), ((HandledException) exception).getMessage());
    }

    @Test
    void givenInvalidPasswordCredentialsWhenLoginThenReturnFalse() throws IOException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();

        String xmlResponse = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/files/login.agn/InvalidCredentialsByPasswordResponse.xml")), StandardCharsets.UTF_8);

        stubFor(get(urlPathEqualTo("/Service1.svc/mtdValidarClave4_N")).willReturn(okXml(xmlResponse)));

        // Act
        Boolean result = loginAGNProvider.login(request);

        // Assert
        assertEquals(false, result);
    }

    @Test
    void givenErrorExecutingRequestWhenLoginThenReturnInternalError() throws IOException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();
        CloseableHttpClient httpClientMock = Mockito.mock(CloseableHttpClient.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(httpClientMock);
        Mockito.when(httpClientMock.execute(Mockito.any(HttpGet.class))).thenThrow(new RuntimeException());

        // Act
        Exception exception = assertThrows(Exception.class, () ->  loginAGNProvider.login(request));

        // Assert
        assertEquals(HandledException.class, exception.getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((HandledException) exception).getStatus());
        assertEquals(GenericControllerErrorResponse.REQUEST_EXCEPTION.getCode(), ((HandledException) exception).getCode());
        assertEquals(GenericControllerErrorResponse.REQUEST_EXCEPTION.getDescription(), ((HandledException) exception).getMessage());
    }

    @Test
    void givenErrorCreatingClientWhenLoginThenReturnInternalError() throws IOException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException());

        // Act
        Exception exception = assertThrows(Exception.class, () ->  loginAGNProvider.login(request));

        // Assert
        assertEquals(HandledException.class, exception.getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((HandledException) exception).getStatus());
        assertEquals(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION.getCode(), ((HandledException) exception).getCode());
        assertEquals(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION.getDescription(), ((HandledException) exception).getMessage());
    }


}
