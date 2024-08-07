package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AmountRange;
import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.AccountStatementType;
import bg.com.bo.bff.commons.filters.OrderFilter;
import bg.com.bo.bff.commons.filters.RangeFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.commons.filters.TypeFilter;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.providers.account.IOwnAccountsMapper;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.interfaces.IOwnAccountsProvider;
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
    private final IOwnAccountsProvider provider;
    private final IOwnAccountsMapper mapper;

    @Autowired
    private AccountStatementService self;

    public AccountStatementService(IOwnAccountsProvider provider, IOwnAccountsMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public List<AccountStatementsResponse> getAccountStatement(String accountId, AccountStatementsRequest request, Map<String, String> parameter) throws IOException {
        String key = accountId + "|" + request.getFilters().getDate().getStart() + "|" + request.getFilters().getDate().getEnd();
        Boolean refreshData = request.getRefreshData();

        AccountStatementsMWRequest mwRequest = mapper.mapperRequest(accountId, init, total, request);
        List<AccountStatementsResponse> list = self.getAccountStatementsCache(mwRequest, parameter, key, refreshData);

        String field = (request.getFilters().getOrder() != null) ? request.getFilters().getOrder().getField() : "DATE";
        boolean desc = (request.getFilters().getOrder() == null) || request.getFilters().getOrder().getDesc();
        Map<String, Function<AccountStatementsResponse, ? extends Comparable<?>>> comparatorOptions = new HashMap<>();
        comparatorOptions.put("AMOUNT", AccountStatementsResponse::getAmount);
        comparatorOptions.put("DATE", response -> LocalDate.parse(response.getMovementDate(), Util.getDateFormatter()));
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
}
