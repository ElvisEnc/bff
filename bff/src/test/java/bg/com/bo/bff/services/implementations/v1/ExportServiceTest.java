package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementRequestFixture;
import bg.com.bo.bff.application.dtos.request.export.AccountStatementExportRequest;
import bg.com.bo.bff.application.dtos.request.export.AccountStatementExportRequestFixture;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementResponseFixture;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.application.dtos.response.export.AccountStatementExportResponse;
import bg.com.bo.bff.mappings.providers.export.ExportMapper;
import bg.com.bo.bff.mappings.providers.export.IExportMapper;
import bg.com.bo.bff.providers.interfaces.IAccountStatementCsvProvider;
import bg.com.bo.bff.providers.interfaces.IAccountStatementPdfProvider;
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
class ExportServiceTest {
    @InjectMocks
    private ExportService service;
    @Mock
    private IAccountStatementPdfProvider pdfProvider;
    @Mock
    private IAccountStatementCsvProvider csvProvider;
    @Spy
    private IExportMapper mapper = new ExportMapper();
    @Mock
    private AccountStatementService statementService;

    @Test
    void givenExportRequestPdfWhenAccountStatementsThenData() throws IOException {
        // Arrange
        AccountStatementExportRequest requestMock = AccountStatementExportRequestFixture.withDefaultAccountStatementExportRequest();
        List<AccountStatementsResponse> expectedResponse = AccountStatementResponseFixture.getDefaultAccountStatementsResponseExport();
        ReflectionTestUtils.setField(service, "statementService", statementService);
        when(statementService.getAccountStatementsCache(any(), any(), any(), any())).thenReturn(expectedResponse);
        when(pdfProvider.generatePdf(any(), any(), any())).thenReturn("data".getBytes());
        // Act
        AccountStatementExportResponse response = service.generateReport(requestMock, "123","321", new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(mapper).mapperRequest(any(), any(), any(), any(), any());
        verify(statementService).getAccountStatementsCache(any(), any(), any(), any());
    }

    @Test
    void givenExportRequestCsvWhenAccountStatementsThenData() throws IOException {
        // Arrange
        AccountStatementExportRequest requestMock = AccountStatementExportRequestFixture.withDefaultAccountStatementExportRequestCsv();
        List<AccountStatementsResponse> expectedResponse = AccountStatementResponseFixture.getDefaultAccountStatementsResponse();
        ReflectionTestUtils.setField(service, "statementService", statementService);
        when(statementService.getAccountStatementsCache(any(), any(), any(), any())).thenReturn(expectedResponse);
        when(csvProvider.generateCsv(any())).thenReturn("data".getBytes());
        // Act
        AccountStatementExportResponse response = service.generateReport(requestMock, "123", "321", new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(mapper).mapperRequest(any(), any(), any(), any(), any());
        verify(statementService).getAccountStatementsCache(any(), any(), any(), any());
    }

    @Test
    void givenExportRequestCsvDateNullWhenAccountStatementsThenData() throws IOException {
        // Arrange
        AccountStatementExportRequest requestMock = AccountStatementExportRequestFixture.withDefaultAccountStatementExportRequestOrderNull();
        List<AccountStatementsResponse> expectedResponse = AccountStatementResponseFixture.getDefaultAccountStatementsResponseExportCredit();
        ReflectionTestUtils.setField(service, "statementService", statementService);
        when(statementService.getAccountStatementsCache(any(), any(), any(), any())).thenReturn(expectedResponse);
        when(csvProvider.generateCsv(any())).thenReturn("data".getBytes());
        // Act
        AccountStatementExportResponse response = service.generateReport(requestMock, "123", "321", new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(mapper).mapperRequest(any(), any(), any(), any(), any());
        verify(statementService).getAccountStatementsCache(any(), any(), any(), any());
    }
}