package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;
import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementRequestFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountReportBasicResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountMWResponseFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class AccountStatementProviderTest {

    private AccountStatementProvider accountStatementProvider;
    private TokenMiddlewareProvider tokenMiddlewareProviderMock;
    private MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactoryMock;
    private ErrorMiddlewareProvider errorMiddlewareProvider;
    private String init = "1";
    private String total = "10";


    @BeforeEach
    void init() {

        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        tokenMiddlewareProviderMock = Mockito.mock(TokenMiddlewareProvider.class);
        middlewareConfig = Mockito.mock(MiddlewareConfig.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        accountStatementProvider = new AccountStatementProvider(tokenMiddlewareProviderMock, middlewareConfig, httpClientFactoryMock);

        setField(accountStatementProvider, "init", init);
        setField(accountStatementProvider, "total", total);
        setField(accountStatementProvider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    void getAccountStatementSuccess() throws IOException {
        //Arrange
        String jsonResponse = new ObjectMapper().writeValueAsString(OwnAccountMWResponseFixture.withDefaultAccountReportBasicResponse());
        stubFor(post(anyUrl()).willReturn(okJson(jsonResponse)));

        //Act
        AccountReportBasicResponse response = accountStatementProvider.getAccountStatement(AccountStatementRequestFixture.withDefaultExtractRequest(), "aeraer", "4354", "654645", false);

        //Assert
        assertNotNull(response);
    }

    @Test
    void getAccountStatementWhenServerErrorThenError500() throws IOException {
        //Arrange
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("500")
                        .description("500")
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse().withStatus(500).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        //Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            accountStatementProvider.getAccountStatement(AccountStatementRequestFixture.withDefaultExtractRequest(), "token", "accountId", "extractId", false);
        });

        //Assert
        assertTrue(exception.getCode().contains("INTERNAL_SERVER_ERROR"));
    }

    @Test
    void getAccountStatementWhenNoRecordsErrorThenReturnEmptyData() throws IOException {
        //Arrange
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("MDWACM-008")
                        .description("MDWACM-008")
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse().withStatus(404).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        //Act
        AccountReportBasicResponse response = accountStatementProvider.getAccountStatement(AccountStatementRequestFixture.withDefaultExtractRequest(), "token", "accountId", "extractId", false);

        //Assert
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void getAccountStatementWhenUnexpectedErrorOccursThenRuntimeException() {
        //Arrange
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        //Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountStatementProvider.getAccountStatement(AccountStatementRequestFixture.withDefaultExtractRequest(), "token", "accountId", "extractId", false);
        });

        //Assert
        assertEquals("Hubo un error no controlado al crear el cliente", exception.getMessage());
    }
}