package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.response.DeleteThirdAccountResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.BanksMWResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.responses.*;
import bg.com.bo.bff.providers.dtos.responses.account.ach.AchAccountMWResponse;
import bg.com.bo.bff.providers.dtos.responses.account.ach.AchAccountMWResponseFixture;
import bg.com.bo.bff.providers.dtos.responses.qr.QrListMWResponse;
import bg.com.bo.bff.providers.dtos.responses.qr.QrListMWResponseFixture;
import bg.com.bo.bff.providers.mappings.ach.account.AchAccountMWtMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class AchAccountMiddlewareProviderTest {
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    private MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactoryMock;
    private AchAccountMiddlewareProvider achAccountMiddlewareProvider;
    private ClientToken clientTokenMock;
    private ErrorMiddlewareProvider errorMiddlewareProvider;
    private AchAccountMWtMapper mapper;

    @BeforeEach
    void setUp() {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = Mockito.mock(MiddlewareConfig.class);
        mapper = AchAccountMWtMapper.INSTANCE;
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        achAccountMiddlewareProvider = new AchAccountMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock, mapper);

        clientTokenMock = new ClientToken();
        clientTokenMock.setAccessToken(UUID.randomUUID().toString());
        setField(achAccountMiddlewareProvider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    void givenValidaDataWhenDeleteAccountThenReturnOk() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        GenericResponse expectedResponse = GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = achAccountMiddlewareProvider.deleteAchAccount("1", 1, "1", "127.0.0.1");

        // Assert
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
    }

    @Test
    void givenValidBankCodeWhenGetBanksThenExpectResponse() throws IOException {
        // Arrange
        BanksMWResponse expectedResponse = BanksMWResponseFixture.withDefault();
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        BanksMWResponse actualResponse = achAccountMiddlewareProvider.getBanks();

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getData().size(), actualResponse.getData().size());
        assertEquals(expectedResponse.getData().get(0).getCode(), actualResponse.getData().get(0).getCode());
        assertEquals(expectedResponse.getData().get(0).getDescription(), actualResponse.getData().get(0).getDescription());
    }

    @Test
    void givenValidDataWhenDeleteAchAccountThenReturnOk() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        GenericResponse expectedResponse = GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(delete(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse actualResponse = achAccountMiddlewareProvider.deleteAchAccount("1", 1, "1", "127.0.0.1");

        // Assert
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }

    @Test
    void giveValidBankCodeWhenGetAllBranchOfficeBankThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(BranchOfficeMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        BranchOfficeMWResponse response = achAccountMiddlewareProvider.getAllBranchOfficeBank(123);

        // Assert
        assertNotNull(response);
        assertEquals(response.getData().getResponse(), BranchOfficeMWResponseFixture.withDefault().getData().getResponse());
    }

    @Test
    void giveValidBankCodeWhenNoRecordsThenReturnEmptyData() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("MDWAAM-001")
                        .description("MDWAAM_001")
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse()
                .withStatus(404)
                .withBody(Util.objectToString(errorMiddlewareProvider))));

        // Act
        BranchOfficeMWResponse response = achAccountMiddlewareProvider.getAllBranchOfficeBank(123);

        // Assert
        assertTrue(response.getData().getResponse().isEmpty());
    }


    @Test
    void giveValidBankCodeWhenUnexpectedErrorOccursThenGenericException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new GenericException("Generic"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            achAccountMiddlewareProvider.getAllBranchOfficeBank(123);
        });

        // Assert
        assertEquals("Generic", exception.getMessage());
    }

    @Test
    void giveValidBankCodeWhenUnexpectedErrorOccursThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            achAccountMiddlewareProvider.getAllBranchOfficeBank(123);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }

    @Test
    void givePersonCodeWhenGetAchAccountsThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(AchAccountMWResponseFixture.withDefault());
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        AchAccountMWResponse response = achAccountMiddlewareProvider.getAchAccounts(1233, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), AchAccountMWResponseFixture.withDefault().getData());
    }

    @Test
    void givePersonCodeWhenGetAchAccountsThenReturnEmptyData() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("MDWAAM-004")
                        .description("MDWAAM_004")
                        .build()))
                .build();
        stubFor(get(anyUrl()).willReturn(aResponse()
                .withStatus(404)
                .withBody(Util.objectToString(errorMiddlewareProvider))));

        // Act
        AchAccountMWResponse response = achAccountMiddlewareProvider.getAchAccounts(1233, new HashMap<>());

        // Assert
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void giveUnexpectedErrorOccursWhenGetAchAccountsThenGenericException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new GenericException("Generic"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            achAccountMiddlewareProvider.getAchAccounts(1233, new HashMap<>());
        });

        // Assert
        assertEquals("Generic", exception.getMessage());
    }

    @Test
    void giveErrorWhenGetAchAccountsThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            achAccountMiddlewareProvider.getAchAccounts(1233, new HashMap<>());
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }

    @Test
    void givePersonCodeWhenGetQrListThenExpectResponse() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        String jsonResponse = Util.objectToString(QrListMWResponseFixture.withDefault());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        QrListMWResponse response = achAccountMiddlewareProvider.getListQrGeneratePaidMW(QrListRequestFixture.withDefault(), 123, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), QrListMWResponseFixture.withDefault().getData());
    }

    @Test
    void givePersonCodeWhenGetQrListThenReturnEmptyData() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("MDWAAM-001")
                        .description("MDWAAM-001")
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse()
                .withStatus(404)
                .withBody(Util.objectToString(errorMiddlewareProvider))));

        // Act
        QrListMWResponse response = achAccountMiddlewareProvider.getListQrGeneratePaidMW(QrListRequestFixture.withDefault(), 123, new HashMap<>());

        // Assert
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void giveUnexpectedErrorOccursWhenQrListThenGenericException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new GenericException("Generic"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            achAccountMiddlewareProvider.getListQrGeneratePaidMW(QrListRequestFixture.withDefault(), 123, new HashMap<>());
        });

        // Assert
        assertEquals("Generic", exception.getMessage());
    }

    @Test
    void giveErrorWhenGetQrListThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            achAccountMiddlewareProvider.getListQrGeneratePaidMW(QrListRequestFixture.withDefault(), 123, new HashMap<>());
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }
}