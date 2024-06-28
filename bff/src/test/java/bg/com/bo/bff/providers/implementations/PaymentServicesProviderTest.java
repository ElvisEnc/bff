package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.payment.services.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.DeleteAffiliateServiceMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.payment.service.AffiliatedServiceMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.AffiliatedServiceMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.CategoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.CategoryMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.DeleteAffiliateServiceMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.DeleteAffiliateServiceMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.ListServicesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.ListServicesMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubCategoryCitiesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubCategoryCitiesMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubcategoriesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubcategoriesMWResponseFixture;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class PaymentServicesProviderTest {
    private PaymentServicesProvider provider;
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();
    ErrorMiddlewareProvider errorMiddlewareProvider;
    private Map<String, String> map;

    @BeforeEach
    void setUp() {
        this.map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "12344",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = Mockito.mock(MiddlewareConfig.class);
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        provider = new PaymentServicesProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        setField(provider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    void whenGetCategoriesPaymentServicesProviderThenExpectResponse() throws IOException {
        // Arrange
        CategoryMWResponse expectedResponse = CategoryMWResponseFixture.withDefault();
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        CategoryMWResponse response = provider.getCategories(new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response, expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void givenCategoryIdWhenGetSubcategoriesThenSubcategoriesMWResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String expected = Util.objectToString(SubcategoriesMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(expected)));

        // Act
        SubcategoriesMWResponse actual = provider.getSubcategories(2, map);

        // Assert
        assertNotNull(actual);
        assertEquals(expected, Util.objectToString(actual));
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void givenCategoryIdWhenGetSubcategoriesThenErrorNotAcceptable() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = SubcategoriesMWResponseFixture.errorMDWPSM_003();
        stubFor(get(anyUrl()).willReturn(jsonResponse(jsonResponse, HttpStatus.SC_NOT_ACCEPTABLE)));

        // Act
        try {
            SubcategoriesMWResponse actual = provider.getSubcategories(500, map);
        } catch (GenericException ex) {
            // Assert
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_003.getCode(), ex.getCode());
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_003.getHttpCode(), ex.getStatus());
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_003.getMessage(), ex.getMessage());
        }
    }

    @Test
    void givenSubCategoryIdWhenGetSubcategoryCitiesThenSubCategoryCitiesMWResponse() throws IOException {
        // Arrange

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String expected = Util.objectToString(SubCategoryCitiesMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(expected)));

        // Act
        SubCategoryCitiesMWResponse actual = provider.getSubcategoryCities(2, map);

        // Assert
        assertNotNull(actual);
        assertEquals(expected, Util.objectToString(actual));
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());

    }

    @Test
    void givenSubCategoryIdWhenGetSubcategoryCitiesThenErrorNotAcceptable() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = SubCategoryCitiesMWResponseFixture.errorMDWPSM004();
        stubFor(get(anyUrl()).willReturn(jsonResponse(jsonResponse, HttpStatus.SC_NOT_ACCEPTABLE)));

        // Act
        try {
            provider.getSubcategoryCities(500, map);
        } catch (GenericException ex) {
            // Assert
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_004.getCode(), ex.getCode());
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_004.getHttpCode(), ex.getStatus());
            assertEquals(PaymentServicesMiddlewareError.MDWPSM_004.getMessage(), ex.getMessage());
        }
    }

    @Test
    @DisplayName("Get service affiliations for a user given PersonId")
    void givenPersonIdWhenGetAffiliationsServicesThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(AffiliatedServiceMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        AffiliatedServiceMWResponse response = provider.getAffiliationsServices(123, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, AffiliatedServiceMWResponseFixture.withDefault());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Return empty list when no service affiliations for a PersonId")
    void givenPersonIdWithNoAffiliationsWhenGetAffiliationsServicesThenEmptyList() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(AffiliatedServiceMWResponseFixture.withErrorMDWPSM005());
        stubFor(get(anyUrl()).willReturn(badRequest().withBody(jsonResponse)));

        // Act
        AffiliatedServiceMWResponse response = provider.getAffiliationsServices(123, map);

        //Assert
        assertNull(response.getData());
    }

    @Test
    void giveErrorMiddlewareWhenGetListDebitCardThenGenericException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse()
                .withStatus(406)
                .withBody(Util.objectToString(errorMiddlewareProvider))));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            provider.getAffiliationsServices(123, map);
        });

        // Assert
        assertEquals("BAD_REQUEST", exception.getCode());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void giveInternalErrorWhenGetListDebitCardThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            provider.getAffiliationsServices(123, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Obtener servicios por subcategoria y ciudad")
    void givenValidDataWhenGetListServicesThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(ListServicesMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        ListServicesMWResponse response = provider.getServicesByCategoryAndCity(1, 2, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, ListServicesMWResponseFixture.withDefault());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Retorna una lista vacía cuando no hay servicios disponibles")
    void givenValidDataWhenGetListServicesThenEmptyList() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(ListServicesMWResponseFixture.withErrorMDWPSM005());
        stubFor(get(anyUrl()).willReturn(badRequest().withBody(jsonResponse)));

        // Act
        ListServicesMWResponse response = provider.getServicesByCategoryAndCity(1, 2, map);

        //Assert
        assertNull(response.getData());
    }

    @Test
    void givenDeleteAffiliateServiceMWRequestWhenDeleteAffiliationServiceThenOK() throws IOException {
        // Arrange
        DeleteAffiliateServiceMWRequest request = DeleteAffiliateServiceMWRequestFixture.withDefault();
        DeleteAffiliateServiceMWResponse expected = DeleteAffiliateServiceMWResponseFixture.withDefault();
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(expected);
        stubFor(post(anyUrl()).willReturn(jsonResponse(jsonResponse, HttpStatus.SC_OK)));

        // Act
        DeleteAffiliateServiceMWResponse actual = provider.deleteAffiliationService(request, new HashMap<>());

        // Assert
        assertEquals(expected.getData().getAffiliationNewCod(),actual.getData().getAffiliationNewCod());
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());

    }
}