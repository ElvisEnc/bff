package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ExportRequest;
import bg.com.bo.bff.application.dtos.response.ExportResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementCsvProvider;
import bg.com.bo.bff.providers.interfaces.IAccountStatementPdfProvider;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.commons.adapters.AccountStatementAdapter;
import bg.com.bo.bff.services.interfaces.IExportService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Service
public class ExportService implements IExportService {
    private final IAccountStatementProvider iAccountStatementProvider;

    private final IAccountStatementPdfProvider pdfProvider;

    private final IAccountStatementCsvProvider csvProvider;

    private final AccountStatementAdapter accountStatementAdapter;

    public ExportService(IAccountStatementProvider iAccountStatementProvider, IAccountStatementPdfProvider pdfProvider, IAccountStatementCsvProvider csvProvider, AccountStatementAdapter accountStatementAdapter) {
        this.iAccountStatementProvider = iAccountStatementProvider;
        this.pdfProvider = pdfProvider;
        this.csvProvider = csvProvider;
        this.accountStatementAdapter = accountStatementAdapter;
    }

    @Override
    public ExportResponse generateReport(ExportRequest request, String accountId) throws IOException {
        ClientToken clientToken = iAccountStatementProvider.generateToken();

        AccountReportBasicResponse basicResponse = iAccountStatementProvider.getAccountStatementForExport(request, accountId, clientToken.getAccessToken());
        List<AccountReportBasicResponse.AccountReportData> basicResponseData = accountStatementAdapter.mapping(basicResponse);

        String format = request.getFormat();

        String base64 = Objects.equals(format, "PDF")
                ? Util.encodeByteArrayToBase64(pdfProvider.generatePdf(basicResponseData, request, accountId))
                : Util.encodeByteArrayToBase64(csvProvider.generateCsv(basicResponseData));


        ExportResponse response = new ExportResponse();
        response.setData(base64);
        return response;
    }
}
