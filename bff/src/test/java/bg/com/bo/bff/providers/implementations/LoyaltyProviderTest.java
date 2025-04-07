package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltySERequestFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySEResponseFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WireMockTest(httpPort = 8080)
@ExtendWith(WireMockExtension.class)
class LoyaltyProviderTest {
    private LoyaltyProvider loyaltyProvider;
    private IHttpClientFactory httpClientFactoryMock;
    private static final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        httpClientFactoryMock = mock(IHttpClientFactory.class);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        when(httpClientFactoryMock.create()).thenReturn(httpClient);

        loyaltyProvider = new LoyaltyProvider(httpClientFactoryMock);
        ReflectionTestUtils.setField(loyaltyProvider, "baseUrl", BASE_URL);
    }

    @Test
    void givenValidPersonId_whenGetSystemCode_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        String expectedUrl = "/lealtad/campana/api/v1/personas-ganamovil/codigo-sistema/" + personId;
        LoyaltySystemCodeServerResponse mockResponse = LoyaltySEResponseFixture.withDefaultSystemCode();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(mockResponse);

        stubFor(get(urlEqualTo(expectedUrl))
                .willReturn(okJson(jsonResponse)));

        Map<String, String> headers = new HashMap<>();
        headers.put("sesion", "01042025154313ffe5a4bb00d20a5b");
        headers.put("idpersona", personId);

        // Act
        LoyaltySystemCodeServerResponse actualResponse = loyaltyProvider.getSystemCode(personId, headers);

        // Assert
        assertNotNull(actualResponse);
    }

    @Test
    void givenServerError_whenGetSystemCode_thenThrowsHandledException() {
        // Arrange
        String personId = "12345";
        String expectedUrl = "/lealtad/campana/api/v1/personas-ganamovil/codigo-sistema/" + personId;

        stubFor(get(urlEqualTo(expectedUrl))
                .willReturn(serverError()));

        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.getSystemCode(personId, headers));

        assertEquals(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE.getCode(), ex.getCode());
    }

    @Test
    void givenInvalidJson_whenGetSystemCode_thenThrowsHandledException() {
        // Arrange
        String personId = "12345";
        String expectedUrl = "/lealtad/campana/api/v1/personas-ganamovil/codigo-sistema/" + personId;
        String invalidJson = "{ invalid json }";
        stubFor(get(urlEqualTo(expectedUrl))
                .willReturn(okJson(invalidJson)));

        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.getSystemCode(personId, headers));

        assertEquals(GenericControllerErrorResponse.REQUEST_EXCEPTION.getCode(), ex.getCode());
    }

    @Test
    void givenHttpClientCreationFails_whenGetSystemCode_thenThrowsHandledException() {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error creating HTTP client"));

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.getSystemCode(personId, headers));

        assertEquals(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION.getCode(), ex.getCode());
    }

    @Test
    void givenValidPersonId_whenGetSumPoint_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        String expectedUrl = "/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos/" + personId + "/campana/1";
        LoyaltySumPointServerResponse mockResponse = LoyaltySEResponseFixture.withDefaultSumPoint();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(mockResponse);

        stubFor(get(urlEqualTo(expectedUrl))
                .willReturn(okJson(jsonResponse)));

        Map<String, String> headers = new HashMap<>();
        headers.put("sesion", "01042025154313ffe5a4bb00d20a5b");
        headers.put("idpersona", personId);

        // Act
        LoyaltySumPointServerResponse actualResponse = loyaltyProvider.getSumPoint(personId, headers);

        // Assert
        assertNotNull(actualResponse);
    }

    @Test
    void givenServerError_whenGetSumPoint_thenThrowsHandledException() {
        // Arrange
        String personId = "12345";
        String expectedUrl = "/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos/" + personId + "/campana/1";

        stubFor(get(urlEqualTo(expectedUrl))
                .willReturn(serverError()));

        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.getSumPoint(personId, headers));

        assertEquals(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE.getCode(), ex.getCode());
    }

    @Test
    void givenInvalidJson_whenGetSumPoint_thenThrowsHandledException() {
        // Arrange
        String personId = "12345";
        String expectedUrl = "/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos/" + personId + "/campana/1";
        String invalidJson = "{ invalid json }";
        stubFor(get(urlEqualTo(expectedUrl))
                .willReturn(okJson(invalidJson)));

        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.getSumPoint(personId, headers));

        assertEquals(GenericControllerErrorResponse.REQUEST_EXCEPTION.getCode(), ex.getCode());
    }

    @Test
    void givenHttpClientCreationFails_whenGetSumPoint_thenThrowsHandledException() {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error creating HTTP client"));

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.getSumPoint(personId, headers));

        assertEquals(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION.getCode(), ex.getCode());
    }

    @Test
    void givenValidPersonId_whenRegisterSubscription_thenReturnResponse() throws Exception {
        // Arrange
        LoyaltyRegisterSubscriptionRequest requestService=  LoyaltySERequestFixture.withDefaultRegisterSubscriptionSE();
        String expectedUrl = "/lealtad/campana/api/v1/suscripciones-ganamovil/contrato-suscripcion" ;
        LoyaltyRegisterSubscriptionResponse mockResponse = LoyaltySEResponseFixture.withDefaultRegisterSubscription();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(mockResponse);

        stubFor(post(urlEqualTo(expectedUrl))
                .willReturn(okJson(jsonResponse)));

        Map<String, String> headers = new HashMap<>();
        headers.put("sesion", "01042025154313ffe5a4bb00d20a5b");
        headers.put("idpersona", "123");
        headers.put("Content-Type", "application/json");

        // Act
        LoyaltyRegisterSubscriptionResponse actualResponse = loyaltyProvider.registerSubscription(requestService, headers);

        // Assert
        assertNotNull(actualResponse);
    }

    @Test
    void givenServerError_whenRegisterSubscription_thenThrowsHandledException() {
        // Arrange
        LoyaltyRegisterSubscriptionRequest requestService=  LoyaltySERequestFixture.withDefaultRegisterSubscriptionSE();
        String expectedUrl = "/lealtad/campana/api/v1/suscripciones-ganamovil/contrato-suscripcion" ;

        stubFor(post(urlEqualTo(expectedUrl))
                .willReturn(serverError()));

        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.registerSubscription(requestService, headers));

        assertEquals(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE.getCode(), ex.getCode());
    }

    @Test
    void givenInvalidJson_whenRegisterSubscription_thenThrowsHandledException() {
        // Arrange
        LoyaltyRegisterSubscriptionRequest requestService=  LoyaltySERequestFixture.withDefaultRegisterSubscriptionSE();
        String expectedUrl = "/lealtad/campana/api/v1/suscripciones-ganamovil/contrato-suscripcion";
        String invalidJson = "{ invalid json }";
        stubFor(post(urlEqualTo(expectedUrl))
                .willReturn(okJson(invalidJson)));

        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.registerSubscription(requestService, headers));

        assertEquals(GenericControllerErrorResponse.REQUEST_EXCEPTION.getCode(), ex.getCode());
    }

    @Test
    void givenHttpClientCreationFails_whenRegisterSubscription_thenThrowsHandledException() {
        // Arrange
        LoyaltyRegisterSubscriptionRequest requestService=  LoyaltySERequestFixture.withDefaultRegisterSubscriptionSE();
        Map<String, String> headers = Map.of("Authorization", "Bearer dummy-token");

        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error creating HTTP client"));

        // Act & Assert
        HandledException ex = assertThrows(HandledException.class, () ->
                loyaltyProvider.registerSubscription(requestService, headers));

        assertEquals(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION.getCode(), ex.getCode());
    }


}
