package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AmountRange;
import bg.com.bo.bff.application.dtos.request.export.AccountStatementExportRequest;
import bg.com.bo.bff.application.dtos.response.export.AccountStatementExportResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.commons.enums.account.statement.AccountStatementType;
import bg.com.bo.bff.commons.filters.OrderFilter;
import bg.com.bo.bff.commons.filters.RangeFilter;
import bg.com.bo.bff.commons.filters.TypeFilter;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.mappings.providers.export.IExportMapper;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.interfaces.IAccountStatementCsvProvider;
import bg.com.bo.bff.providers.interfaces.IAccountStatementPdfProvider;
import bg.com.bo.bff.mappings.providers.export.ExportMapper;
import bg.com.bo.bff.services.interfaces.IExportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Service
public class ExportService implements IExportService {
    @Value("${account.statement.init}")
    private String init;
    @Value("${account.statement.total}")
    private String total;
    private final AccountStatementService statementService;
    private final IAccountStatementPdfProvider pdfProvider;
    private final IAccountStatementCsvProvider csvProvider;
    private final IExportMapper mapper;

    public ExportService(AccountStatementService statementService, IAccountStatementPdfProvider pdfProvider, IAccountStatementCsvProvider csvProvider, ExportMapper mapper) {
        this.statementService = statementService;
        this.pdfProvider = pdfProvider;
        this.csvProvider = csvProvider;
        this.mapper = mapper;
    }

    @Override
    public AccountStatementExportResponse generateReport(AccountStatementExportRequest request, String accountId, String personId, Map<String, String> parameter) throws IOException {
        String key = accountId + "|" + request.getFilters().getDate().getStart() + "|" + request.getFilters().getDate().getEnd();
        AccountStatementsMWRequest mwRequest = mapper.mapperRequest(personId, accountId, init, total, request);

        List<AccountStatementsResponse> list = statementService.getAccountStatementsCache(mwRequest, parameter, key, true);

        String field = (request.getFilters().getOrder() != null) ? request.getFilters().getOrder().getField() : "DATE";
        boolean desc = (request.getFilters().getOrder() == null) || request.getFilters().getOrder().getDesc();
        Map<String, Function<AccountStatementsResponse, ? extends Comparable<?>>> comparatorOptions = new HashMap<>();
        comparatorOptions.put("AMOUNT", AccountStatementsResponse::getAmount);
        comparatorOptions.put("DATE", response -> LocalDate.parse(UtilDate.getDateGenericFormat(response.getMovementDate()), UtilDate.getDateFormatter()));
        list = new OrderFilter<>(field, desc, comparatorOptions).apply(list);

        if (request.getFilters().getAmount() != null) {
            BigDecimal min = Optional.of(request.getFilters().getAmount())
                    .map(AmountRange::getMin)
                    .orElse(null);
            BigDecimal max = Optional.of(request.getFilters().getAmount())
                    .map(AmountRange::getMax)
                    .orElse(null);
            list = new RangeFilter<>(min, max, AccountStatementsResponse::getAmount).apply(list);
        }

        list = new TypeFilter(AccountStatementType.getValueByCode(request.getFilters().getMovementType())).apply(list);
        list = mapper.convertResponse(list);

        String format = request.getFormat();
        String base64 = Objects.equals(format, "PDF")
                ? Util.encodeByteArrayToBase64(pdfProvider.generatePdf(list, request, accountId))
                : Util.encodeByteArrayToBase64(csvProvider.generateCsv(list));

        return new AccountStatementExportResponse(base64);
    }
}
