package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltySERequestFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySEResponseFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        LoyaltyPointServerResponse mockResponse = LoyaltySEResponseFixture.withDefaultSumPoint();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyPointServerResponse.class);

        LoyaltyPointServerResponse response = loyaltyProvider.getSumPoint(codeSystem, headers);
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

    @Test
    void givenValidPersonId_whenGetPointsPeriod_thenReturnResponse() throws Exception {
        String codeSystem = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", "123");
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos-periodo/" + codeSystem + "/campana/1";

        LoyaltyPointServerResponse mockResponse = LoyaltySEResponseFixture.withDefaultSumPoint();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyPointServerResponse.class);

        LoyaltyPointServerResponse response = loyaltyProvider.getPointsPeriod(headers, codeSystem);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenGetInitialPointsVamos_thenReturnResponse() throws Exception {
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/linkser-ganamovil/obtener-puntos-vamos/" + personId;

        LoyaltyGetInitialPointsVamosResponse mockResponse = LoyaltySEResponseFixture.withDefaultInitialPoints();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyGetInitialPointsVamosResponse.class);

        LoyaltyGetInitialPointsVamosResponse response = loyaltyProvider.getInitialPointsVAMOS(headers, personId);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenVerifySubscription_thenReturnResponse() throws Exception {
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/suscripciones-ganamovil/verificar-campanas-suscripcion/" + personId + "/campana/1";

        LoyaltySubscriptionResponse mockResponse = LoyaltySEResponseFixture.withDefaultSubscription();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltySubscriptionResponse.class);

        LoyaltySubscriptionResponse response = loyaltyProvider.verifySubscription(headers, personId);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenStatementPoints_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        LoyaltyStatementPointRequest request = LoyaltySERequestFixture.withDefaultStatementPoint();
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/extracto-ganamovil/reporte-extracto";

        List<LoyaltyStatementPointsResponse> mockResponse = LoyaltySEResponseFixture.withDefaultStatementPoints();

        doReturn(mockResponse).when(loyaltyProvider)
                .executePostRequest(eq(expectedUrl), eq(request), eq(headers), any(TypeReference.class));

        // Act
        List<LoyaltyStatementPointsResponse> response = loyaltyProvider.statementPoints(request, headers);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse.size(), response.size());
        verify(loyaltyProvider).executePostRequest(eq(expectedUrl), eq(request), eq(headers), any(TypeReference.class));
    }


    @Test
    void givenValidPersonId_whenGetGeneralInformation_thenReturnResponse() throws Exception {
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/campana/api/v1/suscripciones-ganamovil/informacion-general/" + personId ;

        LoyaltyGeneralInformationResponse mockResponse = LoyaltySEResponseFixture.withDefaultGeneralInformationData();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyGeneralInformationResponse.class);

        LoyaltyGeneralInformationResponse response = loyaltyProvider.getGeneralInformation(headers, personId);
        assertNotNull(response);
    }


}
