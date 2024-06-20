package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.debit.card.*;
import bg.com.bo.bff.providers.dtos.response.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCDetailMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.CreateAuthorizationOnlinePurchaseMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.CreateAuthorizationOnlinePurchaseMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.debit.card.DebitCardMWErrorResponseFixture;
import bg.com.bo.bff.providers.dtos.response.debit.card.DebitCardMWResponseFixture;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
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
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class DebitCardMiddlewareProviderTest {
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private DebitCardMiddlewareProvider debitCardMiddlewareProvider;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();
    private ErrorMiddlewareProvider errorMiddlewareProvider;
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
        debitCardMiddlewareProvider = new DebitCardMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        setField(debitCardMiddlewareProvider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    void givenValidDataWhenChangeAmountThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DebitCardMWResponseFixture.withDefault());
        stubFor(patch(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = debitCardMiddlewareProvider.changeAmount(
                DebitCardMWRequestFixture.withDefault(), map
        );

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT));
    }

    @Test
    void givenInvalidResponseWhenChangeAmountThenException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DebitCardMWErrorResponseFixture.withDefault());
        stubFor(patch(anyUrl()).willReturn(badRequest().withBody(jsonResponse)));

        // Act
        Exception exception = assertThrows(Exception.class, () -> debitCardMiddlewareProvider.changeAmount(
                DebitCardMWRequestFixture.withDefault(), map
        ));

        // Assert
        assertEquals(GenericException.class, exception.getClass());
        assertEquals(DebitCardMiddlewareError.MDWTJD_002.getHttpCode(), ((GenericException) exception).getStatus());
        assertEquals(DebitCardMiddlewareError.MDWTJD_002.getCode(), ((GenericException) exception).getCode());
        assertEquals(DebitCardMiddlewareError.MDWTJD_002.getMessage(), ((GenericException) exception).getMessage());
    }

    @Test
    @DisplayName("Get debit card list for user with given PersonCode")
    void givenPersonCodeWhenGetLisDebitCardThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(ListDebitCardMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        ListDebitCardMWResponse response = debitCardMiddlewareProvider.listDebitCard(123, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, ListDebitCardMWResponseFixture.withDefault());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Should return empty list for given PersonCode")
    void givenPersonCodeWithNoCardsWhenGetLisDebitCardThenEmptyList() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DebitCardMWErrorResponseFixture.withErrorMDWTJD004());
        stubFor(get(anyUrl()).willReturn(badRequest().withBody(jsonResponse)));

        // Act
        ListDebitCardMWResponse response = debitCardMiddlewareProvider.listDebitCard(123, map);

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
            debitCardMiddlewareProvider.listDebitCard(123, map);
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
            debitCardMiddlewareProvider.listDebitCard(123, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Get accounts list for debit card with given PersonCode and CardId")
    void givenPersonCodeAndCardIdWhenGetAccountLisDebitCardThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(AccountsDebitCardMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        AccountsDebitCardMWResponse response = debitCardMiddlewareProvider.accountListDebitCard(123, 123, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, AccountsDebitCardMWResponseFixture.withDefault());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void giveErrorMiddlewareWhenGetAccountListDebitCardThenGenericException() throws IOException {
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
            debitCardMiddlewareProvider.accountListDebitCard(123, 123, map);
        });

        // Assert
        assertEquals("BAD_REQUEST", exception.getCode());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void giveInternalErrorWhenGetAccountListDebitCardThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            debitCardMiddlewareProvider.accountListDebitCard(123, 123, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }

    @Test
    void givenPersonIdAndCardIdWhenGetListAuthorizationsThenDCInternetAuthorizationNWResponse() throws IOException {
        // Arrange
        DCInternetAuthorizationNWResponse expected = DCInternetAuthorizationNWResponseFixture.withDefault();
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DCInternetAuthorizationNWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        String personId = "1234";
        String cardId = "123";

        // Act
        DCInternetAuthorizationNWResponse actual = debitCardMiddlewareProvider.getListAuthorizations(personId, cardId, map);

        // Assert
        assertNotNull(actual);
        assertEquals(expected.getData().get(0).getInternetIdTjTD(), actual.getData().get(0).getInternetIdTjTD());
    }

    @Test
    void givenPersonIdAndCardIdWhenGetListAuthorizationsThenErrorMDWTJD_005() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = DCInternetAuthorizationNWResponseFixture.withErrorMDWTJD005();
        stubFor(get(anyUrl()).willReturn(jsonResponse(jsonResponse, HttpStatus.SC_NOT_ACCEPTABLE)));
        String personId = "1234";
        String cardId = "123";

        // Act
        try {
            DCInternetAuthorizationNWResponse actual = debitCardMiddlewareProvider.getListAuthorizations(personId, cardId, map);
        } catch (GenericException ex) {
            // Assert
            assertEquals(DebitCardMiddlewareError.MDWTJD_005.getCode(), ex.getCode());
            assertEquals(DebitCardMiddlewareError.MDWTJD_005.getHttpCode(), ex.getStatus());
            assertEquals(DebitCardMiddlewareError.MDWTJD_005.getMessage(), ex.getMessage());
        }
    }

    @Test
    @DisplayName("Delete Internet Purchase Authorization")
    void givenPersonCodeAndCardIdAndAuthIdWhenDeleteAuthThenExpectResponse() throws IOException {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_DELETE_AUTH_PURCHASE);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DeleteAuthPurchaseMWResponseFixture.withDefault());
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = debitCardMiddlewareProvider.deleteAuth(DeleteAuthPurchaseMWRequestFixture.withDefault(), map);

        // Assert
        assertNotNull(response);
        assertEquals(response, expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("Delete Internet Purchase Authorization - Expect Failure Response")
    void givenPersonCodeAndCardIdAndAuthIdWhenDeleteAuthThenErrorDeleteAuth() throws IOException {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_DELETE_AUTH_PURCHASE);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DeleteAuthPurchaseMWResponseFixture.errorDefault());
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = debitCardMiddlewareProvider.deleteAuth(DeleteAuthPurchaseMWRequestFixture.withDefault(), map);

        // Assert
        assertNotNull(response);
        assertEquals(response, expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void givenValidDataWhenDetailThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DebitCardMWResponseFixture.withDefaultDetail());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        DCDetailMWResponse response = debitCardMiddlewareProvider.detail("123", "123", map);

        // Assert
        assertNotNull(response);
        assertEquals(response, DebitCardMWResponseFixture.withDefaultDetail());
    }

    @Test
    void givenValidDataWhenLockStatusThenExpectResponse() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = "{ \"data\": { \"code\": \"COD000\", \"message\": \"Operación concluida con éxito.\" } }";
        stubFor(patch(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = debitCardMiddlewareProvider.lockStatus(
                DebitCardMWRequestFixture.withDefaultLockStatus(), map
        );

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK));
    }

    @Test
    void givenInternalErrorWhenLockStatusThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));
        DCLockStatusMWRequest request = DebitCardMWRequestFixture.withDefaultLockStatus();

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            debitCardMiddlewareProvider.lockStatus(request, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }

    @Test
    void givenErrorMiddlewareWhenLockStatusThenGenericException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        stubFor(patch(anyUrl()).willReturn(aResponse()
                .withStatus(406)
                .withBody(Util.objectToString(errorMiddlewareProvider))));
        DCLockStatusMWRequest request = DebitCardMWRequestFixture.withDefaultLockStatus();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            debitCardMiddlewareProvider.lockStatus(request, map);
        });

        // Assert
        assertEquals("BAD_REQUEST", exception.getCode());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void givenCreateAuthorizationOnlinePurchaseMWRequestWhenCreateAuthorizationOnlinePurchaseThenCreateAuthorizationOnlinePurchaseMWResponse() throws IOException {
        // Arrange
        CreateAuthorizationOnlinePurchaseMWRequest request = CreateAuthorizationOnlinePurchaseMWRequestFixture.withDefault();
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(CreateAuthorizationOnlinePurchaseMWResponseFixture.withDefault());
        stubFor(patch(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        CreateAuthorizationOnlinePurchaseMWResponse actual = debitCardMiddlewareProvider.createAuthorizationOnlinePurchase(request, map);

        // Assert
        assertNotNull(actual);
    }

    @Test
    void givenValidDataWhenModifyAccountsOrderThenExpectResponse() throws IOException {
        // Arrange
        String jsonResponse = Util.objectToString(DebitCardMWResponseFixture.withDefaultAccountsOrder());
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        stubFor(patch(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = debitCardMiddlewareProvider.modifyAccountsOrder(
                DebitCardMWRequestFixture.withDefaultAccountsOrder(), map
        );

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_MODIFY_ACCOUNTS_ORDER));
    }

    @Test
    void givenInternalErrorWhenModifyAccountsOrderThenRuntimeException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));
        DCAccountsOrderMWRequest request = DebitCardMWRequestFixture.withDefaultAccountsOrder();

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            debitCardMiddlewareProvider.modifyAccountsOrder(request, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }

    @Test
    void givenErrorMiddlewareWhenModifyAccountsOrderThenGenericException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        stubFor(patch(anyUrl()).willReturn(aResponse()
                .withStatus(406)
                .withBody(Util.objectToString(errorMiddlewareProvider))));
        DCAccountsOrderMWRequest request = DebitCardMWRequestFixture.withDefaultAccountsOrder();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            debitCardMiddlewareProvider.modifyAccountsOrder(request, map);
        });

        // Assert
        assertEquals("BAD_REQUEST", exception.getCode());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void givenPersonCodeAndCardIdWhenActiveDebitCardSecureThenExpectResponse() throws IOException {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVE_ASSURANCE);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(DCLimitsMWResponseFixture.withDefault());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = debitCardMiddlewareProvider.activeDebitCardSecure(UpdateDebitCardSecureMWRequestFixture.withDefault(), map);

        // Assert
        assertNotNull(response);
        assertEquals(response, expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void givenPersonCodeAndCardIdWhenActiveDebitCardSecureThenErrorDeleteAuth() throws IOException {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_ACTIVE_ASSURANCE);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(new DCLimitsMWResponse(new DCLimitsMWResponse.LimitsData()));
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = debitCardMiddlewareProvider.activeDebitCardSecure(UpdateDebitCardSecureMWRequestFixture.withDefault(), map);

        // Assert
        assertNotNull(response);
        assertEquals(response, expectedResponse);
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void giveErrorMiddlewareWhenActiveDebitCardSecureThenGenericException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("MDWTJD-010")
                        .description("MDWTJD-010")
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse()
                .withStatus(406)
                .withBody(Util.objectToString(errorMiddlewareProvider))));
        UpdateDebitCardSecureMWRequest request = UpdateDebitCardSecureMWRequestFixture.withDefault();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            debitCardMiddlewareProvider.activeDebitCardSecure(request, map);
        });

        // Assert
        assertEquals("NOT_ACCEPTABLE", exception.getCode());
        verify(httpClientFactoryMock).create();
        verify(tokenMiddlewareProviderMock).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void giveInternalErrorWhenActiveDebitCardSecureThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));
        UpdateDebitCardSecureMWRequest request = UpdateDebitCardSecureMWRequestFixture.withDefault();

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            debitCardMiddlewareProvider.activeDebitCardSecure(request, map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }
}
