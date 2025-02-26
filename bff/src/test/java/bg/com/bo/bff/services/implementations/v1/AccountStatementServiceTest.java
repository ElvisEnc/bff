package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementRequestFixture;
import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.TransferMovementsRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementResponseFixture;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.TransferMovementsResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.mappings.providers.account.IOwnAccountsMapper;
import bg.com.bo.bff.mappings.providers.account.OwnAccountsMapper;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.OwnAccountMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.ReportTransfersMWRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.ReportTransfersMWResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.providers.models.enums.middleware.account.statement.AccountStatementMiddlewareError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
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
        this.service = new AccountStatementService(provider, mapper);
    }

    @Test
    void givenAccountStatementsRequestWhenAccountStatementsThenListAccounts() throws IOException {
        // Arrange
        AccountStatementsRequest requestMock = AccountStatementRequestFixture.getDefaultAccountStatementsRequest();
        List<AccountStatementsResponse> expected = AccountStatementResponseFixture.getDefaultAccountStatementsResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getAccountStatementsCache(any(), any(), any(), any())).thenReturn(expected);

        // Act
        List<AccountStatementsResponse> response = service.getAccountStatement("123", "123456", requestMock, map);

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
        List<AccountStatementsResponse> response = service.getAccountStatementsCache(requestMock, map, "123", false);

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
        List<AccountStatementsResponse> response = service.getAccountStatementsCache(requestMock, map, "123", false);

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
        List<AccountStatementsResponse> response = service.getAccountStatementsCache(requestMock, map, "123", false);

        // Assert
        assertNotNull(response);
        verify(provider).getAccountStatements(any(), any());
    }

    @Test
    void givenValidRequestWhenGetTranferMovementsThenListTransferMovements() throws IOException {
        // Arrange
        TransferMovementsRequest requestMock = AccountStatementRequestFixture.getDefaultTransferMovementsRequest();
        List<TransferMovementsResponse> expected = AccountStatementResponseFixture.getDefaultTransferMovementsResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getTransferMovementsCache(any(), any(), any(), any())).thenReturn(expected);

        // Act
        List<TransferMovementsResponse> response = service.getTransferMovements("123", "123456", requestMock, map);

        // Assert
        assertNotNull(response);
        verify(self).getTransferMovementsCache(any(), any(), any(), any());
        verify(mapper).mapperRequest(any(), any(), any());
    }

    @Test
    void givenValidFiltersAmountWhenGetTranferMovementsThenListTransferMovements() throws IOException {
        // Arrange
        TransferMovementsRequest requestMock = AccountStatementRequestFixture.getDefaultTransferMovementsRequest();
        requestMock.setFilters(AccountStatementRequestFixture.getFiltersFillFilter());
        List<TransferMovementsResponse> expected = AccountStatementResponseFixture.getDefaultTransferMovementsResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getTransferMovementsCache(any(), any(), any(), any())).thenReturn(expected);

        // Act
        List<TransferMovementsResponse> response = service.getTransferMovements("123", "123456", requestMock, map);

        // Assert
        assertNotNull(response);
        verify(self).getTransferMovementsCache(any(), any(), any(), any());
        verify(mapper).mapperRequest(any(), any(), any());
    }

    @Test
    void givenInvalidCriteriaFilterWhenGetTransferMovementsThenGenericException() {
        // Arrange
        TransferMovementsRequest requestMock = AccountStatementRequestFixture.getDefaultTransferMovementsRequest();
        requestMock.setFilters(AccountStatementRequestFixture.getFiltersFillFilter());
        requestMock.getFilters().getSearchCriteria().setParameters(List.of("DATE"));
        ReflectionTestUtils.setField(service, "self", self);

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () ->
                service.getTransferMovements("123", "123456", requestMock, map)
        );

        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals(AccountStatementMiddlewareError.INVALID_SEARCH_PARAMS.getMessage(), exception.getMessage())
        );
    }

    @Test
    void givenValidRequestWhenGetTransferMovementsCacheThenListTransferMovements() throws IOException {
        // Arrange
        ReportTransfersMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultTransferMovementsMWRequest();
        ReportTransfersMWResponse expected = OwnAccountMWResponseFixture.withDefaultTransferMovementsResponse();
        when(provider.getTransferMovements(any(), any())).thenReturn(expected);

        // Act
        List<TransferMovementsResponse> response = service.getTransferMovementsCache(requestMock, map, "123", false);

        // Assert
        assertNotNull(response);
        verify(provider).getTransferMovements(any(), any());
        verify(mapper).convertResponse(any(ReportTransfersMWResponse.class));
    }

    @Test
    void givenEmptyWhenGetTransferMoevementsCacheThenEmptyListTransferMovements() throws IOException {
        // Arrange
        ReportTransfersMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultTransferMovementsMWRequest();
        ReportTransfersMWResponse expected = OwnAccountMWResponseFixture.withDefaultTransferMovementsResponseNull();
        when(provider.getTransferMovements(any(), any())).thenReturn(expected);

        // Act
        List<TransferMovementsResponse> response = service.getTransferMovementsCache(requestMock, map, "123", false);

        // Assert
        assertNotNull(response);
        verify(provider).getTransferMovements(any(), any());
        verify(mapper).convertResponse(any(ReportTransfersMWResponse.class));
    }

    @Test
    void givenNullWhenGetTransferMovementsCacheThenEmptyListTransferMovements() throws IOException {
        // Arrange
        ReportTransfersMWRequest requestMock = OwnAccountMWRequestFixture.withDefaultTransferMovementsMWRequest();
        when(provider.getTransferMovements(any(), any())).thenReturn(null);

        // Act
        List<TransferMovementsResponse> response = service.getTransferMovementsCache(requestMock, map, "123", false);

        // Assert
        assertNotNull(response);
        verify(provider).getTransferMovements(any(), any());
    }
}