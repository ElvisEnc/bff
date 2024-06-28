package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.export.account.statement.ExportRequest;
import bg.com.bo.bff.application.dtos.response.ExportResponse;
import bg.com.bo.bff.commons.enums.AccountStatementType;
import bg.com.bo.bff.commons.filters.AmountRangeFilter;
import bg.com.bo.bff.commons.filters.TypeFilter;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.response.AccountReportBasicResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementCsvProvider;
import bg.com.bo.bff.providers.interfaces.IAccountStatementPdfProvider;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.mappings.providers.statement.AccountStatementAdapter;
import bg.com.bo.bff.services.interfaces.IExportService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;
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
        List<AccountReportBasicResponse.AccountReportData> basicResponseData = basicResponse.getData();

        String extractType = request.getFilters().getType();

        Double min = request.getFilters().getAmount() != null ? request.getFilters().getAmount().getMin() : null;
        Double max = request.getFilters().getAmount() != null ? request.getFilters().getAmount().getMax() : null;

        if (Boolean.FALSE.equals(request.getFilters().getIsAsc())) Collections.reverse(basicResponseData);

        basicResponseData = new TypeFilter(AccountStatementType.getValueByCode(extractType)).apply(basicResponseData);
        basicResponseData = new AmountRangeFilter(min, max).apply(basicResponseData);

        basicResponseData = accountStatementAdapter.mapping(basicResponseData);

        String format = request.getFormat();
        String base64 = Objects.equals(format, "PDF") ? Util.encodeByteArrayToBase64(pdfProvider.generatePdf(basicResponseData, request, accountId)) : Util.encodeByteArrayToBase64(csvProvider.generateCsv(basicResponseData));

        ExportResponse response = new ExportResponse();
        response.setData(base64);
        return response;
    }
}
