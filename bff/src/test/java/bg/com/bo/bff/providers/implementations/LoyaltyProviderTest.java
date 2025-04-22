package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.CityCategoryMerchantsAPIRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyGetImagesRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyPersonCampRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.MerchantCampaignVoucherAPIRequest;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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

        LoyaltyPostRegisterRedeemVoucherResponse mockResponse = LoyaltySEResponseFixture.withDefaultRegisterRedeemVoucher();
        doReturn(mockResponse).when(loyaltyProvider).executePostRequest(expectedUrl, request, headers, LoyaltyPostRegisterRedeemVoucherResponse.class);

        LoyaltyPostRegisterRedeemVoucherResponse response = loyaltyProvider.registerRedeemVoucher(request, headers);
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

        LoyaltyStatusResponse mockResponse = LoyaltySEResponseFixture.withDefaultSubscription();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyStatusResponse.class);

        LoyaltyStatusResponse response = loyaltyProvider.verifySubscription(headers, personId);
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

    @Test
    void givenValidPersonId_whenGetImageInformation_thenReturnResponse() throws Exception {
        String imageId = "12345";
        String expectedUrl = BASE_URL + "/lealtad/archivos/api/v1/imagenes/buscarImagen/" + imageId + "/completa/true";

        LoyaltyGetImageResponse mockResponse = LoyaltySEResponseFixture.withDefaultImage();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, Collections.emptyMap(), LoyaltyGetImageResponse.class);

        LoyaltyGetImageResponse response = loyaltyProvider.getImageInformation(imageId);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenGetImagesInformation_thenReturnResponse() throws Exception {
        // Arrange
        LoyaltyGetImagesRequest request = LoyaltySERequestFixture.withDefaultImage();
        String expectedUrl = BASE_URL + "/lealtad/archivos/api/v1/imagenes/buscar-imagenes";

        LoyaltyGetImageResponse mockResponse = LoyaltySEResponseFixture.withDefaultImage();
        List<LoyaltyGetImageResponse> mockListResponse = new ArrayList<>();
        mockListResponse.add(mockResponse);
        doReturn(mockListResponse).when(loyaltyProvider)
                .executePostRequest(eq(expectedUrl), eq(request), eq(Collections.emptyMap()), any(TypeReference.class));

        // Act
        List<LoyaltyGetImageResponse> response = loyaltyProvider.getImagesInformation(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockListResponse.size(), response.size());
        verify(loyaltyProvider).executePostRequest(eq(expectedUrl), eq(request), eq(Collections.emptyMap()), any(TypeReference.class));
    }

    @Test
    void givenValidPersonId_whenGetCategoryPromotions_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/administracion/api/v1/promociones-ganamovil";

        LoyaltyGetCategoryPromotionResponse mockResponse = LoyaltySEResponseFixture.withDefaultCategoryPromotion();
        List<LoyaltyGetCategoryPromotionResponse> mockListResponse = new ArrayList<>();
        mockListResponse.add(mockResponse);
        doReturn(mockListResponse).when(loyaltyProvider)
                .executeGetRequest(eq(expectedUrl), eq(headers), any(TypeReference.class));

        // Act
        List<LoyaltyGetCategoryPromotionResponse> response = loyaltyProvider.getCategoryPromotions(headers);

        // Assert
        assertNotNull(response);
        assertEquals(mockListResponse.size(), response.size());
        verify(loyaltyProvider).executeGetRequest(eq(expectedUrl), eq(headers), any(TypeReference.class));
    }

    @Test
    void givenValidPersonId_whenGetCategoryPointsLevels_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/administracion/api/v1/niveles-ganamovil/campanas/1";

        LoyaltyGetLevelResponse mockResponse = LoyaltySEResponseFixture.withDefaultLoyaltyGetLevel();
        List<LoyaltyGetLevelResponse> mockListResponse = new ArrayList<>();
        mockListResponse.add(mockResponse);
        doReturn(mockListResponse).when(loyaltyProvider)
                .executeGetRequest(eq(expectedUrl), eq(headers), any(TypeReference.class));

        // Act
        List<LoyaltyGetLevelResponse> response = loyaltyProvider.getCategoryPointsLevels(headers);

        // Assert
        assertNotNull(response);
        assertEquals(mockListResponse.size(), response.size());
        verify(loyaltyProvider).executeGetRequest(eq(expectedUrl), eq(headers), any(TypeReference.class));
    }

    @Test
    void givenValidRequest_whenTermsConditions_thenReturnResponse() throws Exception {
        String personId = "12345";
        LoyaltyPersonCampRequest request = LoyaltySERequestFixture.withDefaultPersonCamp();
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId, "Content-Type", "application/json");
        String expectedUrl = BASE_URL + "/lealtad/administracion/api/v1/campanas-ganamovil/obtener-contrato-persona";

        LoyaltyGetTermsConditionsResponse mockResponse = LoyaltySEResponseFixture.withDefaultTermsConditions();
        doReturn(mockResponse).when(loyaltyProvider).executePostRequest(expectedUrl, request, headers, LoyaltyGetTermsConditionsResponse.class);

        LoyaltyGetTermsConditionsResponse response = loyaltyProvider.termsConditions(request, headers);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenCheckFlow_thenReturnResponse() throws Exception {
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/administracion/api/v1/reglas-ganamovil/validar-vamos/" + personId ;

        LoyaltyStatusResponse mockResponse = LoyaltySEResponseFixture.withDefaultSubscription();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyStatusResponse.class);

        LoyaltyStatusResponse response = loyaltyProvider.checkFlow(headers, personId);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenGetPromotions_thenReturnResponse() throws Exception {
        String promotionId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", "1234");
        String expectedUrl = BASE_URL + "/lealtad/administracion/api/v1/promociones-ganamovil/" + promotionId;

        LoyaltyGetPromotionResponse mockResponse = LoyaltySEResponseFixture.withDefaultPromotion();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyGetPromotionResponse.class);

        LoyaltyGetPromotionResponse response = loyaltyProvider.getPromotions(headers, promotionId);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenGetStoreFeatured_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/beneficios/api/v1/comercios-ganamovil/comercios-destacados";

        LoyaltyGetStoreFeaturedResponse mockResponse = LoyaltySEResponseFixture.withDefaultStoreFeatured();
        List<LoyaltyGetStoreFeaturedResponse> mockListResponse = new ArrayList<>();
        mockListResponse.add(mockResponse);
        doReturn(mockListResponse).when(loyaltyProvider)
                .executeGetRequest(eq(expectedUrl), eq(headers), any(TypeReference.class));

        // Act
        List<LoyaltyGetStoreFeaturedResponse> response = loyaltyProvider.getStoreFeatured(headers);

        // Assert
        assertNotNull(response);
        assertEquals(mockListResponse.size(), response.size());
        verify(loyaltyProvider).executeGetRequest(eq(expectedUrl), eq(headers), any(TypeReference.class));
    }

    @Test
    void givenValidPersonId_whenGetQRTransactions_thenReturnResponse() throws Exception {
        String voucherId = "12345";
        String typeVoucher = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", "1234");
        String expectedUrl = BASE_URL + "/lealtad/beneficios/api/v1/vales-ganamovil/" + voucherId +"/tipo-vale/" + typeVoucher;

        LoyaltyGetGenericTransactionsResponse mockResponse = LoyaltySEResponseFixture.withDefaultQrTransactions();
        doReturn(mockResponse).when(loyaltyProvider).executeGetRequest(expectedUrl, headers, LoyaltyGetGenericTransactionsResponse.class);

        LoyaltyGetGenericTransactionsResponse response = loyaltyProvider.getQRTransactions(headers, voucherId, typeVoucher);
        assertNotNull(response);
    }

    @Test
    void givenValidPersonId_whenGetVoucherTransactions_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        String codeSystem = "12345";
        String status = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/beneficios/api/v1/vales-ganamovil/campana/1/persona/" + codeSystem + "/estado/" + status;

        LoyaltyGetTransactionsResponse mockResponse = LoyaltySEResponseFixture.withDefaultTransactions();
        List<LoyaltyGetTransactionsResponse> mockListResponse = new ArrayList<>();
        mockListResponse.add(mockResponse);
        doReturn(mockListResponse).when(loyaltyProvider)
                .executeGetRequest(eq(expectedUrl), eq(headers), any(TypeReference.class));

        // Act
        List<LoyaltyGetTransactionsResponse> response = loyaltyProvider.getVoucherTransactions(headers, codeSystem, status);

        // Assert
        assertNotNull(response);
        assertEquals(mockListResponse.size(), response.size());
        verify(loyaltyProvider).executeGetRequest(eq(expectedUrl), eq(headers), any(TypeReference.class));
    }

    @Test
    void givenValidPersonId_whenGetTradeCategories_thenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);

        String expectedUrl = BASE_URL + "/lealtad/beneficios/api/v1/comercios-ganamovil/categorias-comercios";

        List<LoyaltyGetTradeCategoryResponse> mockResponseList =
                LoyaltySEResponseFixture.withDefaultLoyaltyGetTradeCategories();

        LoyaltyGetTradeCategoryResponse[] mockArray =
                mockResponseList.toArray(new LoyaltyGetTradeCategoryResponse[0]);

        doReturn(mockArray).when(loyaltyProvider)
                .executeGetRequest(expectedUrl, headers, LoyaltyGetTradeCategoryResponse[].class);

        // Act
        List<LoyaltyGetTradeCategoryResponse> response = loyaltyProvider.getTradeCategories(headers, personId);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponseList.size(), response.size());
    }

    @Test
    void givenValidPersonIdWhenGetFeaturedMerchantThenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/beneficios/api/v1/comercios-ganamovil/comercios-destacados";
        List<LoyaltyFeaturedMerchantAPIResponse> mockResponseList =
                LoyaltySEResponseFixture.withDefaultGetCityCategoryMerchants();
        LoyaltyFeaturedMerchantAPIResponse[] mockArray =
                mockResponseList.toArray(new LoyaltyFeaturedMerchantAPIResponse[0]);

        doReturn(mockArray).when(loyaltyProvider)
                .executeGetRequest(expectedUrl, headers, LoyaltyFeaturedMerchantAPIResponse[].class);

        // Act
        List<LoyaltyFeaturedMerchantAPIResponse> response = loyaltyProvider.getFeaturedMerchant(headers, personId);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponseList.size(), response.size());
    }

    @Test
    void givenValidPersonIdWhenGetCityListThenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        String expectedUrl = BASE_URL + "/lealtad/beneficios/api/v1/ciudades-ganamovil";
        List<LoyaltyCityListAPIResponse> mockResponseList =
                LoyaltySEResponseFixture.withDefaultGetCityList();
        LoyaltyCityListAPIResponse[] mockArray =
                mockResponseList.toArray(new LoyaltyCityListAPIResponse[0]);

        doReturn(mockArray).when(loyaltyProvider)
                .executeGetRequest(expectedUrl, headers, LoyaltyCityListAPIResponse[].class);

        // Act
        List<LoyaltyCityListAPIResponse> response = loyaltyProvider.getCityList(headers, personId);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponseList.size(), response.size());
    }

    @Test
    void givenValidPersonIdWhenGetCityCategoryMerchantsThenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);
        CityCategoryMerchantsAPIRequest request = CityCategoryMerchantsAPIRequest.builder()
                .idCategoria(UUID.randomUUID()).idCiudad(UUID.randomUUID()).build();
        String expectedUrl = BASE_URL + "/lealtad/beneficios/api/v1/comercios-ganamovil/categoria-comercio/ciudad";
        List<LoyaltyFeaturedMerchantAPIResponse> mockResponseList =
                LoyaltySEResponseFixture.withDefaultFeaturedMerchants();
        LoyaltyFeaturedMerchantAPIResponse[] mockArray =
                mockResponseList.toArray(new LoyaltyFeaturedMerchantAPIResponse[0]);

        doReturn(mockArray).when(loyaltyProvider)
                .executePostRequest(expectedUrl, request, headers, LoyaltyFeaturedMerchantAPIResponse[].class);

        // Act
        List<LoyaltyFeaturedMerchantAPIResponse> response = loyaltyProvider.getCityCategoryMerchants(headers, request);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponseList.size(), response.size());
    }

    @Test
    void givenDataVAlidWhenGetVoucherDetailThenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        UUID voucherId = UUID.randomUUID();
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);

        String expectedUrl = String.format(
                BASE_URL + "/lealtad/beneficios/api/v1/vales-ganamovil/%s/tipo-vale/%s",
                voucherId,
                "PASAJE"
        );
        LoyaltyQrTransactionAPIResponse mockResponse = LoyaltySEResponseFixture.withDefaultGetVoucherDetail();

        doReturn(mockResponse).when(loyaltyProvider)
                .executeGetRequest(expectedUrl, headers, LoyaltyQrTransactionAPIResponse.class);

        // Act
        LoyaltyQrTransactionAPIResponse response = loyaltyProvider.getVoucherDetail(headers, voucherId, "PASAJE");

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response);
    }

    @Test
    void givenDataValidWhenGetMerchantCampaignVouchersThenReturnResponse() throws Exception {
        // Arrange
        String personId = "12345";
        UUID merchantId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        MerchantCampaignVoucherAPIRequest request = MerchantCampaignVoucherAPIRequest.builder()
                .merchantId(merchantId)
                .campaignId(1)
                .categoryId(categoryId)
                .build();
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);

        String expectedUrl = String.format(
                BASE_URL + "/lealtad/beneficios/api/v1/beneficios-ganamovil/obtener-todos"
        );
        LoyaltyMerchantCampaignVoucherAPIResponse mockResponse = LoyaltySEResponseFixture.withDefaultGetCampaignVouchers();

        doReturn(mockResponse).when(loyaltyProvider)
                .executePostRequest(expectedUrl, request, headers, LoyaltyMerchantCampaignVoucherAPIResponse.class);

        // Act
        LoyaltyMerchantCampaignVoucherAPIResponse response = loyaltyProvider.getMerchantCampaignVouchers(headers, request);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response);
    }

    @Test
    void givenDataValidWhenGetVoucherTransactedListThenReturnResponse() throws Exception {
        // Arrange
        String personId = "123456";
        Map<String, String> headers = Map.of("sesion", "123", "idpersona", personId);

        String expectedUrl = String.format(
                BASE_URL + "/lealtad/beneficios/api/v1/vales-ganamovil/campana/%s/persona/%s/estado/%s",
                1,
                personId,
                "ACTIVO"
        );

        List<LoyaltyPostRegisterRedeemVoucherResponse> mockResponseList = LoyaltySEResponseFixture
                .withDefaultGetVoucherTransactedList();
        LoyaltyPostRegisterRedeemVoucherResponse[] mockResponseArray = mockResponseList.toArray(
                new LoyaltyPostRegisterRedeemVoucherResponse[0]
        );

        doReturn(mockResponseArray).when(loyaltyProvider)
                .executeGetRequest(expectedUrl, headers, LoyaltyPostRegisterRedeemVoucherResponse[].class);

        // Act
        List<LoyaltyPostRegisterRedeemVoucherResponse> response = loyaltyProvider.getVoucherTransactedList(
                headers, personId, 1, "ACTIVO"
        );

        // Assert
        assertNotNull(response);
        assertEquals(mockResponseList.size(), response.size());
        assertIterableEquals(mockResponseList, response);
    }

}
