package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementRequestFixture;
import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementResponseFixture;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.mappings.providers.account.IOwnAccountsMapper;
import bg.com.bo.bff.mappings.providers.account.OwnAccountsMapper;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.OwnAccountMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.providers.interfaces.IOwnAccountsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountStatementServiceTest {
    @InjectMocks
    private AccountStatementService service;
    @Mock
    private IAccountStatementProvider provider;
    @Spy
    private IOwnAccountsMapper mapper = new OwnAccountsMapper();
    @Mock
    private AccountStatementService self;

    @Test
    void givenAccountStatementsRequestWhenAccountStatementsThenListAccounts() throws IOException {
        // Arrange
        AccountStatementsRequest requestMock = AccountStatementRequestFixture.getDefaultAccountStatementsRequest();
        List<AccountStatementsResponse> expected = AccountStatementResponseFixture.getDefaultAccountStatementsResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getAccountStatementsCache(any(), any(), any(), any())).thenReturn(expected);

        // Act
        List<AccountStatementsResponse> response = service.getAccountStatement("123", "123456", requestMock, new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(self).getAccountStatementsCache(any(), any(), any(), any());
        verify(mapper).mapperRequest(any(), any(), any(), any(), any());
    }

    @Test
    void givenAccountStatementsRequestWhenAccountStatementsCacheThenListAccounts() throws IOException {
        // Arrange
        AccountStatementsMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultAccountStatementsMWRequest();
        AccountStatementsMWResponse expected = OwnAccountMWResponseFixture.withDefaultAccountReportBasicResponse();
        when(provider.getAccountStatements(any(), any())).thenReturn(expected);

        // Act
        List<AccountStatementsResponse> response = service.getAccountStatementsCache(requestMock, new HashMap<>(), "123", false);

        // Assert
        assertNotNull(response);
        verify(provider).getAccountStatements(any(), any());
        verify(mapper).convertResponse(any(AccountStatementsMWResponse.class));
    }

    @Test
    void givenEmptyWhenGetAccountStatementsCacheThenEmptyListAccounts() throws IOException {
        // Arrange
        AccountStatementsMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultAccountStatementsMWRequest();
        AccountStatementsMWResponse expected = OwnAccountMWResponseFixture.withDefaultAccountReportBasicResponseNull();
        when(provider.getAccountStatements(any(), any())).thenReturn(expected);

        // Act
        List<AccountStatementsResponse> response = service.getAccountStatementsCache(requestMock, new HashMap<>(), "123", false);

        // Assert
        assertNotNull(response);
        verify(provider).getAccountStatements(any(), any());
        verify(mapper).convertResponse(any(AccountStatementsMWResponse.class));
    }

    @Test
    void givenNullWhenGetAccountStatementsCacheThenEmptyListAccounts() throws IOException {
        // Arrange
        AccountStatementsMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultAccountStatementsMWRequest();
        when(provider.getAccountStatements(any(), any())).thenReturn(null);

        // Act
        List<AccountStatementsResponse> response = service.getAccountStatementsCache(requestMock, new HashMap<>(), "123", false);

        // Assert
        assertNotNull(response);
        verify(provider).getAccountStatements(any(), any());
    }
}