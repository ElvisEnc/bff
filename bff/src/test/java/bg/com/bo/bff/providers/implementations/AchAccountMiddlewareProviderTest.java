package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.response.destination.account.AddAccountResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AchAccountMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QrListMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.*;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QrMWResponseFixture;
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchAccountMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchAccountMiddlewareResponse;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class AchAccountMiddlewareProviderTest {
    private AchAccountMiddlewareProvider provider;
    TokenMiddlewareProvider tokenMiddlewareProviderMock;
    MiddlewareConfig middlewareConfig;
    IHttpClientFactory httpClientFactoryMock;
    private final ClientToken clientTokenMock = ClientTokenFixture.withDefault();
    private Map<String, String> map;

    @BeforeEach
    void setUp() throws IOException {
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
        middlewareConfig = MiddlewareConfigFixture.withDefault();
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        provider = new AchAccountMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        setField(provider, "middlewareConfig", middlewareConfig);
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientTokenMock);
    }

    // Add Ach Accounts
    @Test
    void givenValidaDataWhenAddAchAccountThenReturnOk() throws IOException {
        // Arrange
        AddAchAccountBasicRequest requestMock = AchAccountMWRequestFixture.withDefaultOKAddAchAccountBasicRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = AchAccountMWResponseFixture.withDefaultAddAchAccountMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        AddAccountResponse response = provider.addAchAccount(requestMock, map);

        // Assert
        assertNotNull(response);
        assertEquals(response.getId().toString(), expectedResponse.getData().getIdentifier());
    }

    @Test
    void givenValidaDataWhenAddAchAccountThenReturnError() throws IOException {
        // Arrange
        AddAchAccountBasicRequest requestMock = AchAccountMWRequestFixture.withDefaultOKAddAchAccountBasicRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ApiDataResponse.of(new AddAccountMWResponse());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> provider.addAchAccount(requestMock, map));

        // Assert
        assertNotNull(exception);
        assertEquals(exception.getCode(), AchAccountMiddlewareError.ERROR_ADD_ACCOUNT.getCode());
        assertEquals(exception.getMessage(), AchAccountMiddlewareError.ERROR_ADD_ACCOUNT.getMessage());
    }

    // Delete Ach Accounts
    @Test
    void givenValidaDataWhenDeleteAchAccountThenReturnOk() throws IOException {
        // Arrange
        DeleteAchAccountMWRequest requestMock = AchAccountMWRequestFixture.withDefaultDeleteAchAccountMWRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = AchAccountMWResponseFixture.withDefaultAddAchAccountMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.deleteAchAccount("123", 123456,654321, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(AchAccountMiddlewareResponse.SUCCESS_DELETE_ACCOUNT));
    }

    @Test
    void givenValidaDataWhenDeleteAchAccountThenReturnError() throws IOException {
        // Arrange
        DeleteAchAccountMWRequest requestMock = AchAccountMWRequestFixture.withDefaultDeleteAchAccountMWRequest();
        ApiDataResponse<AddAccountMWResponse> expectedResponse = ApiDataResponse.of(new AddAccountMWResponse());
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(delete(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        GenericResponse response = provider.deleteAchAccount("123", 123456,654321, map);

        // Assert
        assertNotNull(response);
        assertEquals(response, GenericResponse.instance(AchAccountMiddlewareResponse.ERROR_DELETE_ACCOUNT));
    }

    // Get Bank List and Office Branches
    @Test
    void givenValidBankCodeWhenGetBanksThenExpectResponse() throws IOException {
        // Arrange
        BanksMWResponse expectedResponse = AchAccountMWResponseFixture.withDefaultBanksMWResponse();
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        BanksMWResponse actualResponse = provider.getBanks(map);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getData().size(), actualResponse.getData().size());
        assertEquals(expectedResponse.getData().get(0).getCode(), actualResponse.getData().get(0).getCode());
        assertEquals(expectedResponse.getData().get(0).getDescription(), actualResponse.getData().get(0).getDescription());
    }

    @Test
    void giveValidBankCodeWhenGetAllBranchOfficeBankThenExpectResponse() throws IOException {
        // Arrange
        BranchOfficeMWResponse responseExpected = AchAccountMWResponseFixture.withDefaultBranchOfficeMWResponse();
        String jsonResponse = Util.objectToString(responseExpected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        BranchOfficeMWResponse response = provider.getAllBranchOfficeBank("123", map);

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), responseExpected.getData());
    }

    // Get Ach Accounts
    @Test
    void givePersonCodeWhenGetAchAccountsThenExpectResponse() throws IOException {
        // Arrange
        AchAccountsMWResponse responseExpected = AchAccountMWResponseFixture.withDefaultAchAccountMWResponse();
        String jsonResponse = Util.objectToString(responseExpected);
        stubFor(get(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        AchAccountsMWResponse response = provider.getAchAccounts("123", map);

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), responseExpected.getData());
    }

    // Get History QR
    @Test
    void givePersonCodeWhenGetQrListThenExpectResponse() throws IOException {
        // Arrange
        QrListMWRequest requestMock= AchAccountMWRequestFixture.withDefaultQrListMWRequest();
        QrListMWResponse responseExpected=QrMWResponseFixture.withDefaultQrListMWResponse();
        String jsonResponse = Util.objectToString(responseExpected);
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        // Act
        QrListMWResponse response = provider.getListQrGeneratePaidMW(requestMock, "123", map);

        // Assert
        assertNotNull(response);
        assertEquals(response.getData(), responseExpected.getData());
    }
}