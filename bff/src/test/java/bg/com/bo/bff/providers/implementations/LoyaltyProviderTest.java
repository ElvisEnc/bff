package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltySERequestFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySEResponseFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@WireMockTest(httpPort = 8080)
@ExtendWith(WireMockExtension.class)
class LoyaltyProviderTest {
    private LoyaltyProvider loyaltyProvider;
    private IHttpClientFactory httpClientFactoryMock;
    private ObjectMapper objectMapperMock;

    private static final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        httpClientFactoryMock = mock(IHttpClientFactory.class);
        objectMapperMock = mock(ObjectMapper.class);

        loyaltyProvider = spy(new LoyaltyProvider(httpClientFactoryMock, objectMapperMock));
        ReflectionTestUtils.setField(loyaltyProvider, "baseUrl", BASE_URL);
    }

    @Test
    void givenValidPersonId_whenGetSystemCode_thenReturnResponse() throws Exception {
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/personas-ganamovil/codigo-sistema/" + personId;

        LoyaltySystemCodeServerResponse mockResponse = LoyaltySEResponseFixture.withDefaultSystemCode();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltySystemCodeServerResponse.class);

        LoyaltySystemCodeServerResponse response = loyaltyProvider.getSystemCode(personId, headers);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenGetSumPoint_thenReturnResponse() throws Exception {
        String personId = "12345";
        String codeSystem = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos/" + codeSystem + "/campana/1";

        LoyaltySumPointServerResponse mockResponse = LoyaltySEResponseFixture.withDefaultSumPoint();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltySumPointServerResponse.class);

        LoyaltySumPointServerResponse response = loyaltyProvider.getSumPoint(codeSystem, headers);
        assertNotNull(response);
    }

    @Test
    void givenValidRequest_whenRegisterSubscription_thenReturnResponse() throws Exception {
        String personId = "12345";
        LoyaltyRegisterSubscriptionRequest request = LoyaltySERequestFixture.withDefaultRegisterSubscriptionSE();
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId, "Content-Type", "application/json");
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/suscripciones-ganamovil/contrato-suscripcion";

        LoyaltyRegisterSubscriptionResponse mockResponse = LoyaltySEResponseFixture.withDefaultRegisterSubscription();
        doReturn(mockResponse).when(loyaltyProvider).executePostRequest(expectedUrl, request, headers, LoyaltyRegisterSubscriptionResponse.class);

        LoyaltyRegisterSubscriptionResponse response = loyaltyProvider.registerSubscription(request, headers);
        assertNotNull(response);
    }

    @Test
    void givenValidRequest_whenRegisterRedeemVoucher_thenReturnResponse() throws Exception {
        String personId = "12345";
        var request = LoyaltySERequestFixture.withDefaultRegisterRedeemVoucherSE();
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/canje-vales-ganamovil/canje-vale";

        LoyaltyRegisterRedeemVoucherResponse mockResponse = LoyaltySEResponseFixture.withDefaultRegisterRedeemVoucher();
        doReturn(mockResponse).when(loyaltyProvider).executePostRequest(expectedUrl, request, headers, LoyaltyRegisterRedeemVoucherResponse.class);

        LoyaltyRegisterRedeemVoucherResponse response = loyaltyProvider.registerRedeemVoucher(request, headers);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenGetLevel_thenReturnResponse() throws Exception {
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/suscripciones-ganamovil/obtener-nivel/" + personId + "/campana/1";

        LoyaltyGetLevelResponse mockResponse = LoyaltySEResponseFixture.withDefaultLoyaltyGetLevel();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyGetLevelResponse.class);

        LoyaltyGetLevelResponse response = loyaltyProvider.getLevel(headers, personId);
        assertNotNull(response);
    }


}
