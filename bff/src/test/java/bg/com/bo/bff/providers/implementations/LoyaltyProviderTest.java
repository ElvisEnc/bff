package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltySERequestFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySEResponseFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.models.external.services.HttpClient;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WireMockTest(httpPort = 8080)
@ExtendWith(WireMockExtension.class)
class LoyaltyProviderTest {
    private static final String BASE_URL = "http://localhost:8080";
    private LoyaltyProvider loyaltyProvider;
    private HttpClient httpClientMock;

    @BeforeEach
    void setUp() {
        httpClientMock = mock(HttpClient.class);
        loyaltyProvider = new LoyaltyProvider(httpClientMock);
        ReflectionTestUtils.setField(loyaltyProvider, "baseUrl", BASE_URL);
    }

    @Test
    void givenValidPersonId_whenGetSystemCode_thenReturnResponse() throws Exception {
        String personId = "12345";
        LoyaltySystemCodeServerResponse mockResponse = LoyaltySEResponseFixture.withDefaultSystemCode();

        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/personas-ganamovil/codigo-sistema/" + personId;

        when(httpClientMock.executeGetRequest(expectedUrl, headers, LoyaltySystemCodeServerResponse.class))
                .thenReturn(mockResponse);
        LoyaltySystemCodeServerResponse response = loyaltyProvider.getSystemCode(personId, headers);

        assertNotNull(response);
    }


    @Test
    void givenValidPersonId_whenGetSumPoint_thenReturnResponse() throws Exception {
        String personId = "12345";
        String codeSystem = "12345";
        LoyaltySumPointServerResponse mockResponse = LoyaltySEResponseFixture.withDefaultSumPoint();
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos/" + codeSystem + "/campana/1";

        when(httpClientMock.executeGetRequest(expectedUrl, headers, LoyaltySumPointServerResponse.class))
                .thenReturn(mockResponse);

        LoyaltySumPointServerResponse response = loyaltyProvider.getSumPoint(codeSystem, headers);

        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenRegisterSubscription_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        LoyaltyRegisterSubscriptionRequest requestService=  LoyaltySERequestFixture.withDefaultRegisterSubscriptionSE();
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId, "Content-Type", "application/json");
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/suscripciones-ganamovil/contrato-suscripcion" ;

        LoyaltyRegisterSubscriptionResponse mockResponse = LoyaltySEResponseFixture.withDefaultRegisterSubscription();
        when(httpClientMock.executePostRequest(expectedUrl, requestService, headers, LoyaltyRegisterSubscriptionResponse.class))
                .thenReturn(mockResponse);

        // Act
        LoyaltyRegisterSubscriptionResponse actualResponse = loyaltyProvider.registerSubscription(requestService, headers);

        // Assert
        assertNotNull(actualResponse);
    }

}
