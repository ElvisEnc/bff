package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.dtos.request.LogoutRequest;
import bg.com.bo.bff.mappings.providers.GenericsMapper;
import bg.com.bo.bff.mappings.services.keycloak.KeyCloakJsonMapper;
import bg.com.bo.bff.mappings.providers.keycloak.KeyCloakObjectMapper;
import bg.com.bo.bff.mappings.providers.keycloak.KeyCloakMapper;
import bg.com.bo.bff.providers.dtos.response.keycloak.ErrorKCResponse;
import bg.com.bo.bff.models.dtos.login.CreateTokenServiceResponse;
import bg.com.bo.bff.application.exceptions.CreateTokenServiceException;
import bg.com.bo.bff.application.exceptions.NotHandledResponseException;
import bg.com.bo.bff.models.jwt.JwtAccess;
import bg.com.bo.bff.models.jwt.JwtKey;
import bg.com.bo.bff.providers.dtos.response.keycloak.KeyCloakCertListResponse;
import bg.com.bo.bff.providers.dtos.response.keycloak.KeyCloakKeyResponse;
import bg.com.bo.bff.providers.dtos.response.keycloak.CreateTokenKCResponse;
import bg.com.bo.bff.commons.enums.UserRole;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class JwtKeyCloakProviderTests {
    static IHttpClientFactory httpClientFactoryMock;
    static JwtKeyCloakProvider jwtKeyCloakProvider;
    private String url = "http://login.fake.api";
    private String urlTokenComplement = "/token";
    private String urlCertsComplement = "/certs";
    private String urlRevokeTokenComplement = "/revoke";
    private String issuerKCServiceParam = "https://bg-gnm-dev.eastus.cloudapp.azure.com/keycloak/realms/kong";
    private String audienceKCServiceParam = "ganamovil-bff";
    private String authorizedPartyKCServiceParam = "ganamovil-bff";
    private String subjectKCServiceParam = "67d342a8-dec3-4938-9b6a-b93cfc999b19";

    @BeforeAll
    public static void setup() {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
    }

    @BeforeEach
    public void init() {
        KeyCloakMapper keyCloakMapper = new KeyCloakMapper(KeyCloakObjectMapper.INSTANCE, new KeyCloakJsonMapper());
        GenericsMapper genericsMapper = new GenericsMapper();
        jwtKeyCloakProvider = new JwtKeyCloakProvider(httpClientFactoryMock, keyCloakMapper, genericsMapper);

        //Propiedades cargadas por reflection dentro de JwtKeyCloakService.
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "urlBase", url);
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "urlTokenComplement", urlTokenComplement);
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "clientId", "ganamovil-bff");
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "clientSecret", "");
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "urlCertsComplement", urlCertsComplement);
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "issuer", issuerKCServiceParam);
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "audience", audienceKCServiceParam);
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "authorizedParty", authorizedPartyKCServiceParam);
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "subject", subjectKCServiceParam);
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "urlRevokeComplement", urlRevokeTokenComplement);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
    }

    @Test
    void givenCorrectDataWhenGenerateTokenThenReturnTokenData() throws Exception {
        //Arrange
        String personId = "1";
        UserRole userRole = UserRole.LOGGED_USER;
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        String sid = UUID.randomUUID().toString();

        CreateTokenKCResponse createTokenKCResponse = new CreateTokenKCResponse();
        createTokenKCResponse.setAccessToken(accessToken);
        createTokenKCResponse.setRefreshToken(refreshToken);

        String jsonResponse = new ObjectMapper().writeValueAsString(createTokenKCResponse);

        stubFor(post(urlTokenComplement).willReturn(okJson(jsonResponse)));

        //Act
        CreateTokenServiceResponse tokenData = jwtKeyCloakProvider.generateToken(personId, sid, userRole);

        //Assert
        assertNotNull(tokenData);
        assertEquals(CreateTokenServiceResponse.StatusCode.SUCCESS, tokenData.getStatusCode());
        assertEquals(accessToken, tokenData.getTokenData().getAccessToken());
        assertEquals(refreshToken, tokenData.getTokenData().getRefreshToken());
    }

    @Test
    void givenCorrectDataWhenGetCertsThenReturnKeyCloakCertList() throws IOException {
        //Arrange
        String keyCloakKeyId = "QzYxcSySXxmQVV8M_j1FUeFbloxuRFNtdPEEm6EG7xY";
        String keyCloakX5c = "\"MIIClzCCAX8CBgGMgl+kxzANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARrb25nMB4XDTIzMTIxOTEzNTczNVoXDTMzMTIxOTEzNTkxNVowDzENMAsGA1UEAwwEa29uZzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANTB4OEKXXJqK29y+1kfJI2z6w66ySd/CzhTNNBMVnwxsMOYoLj2a/VTMpi0TmwinyE6tnFbnTAAC1vcfb0R5hw4EYCnKD48PYPp0Rze+92qrpilQSXHViVJ9hTi/lyi0OV4LtyUU4kIa3hFxKwx1TR6UAi8x4QwuiXop0e66nHh0b40yDrbDT7lJSlBgGjG+4/2WjJVaZ0c6CRSALaDbY/WBlm3nD1+g95wnZtOOzA3lRz0gdks8LnUppNN/0QQNdnSWrZNkL7AvBt3FbkOu21NrjtCYYXy2wD7IS2Leb/grNtv8haf506zbJyNdeMD9/NfmYLpI/jsP6YTHRA1o80CAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAQ0gb4dhH+IP5OCBXCYtwcbHebxzeS15uE47dakqHb7Gp43S9bpz8Cr5NvkMDhsxYi9ARNKJ4LEeg5GmXzStLozHVJyLDizEUiCi7a6bwTNhYpFOdu+4E3an1ZnHGai5NyMsBmG2DGYDYWGKvykW2SFrSRbxRZIjRdawxQbxoB2Uxk9o1ITV8KRscwxOTTX6tvvlnP1hABOH2zYeOSrkNcvkyD4jyaE3VpHEjqJWv1TnV4lRaemdQ9QmPBSfIsA9oHclbduzkMi1uPABWCgk/VN+h/Ne9C0DuO61LZaaykpXzFcSo785CljElr77sVxUijRUY1Ajquk4T6rRgNL5PMg==\"";
        String algorithm = "RSA-OAEP";

        KeyCloakKeyResponse keyCloakKeyResponse = KeyCloakKeyResponse.builder()
                .id(keyCloakKeyId)
                .x5c(keyCloakX5c)
                .algorithm(algorithm)
                .build();

        KeyCloakCertListResponse keyCloakCertList = KeyCloakCertListResponse.builder()
                .key(keyCloakKeyResponse)
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(keyCloakCertList);
        stubFor(get(urlCertsComplement).willReturn(okJson(jsonResponse)));

        //Act
        Map<String, JwtKey> certs = jwtKeyCloakProvider.certs();

        //Assert
        assertNotNull(certs);
        assertFalse(certs.isEmpty());
        assertTrue(certs.containsKey(keyCloakKeyId));
        assertEquals(algorithm, certs.get(keyCloakKeyId).getAlgorithm());
    }


    @Test
    void givenValidRefreshTokenWhenRefreshTokenThenReturnNewRefreshToken() throws JsonProcessingException {
        //Arrange
        String refreshToken = UUID.randomUUID().toString();
        String newRefreshToken = UUID.randomUUID().toString();
        String newAccessToken = UUID.randomUUID().toString();

        CreateTokenKCResponse createTokenKCResponse = new CreateTokenKCResponse();
        createTokenKCResponse.setAccessToken(newAccessToken);
        createTokenKCResponse.setRefreshToken(newRefreshToken);

        String jsonResponse = new ObjectMapper().writeValueAsString(createTokenKCResponse);
        stubFor(post(urlTokenComplement).willReturn(okJson(jsonResponse)));

        //Act
        CreateTokenServiceResponse createTokenServiceResponse = jwtKeyCloakProvider.refreshToken(refreshToken);

        //Assert
        assertEquals(newRefreshToken, createTokenServiceResponse.getTokenData().getRefreshToken());
        assertEquals(newAccessToken, createTokenServiceResponse.getTokenData().getAccessToken());
    }

    @Test
    void givenUnauthorizedClientWhenRefreshTokenThenReturnUnauthorizedException() throws JsonProcessingException {
        //Arrange
        String refreshToken = UUID.randomUUID().toString();

        ErrorKCResponse errorKCResponse = ErrorKCResponse.builder()
                .error(ErrorKCResponse.Error.INVALID_CLIENT.getCode())
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(errorKCResponse);

        stubFor(post(urlTokenComplement)
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));

        //Act
        CreateTokenServiceException createTokenServiceException = assertThrows(CreateTokenServiceException.class, () -> jwtKeyCloakProvider.refreshToken(refreshToken));

        //Assert
        assertEquals(ErrorKCResponse.Error.INVALID_CLIENT.name(), createTokenServiceException.getCode());
    }

    @ParameterizedTest
    @EnumSource(value = ErrorKCResponse.Error.class, names = {"NOT_FOUND_404", "INVALID_GRANT", "NOT_AVAILABLE"}, mode = EnumSource.Mode.EXCLUDE)
    void givenBadRequestWhenRefreshTokenThenReturnCreateTokenServiceException(ErrorKCResponse.Error error) throws JsonProcessingException {
        //Arrange
        String refreshToken = UUID.randomUUID().toString();

        ErrorKCResponse errorKCResponse = ErrorKCResponse.builder()
                .error(error.getCode())
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(errorKCResponse);

        stubFor(post(urlTokenComplement)
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));

        //Act
        CreateTokenServiceException createTokenServiceException = assertThrows(CreateTokenServiceException.class, () -> jwtKeyCloakProvider.refreshToken(refreshToken));

        //Assert
        assertEquals(error.name(), createTokenServiceException.getCode());
    }

    @Test
    void givenInvalidGrantsWhenRefreshTokenThenReturnCreateTokenServiceException() throws JsonProcessingException {
        //Arrange
        String refreshToken = UUID.randomUUID().toString();

        ErrorKCResponse errorKCResponse = ErrorKCResponse.builder()
                .error(ErrorKCResponse.Error.INVALID_GRANT.getCode())
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(errorKCResponse);

        stubFor(post(urlTokenComplement)
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));

        //Act
        CreateTokenServiceResponse createTokenServiceResponse = jwtKeyCloakProvider.refreshToken(refreshToken);

        //Assert
        assertEquals(CreateTokenServiceResponse.StatusCode.INVALID_DATA, createTokenServiceResponse.getStatusCode());
    }

    @Test
    void givenNotHandled400ResponseWhenRefreshTokenThenReturnNotHandledResponseException() {
        //Arrange
        String refreshToken = UUID.randomUUID().toString();
        String message = "NOT_VALID_STATE";

        stubFor(post(urlTokenComplement)
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody(message)));

        //Act
        NotHandledResponseException notHandledResponseException = assertThrows(NotHandledResponseException.class, () -> jwtKeyCloakProvider.refreshToken(refreshToken));

        //Assert
        assertEquals(message, notHandledResponseException.getMessage());
    }

    @Test
    void givenNotHandled400AsErrorKCResponseWhenRefreshTokenThenReturnNotHandledResponseException() throws JsonProcessingException {
        //Arrange
        String refreshToken = UUID.randomUUID().toString();
        String errorCode = "NOT_VALID_STATE";

        ErrorKCResponse errorKCResponse = ErrorKCResponse.builder()
                .error(errorCode)
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(errorKCResponse);

        stubFor(post(urlTokenComplement)
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));

        //Act
        NotHandledResponseException notHandledResponseException = assertThrows(NotHandledResponseException.class, () -> jwtKeyCloakProvider.refreshToken(refreshToken));

        //Assert
        assertEquals(errorCode, notHandledResponseException.getMessage());
    }

    @Test
    void given404ResponseWhenRefreshTokenThenReturnCreateTokenServiceException() {
        //Arrange
        String refreshToken = UUID.randomUUID().toString();
        String message = "NOT_A_VALID_CONTENT";

        stubFor(post(urlTokenComplement)
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(message)));

        //Act
        CreateTokenServiceException createTokenServiceException = assertThrows(CreateTokenServiceException.class, () -> jwtKeyCloakProvider.refreshToken(refreshToken));

        //Assert
        assertEquals(ErrorKCResponse.Error.NOT_FOUND_404.name(), createTokenServiceException.getCode());
    }

    @Test
    void givenNotHandledResponseWhenRefreshTokenThenReturnNotHandledResponseException() {
        //Arrange
        String refreshToken = UUID.randomUUID().toString();
        String message = "NOT_A_VALID_CONTENT";

        stubFor(post(urlTokenComplement)
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody(message)));

        //Act
        NotHandledResponseException notHandledResponseException = assertThrows(NotHandledResponseException.class, () -> jwtKeyCloakProvider.refreshToken(refreshToken));

        //Assert
        assertEquals(message, notHandledResponseException.getMessage());
    }

    @Disabled
    @Test
    void givenValidTokenWhenValidateTokenThenSuccessfullValidation() throws IOException {
        //Arrange
        String jsonResponse = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/files/KeyCloakCertsResponse.json")), StandardCharsets.UTF_8);
        stubFor(get(urlCertsComplement).willReturn(okJson(jsonResponse)));

        //Act
        JwtAccess result = jwtKeyCloakProvider.parseJwtAccess("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRell4Y1N5U1h4bVFWVjhNX2oxRlVlRmJsb3h1UkZOdGRQRUVtNkVHN3hZIn0.eyJleHAiOjE3MDU1MDk5MjksImlhdCI6MTcwNTUwNjMyOSwianRpIjoiZDIzNWM2MzctOGQ5OC00YTY0LTljNGYtMmEwODI4YTg0ZjViIiwiaXNzIjoiaHR0cHM6Ly9iZy1nbm0tZGV2LmVhc3R1cy5jbG91ZGFwcC5henVyZS5jb20va2V5Y2xvYWsvcmVhbG1zL2tvbmciLCJhdWQiOiJnYW5hbW92aWwtYmZmIiwic3ViIjoiNjdkMzQyYTgtZGVjMy00OTM4LTliNmEtYjkzY2ZjOTk5YjE5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZ2FuYW1vdmlsLWJmZiIsInNlc3Npb25fc3RhdGUiOiJhMzRiYWZmZS1iMWYzLTQwODEtOWVjZC1iMDcwZDk0YTVjN2IiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWtvbmciXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJhdXRob3JpemF0aW9uIjp7InBlcm1pc3Npb25zIjpbeyJjbGFpbXMiOnsicm9sZXMiOlsiTE9HR0VEX1VTRVIiXSwicGVyc29uSWQiOlsiMSJdfSwicnNpZCI6IjA1ZTQ0ZDA3LWUyNTEtNGYzZC05ZjAyLWFhMjFiZmQ0MWNhYyIsInJzbmFtZSI6IkRlZmF1bHQgUmVzb3VyY2UifV19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiJhMzRiYWZmZS1iMWYzLTQwODEtOWVjZC1iMDcwZDk0YTVjN2IiLCJjbGllbnRIb3N0IjoiMTAuMjI0LjAuMTE0IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtZ2FuYW1vdmlsLWJmZiIsImNsaWVudEFkZHJlc3MiOiIxMC4yMjQuMC4xMTQiLCJjbGllbnRfaWQiOiJnYW5hbW92aWwtYmZmIn0.Kb_3Hw5sYChsjPIQ7FUvNW-zf1jpxelwNmKz2Q9NEE9-Mr-9Ej8DBw-VAIQCQwcoTsVBKSi8CKZWV2wZFzsnNHRLCkGDXw-cOfmJfbfrI3lgw8rUxHgNVzrKNDpVqhuu2-1xEyzWv2FrwiP3jHhlj30t1NCsEM2Z3EA9RsUvWJLCJorP0rMn3PQ3-UdzGeVxf2G-sLmY3tNyhPIziA28_P2lHKtam_5qInO1-As5ELvx9n2yoMHeqcUrudutwGg0YKcpjJ_S9ssYBH5HGUC861uRxIQdds0NoYGONkW2g35c_jKp8gjwItzR__7SHoSw9sdKAfNGZZZzZjNGS9qifg");

        //Assert
        assertNotNull(result);
    }


    @Test
    void revokeAccessTokenSuccessfully() {
        // Arrange
        String testToken = "testAccessToken";
        stubFor(post(urlEqualTo(urlRevokeTokenComplement))
                .withRequestBody(containing("access_token"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())));

        // Act
        boolean result = jwtKeyCloakProvider.revokeAccessToken(testToken);

        // Assert
        assertTrue(result, "Token should be successfully revoked.");
        verify(postRequestedFor(urlPathEqualTo(urlRevokeTokenComplement))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded")));
    }

    @Test
    void revokeAccessTokenFailure() {
        // Arrange
        String testToken = "testAccessToken";
        stubFor(post(urlEqualTo(urlRevokeTokenComplement))
                .withRequestBody(containing("access_token"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        // Act
        boolean result = jwtKeyCloakProvider.revokeAccessToken(testToken);

        // Assert
        assertFalse(result, "Token revocation should fail.");
        verify(postRequestedFor(urlPathEqualTo(urlRevokeTokenComplement))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded")));
    }

    @Test
    void revokeRefreshTokenSuccessfully() {
        // Arrange
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRefreshToken("testRefreshToken");
        stubFor(post(urlEqualTo(urlRevokeTokenComplement))
                .withRequestBody(containing("refresh_token"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())));

        // Act
        boolean result = jwtKeyCloakProvider.revokeRefreshToken(logoutRequest);

        // Assert
        assertTrue(result, "Refresh token should be successfully revoked.");
        verify(postRequestedFor(urlPathEqualTo(urlRevokeTokenComplement))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded")));
    }

    @Test
    void revokeRefreshTokenFailure() {
        // Arrange
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRefreshToken("testRefreshToken");
        stubFor(post(urlEqualTo(urlRevokeTokenComplement))
                .withRequestBody(containing("refresh_token"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        // Act
        boolean result = jwtKeyCloakProvider.revokeRefreshToken(logoutRequest);

        // Assert
        assertFalse(result, "Refresh token revocation should fail.");
        verify(postRequestedFor(urlPathEqualTo(urlRevokeTokenComplement))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded")));
    }
}
