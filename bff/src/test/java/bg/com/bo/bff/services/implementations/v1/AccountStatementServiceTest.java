package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ExtractRequestFixture;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponseFixture;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountStatementServiceTest {

    @Mock
    private AccountStatementService service;

    @Mock
    private IAccountStatementProvider provider;

    @BeforeEach
    void setup() throws IOException {
        service = new AccountStatementService(provider);
        ClientToken mockToken = new ClientToken();
        mockToken.setAccessToken("dsfsdfsdfsdfsdf");
        Mockito.when(provider.generateToken()).thenReturn(mockToken);
    }

    @Test
    void getAccountStatementSuccess() throws IOException {
        //Arrange
        ExtractRequest mockRequest = ExtractRequestFixture.withDefault();
        AccountReportBasicResponse mockResponse = AccountReportBasicResponseFixture.withDefault();
        String accountId = "123";
        Mockito.when(provider.getAccountStatement(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(mockResponse);

        //Act
        ExtractDataResponse response = service.getAccountStatement(mockRequest, accountId);

        //Assert
        assertNotNull(response);
    }
}