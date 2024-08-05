package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.ExportRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractPagination;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementExportResponse;
import bg.com.bo.bff.commons.enums.AccountStatementType;
import bg.com.bo.bff.commons.filters.AmountRangeFilter;
import bg.com.bo.bff.commons.filters.TypeFilter;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementCsvProvider;
import bg.com.bo.bff.providers.interfaces.IAccountStatementPdfProvider;
import bg.com.bo.bff.mappings.providers.statement.AccountStatementAdapter;
import bg.com.bo.bff.services.interfaces.IExportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ExportService implements IExportService {
    @Value("${account.statement.init}")
    private String init;
    @Value("${account.statement.total}")
    private String total;
    private final AccountStatementService statementService;
    private final IAccountStatementPdfProvider pdfProvider;
    private final IAccountStatementCsvProvider csvProvider;
    private final AccountStatementAdapter accountStatementAdapter;

    public ExportService(AccountStatementService statementService, IAccountStatementPdfProvider pdfProvider, IAccountStatementCsvProvider csvProvider, AccountStatementAdapter accountStatementAdapter) {
        this.statementService = statementService;
        this.pdfProvider = pdfProvider;
        this.csvProvider = csvProvider;
        this.accountStatementAdapter = accountStatementAdapter;
    }

    @Override
    public AccountStatementExportResponse generateReport(ExportRequest request, String accountId, Map<String, String> parameter) throws IOException {
        String startDate = request.getFilters().getStartDate();
        String endDate = request.getFilters().getEndDate();
        String key = accountId + "|" + startDate + "|" + endDate;

        AccountStatementsRequest statementsRequest = AccountStatementsRequest.builder()
                .filters(AccountStatementsRequest.AccountStatementsFilter.builder()
                        .pagination(ExtractPagination.builder()
                                .startDate(request.getFilters().getStartDate())
                                .endDate(request.getFilters().getEndDate())
                                .build())
                        .build())
                .build();
        AccountStatementsMWResponse basicResponse = statementService.getAccountStatementsCache(statementsRequest, accountId, init, total, parameter, key, true);
        List<AccountStatementsMWResponse.AccountStatementMW> basicResponseData = basicResponse.getData();

        String extractType = request.getFilters().getType();

        BigDecimal min = request.getFilters().getAmount() != null ? request.getFilters().getAmount().getMin() : null;
        BigDecimal max = request.getFilters().getAmount() != null ? request.getFilters().getAmount().getMax() : null;

        if (Boolean.FALSE.equals(request.getFilters().getIsAsc())) Collections.reverse(basicResponseData);

        basicResponseData = new TypeFilter(AccountStatementType.getValueByCode(extractType)).apply(basicResponseData);
        basicResponseData = new AmountRangeFilter(min, max).apply(basicResponseData);

        basicResponseData = accountStatementAdapter.mapping(basicResponseData);

        String format = request.getFormat();
        String base64 = Objects.equals(format, "PDF") ? Util.encodeByteArrayToBase64(pdfProvider.generatePdf(basicResponseData, request, accountId)) : Util.encodeByteArrayToBase64(csvProvider.generateCsv(basicResponseData));

        AccountStatementExportResponse response = new AccountStatementExportResponse();
        response.setData(base64);
        return response;
    }
}
