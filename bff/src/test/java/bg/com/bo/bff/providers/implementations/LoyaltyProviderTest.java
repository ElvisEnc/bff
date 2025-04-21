package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltySERequestFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySEResponseFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyTradeCategoryAPIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;


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

    @Test
    void givenValidPersonId_whenGetTradeCategories_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);

        String expectedUrl = BASE_URL + "/lealtad/beneficios/api/v1/comercios-ganamovil/categorias-comercios";

        List<LoyaltyTradeCategoryAPIResponse> mockResponseList =
                LoyaltySEResponseFixture.withDefaultLoyaltyGetTradeCategories();

        LoyaltyTradeCategoryAPIResponse[] mockArray =
                mockResponseList.toArray(new LoyaltyTradeCategoryAPIResponse[0]);

        doReturn(mockArray).when(loyaltyProvider)
                .executeGetRequest(expectedUrl, headers, LoyaltyTradeCategoryAPIResponse[].class);

        // Act
        List<LoyaltyTradeCategoryAPIResponse> response = loyaltyProvider.getTradeCategories(headers, personId);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponseList.size(), response.size());
    }


}
