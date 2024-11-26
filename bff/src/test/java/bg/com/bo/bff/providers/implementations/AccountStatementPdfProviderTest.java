package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.request.export.AccountStatementExportRequest;
import bg.com.bo.bff.application.dtos.request.export.AccountStatementExportRequestFixture;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementResponseFixture;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountStatementPdfProviderTest {
    @InjectMocks
    private AccountStatementPdfProvider pdfProvider;

    @Test
    void givenAccountStatementRequestWhenGeneratePdfThenSuccessfully() throws IOException {
        // Arrange
        List<AccountStatementsResponse> list = AccountStatementResponseFixture.getDefaultAccountStatementsResponse();
        AccountStatementExportRequest request = AccountStatementExportRequestFixture.withDefaultAccountStatementExportRequest();

        // Act
        byte[] response = pdfProvider.generatePdf(list, request, "123");

        assertNotNull(response);
    }

    @Test
    void givenAccountStatementRequestWhenGeneratePdfThenEmptyAccountStatements() throws IOException {
        // Arrange
        List<AccountStatementsResponse> list = new ArrayList<>();
        AccountStatementExportRequest request = AccountStatementExportRequestFixture.withDefaultAccountStatementExportRequest();

        // Act
        byte[] response = pdfProvider.generatePdf(list, request, "123");

        assertNotNull(response);
    }

    @Test
    void shouldHandleExceptionWhileGeneratingPdf() {
        // Arrange
        List<AccountStatementsResponse> list = AccountStatementResponseFixture.getDefaultAccountStatementsResponse();
        AccountStatementExportRequest request = AccountStatementExportRequestFixture.withDefaultAccountStatementExportRequest();

        try (MockedStatic<Image> mockedImage = Mockito.mockStatic(Image.class)) {
            mockedImage.when(() -> Image.getInstance(Mockito.any(byte[].class))).thenThrow(new DocumentException("Simulated document exception"));

            // Act
            byte[] response = pdfProvider.generatePdf(list, request, "123");

            // Assert
            assertNotNull(response);
        } catch (Exception ignored) {
        }
    }
}