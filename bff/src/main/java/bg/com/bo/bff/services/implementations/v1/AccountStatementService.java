package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AmountRange;
import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.TransferMovementsRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.TransferMovementsResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.account.statement.ValidParametersFilter;
import bg.com.bo.bff.commons.filters.*;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.mappings.providers.account.IOwnAccountsMapper;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.ReportTransfersMWRequest;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.providers.models.enums.middleware.account.statement.AccountStatementMiddlewareError;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountStatementService implements IAccountStatementService {
    @Value("${account.statement.init}")
    private String init;
    @Value("${account.statement.total}")
    private String total;
    private final IAccountStatementProvider provider;
    private final IOwnAccountsMapper mapper;

    @Autowired
    private AccountStatementService self;

    public AccountStatementService(IAccountStatementProvider provider, IOwnAccountsMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public List<AccountStatementsResponse> getAccountStatement(String accountId, String personId, AccountStatementsRequest request, Map<String, String> parameter) throws IOException {
        String key = accountId + "|" + request.getFilters().getDate().getStart() + "|" + request.getFilters().getDate().getEnd();
        Boolean refreshData = request.getRefreshData();

        AccountStatementsMWRequest mwRequest = mapper.mapperRequest(accountId, personId, init, total, request);
        List<AccountStatementsResponse> list = self.getAccountStatementsCache(mwRequest, parameter, key, refreshData);

        Map<String, Function<AccountStatementsResponse, ? extends Comparable<?>>> comparatorOptions = new HashMap<>();
        comparatorOptions.put("AMOUNT", AccountStatementsResponse::getAmount);
        comparatorOptions.put("DATE", response -> LocalDate.parse(response.getMovementDate(), UtilDate.getDateFormatter()));
        comparatorOptions.put("TIME", AccountStatementsResponse::getMovementTime);
        if (request.getFilters().getOrder() == null || "DATE".equals(request.getFilters().getOrder().getField())) {
            boolean desc = request.getFilters().getOrder() == null || request.getFilters().getOrder().getDesc();
            list = list.stream()
                    .sorted(Comparator.comparing(
                                    (AccountStatementsResponse response) -> LocalDate.parse(UtilDate.getDateGenericFormat(response.getMovementDate()), UtilDate.getDateFormatter()),
                                    desc ? Comparator.reverseOrder() : Comparator.naturalOrder())
                            .thenComparing(AccountStatementsResponse::getMovementTime, Comparator.reverseOrder())
                    ).toList();
        } else {
            String field = request.getFilters().getOrder().getField();
            boolean desc = request.getFilters().getOrder().getDesc();
            list = new OrderFilter<>(field, desc, comparatorOptions).apply(list);
        }

        if (request.getFilters().getAmount() != null) {
            BigDecimal min = Optional.of(request.getFilters().getAmount())
                    .map(AmountRange::getMin)
                    .orElse(null);
            BigDecimal max = Optional.of(request.getFilters().getAmount())
                    .map(AmountRange::getMax)
                    .orElse(null);
            list = new RangeFilter<>(min, max, AccountStatementsResponse::getAmount).apply(list);
        }

        list = new TypeFilter(request.getFilters().getMovementType()).apply(list);

        int page = request.getFilters().getPagination().getPage();
        int pageSize = request.getFilters().getPagination().getPageSize();
        list = new PageFilter<AccountStatementsResponse>(page, pageSize).apply(list);

        return list;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.ACCOUNTS_STATEMENTS, key = "#extractId", condition = "#clearCache == false")},
            put = {@CachePut(value = CacheConstants.ACCOUNTS_STATEMENTS, key = "#extractId", condition = "#clearCache == true")})
    protected List<AccountStatementsResponse> getAccountStatementsCache(AccountStatementsMWRequest request, Map<String, String> parameter, String extractId, Boolean clearCache) throws IOException {
        return new ArrayList<>(mapper.convertResponse(provider.getAccountStatements(request, parameter)));
    }

    @Override
    public List<TransferMovementsResponse> getTransferMovements(String accountId, String personId, TransferMovementsRequest request, Map<String, String> parameter) throws IOException {
        boolean hasSearchCriteria = request.getFilters().getSearchCriteria() != null && request.getFilters().getSearchCriteria().getParameters() != null;
        if (hasSearchCriteria) {
            if (request.getFilters().getSearchCriteria().getParameters().isEmpty() || !ValidParametersFilter.isValidParams(request.getFilters().getSearchCriteria().getParameters())) {
                throw new GenericException(AccountStatementMiddlewareError.INVALID_SEARCH_PARAMS);
            }
        } else {
            request.getFilters().setSearchCriteria(null);
        }

        String key = "transfer-movements:" + accountId + "|" + request.getFilters().getPeriod().getStart() + "|" + request.getFilters().getPeriod().getEnd();
        Boolean refreshData = request.getRefreshData();

        ReportTransfersMWRequest mwRequest = mapper.mapperRequest(accountId, personId, request);
        List<TransferMovementsResponse> list = self.getTransferMovementsCache(mwRequest, parameter, key, refreshData);

        Map<String, Function<TransferMovementsResponse, ? extends Comparable<?>>> comparatorOptions = new HashMap<>();
        comparatorOptions.put("DATE", response -> LocalDate.parse(response.getMovementDate(), UtilDate.getDateFormatter()));
        comparatorOptions.put("TIME", TransferMovementsResponse::getMovementTime);
        if (request.getFilters().getOrder() == null || "DATE".equals(request.getFilters().getOrder().getField())) {
            boolean desc = request.getFilters().getOrder() == null || request.getFilters().getOrder().getDesc();
            list = list.stream()
                    .sorted(Comparator.comparing(
                                    (TransferMovementsResponse response) -> LocalDate.parse(UtilDate.getDateGenericFormat(response.getMovementDate()), UtilDate.getDateFormatter()),
                                    desc ? Comparator.reverseOrder() : Comparator.naturalOrder())
                            .thenComparing(TransferMovementsResponse::getMovementTime, Comparator.reverseOrder())
                    ).toList();
        } else {
            String field = request.getFilters().getOrder().getField();
            boolean desc = request.getFilters().getOrder().getDesc();
            list = new OrderFilter<>(field, desc, comparatorOptions).apply(list);
        }

        if (request.getFilters().getSearchCriteria() != null && !request.getFilters().getSearchCriteria().getValue().trim().isEmpty()) {
            list = new SearchCriteriaFilter<TransferMovementsResponse>(request.getFilters().getSearchCriteria().getParameters(), request.getFilters().getSearchCriteria().getValue()).apply(list);
        }

        int page = request.getFilters().getPagination().getPage();
        int pageSize = request.getFilters().getPagination().getPageSize();
        list = new PageFilter<TransferMovementsResponse>(page, pageSize).apply(list);

        return list;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.ACCOUNTS_STATEMENTS, key = "#extractId", condition = "#clearCache == false")},
            put = {@CachePut(value = CacheConstants.ACCOUNTS_STATEMENTS, key = "#extractId", condition = "#clearCache == true")})
    protected List<TransferMovementsResponse> getTransferMovementsCache(ReportTransfersMWRequest request, Map<String, String> parameter, String extractId, Boolean clearCache) throws IOException {
        return new ArrayList<>(mapper.convertResponse(provider.getTransferMovements(request, parameter)));
    }
}
