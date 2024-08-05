package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AmountRange;
import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.AccountStatementType;
import bg.com.bo.bff.commons.filters.AmountRangeFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.commons.filters.TypeFilter;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.providers.account.IOwnAccountsMapper;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.interfaces.IOwnAccountsProvider;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountStatementService implements IAccountStatementService {
    @Value("${account.statement.init}")
    private String init;
    @Value("${account.statement.total}")
    private String total;
    private final IOwnAccountsProvider provider;
    private final IOwnAccountsMapper mapper;


    public AccountStatementService(IOwnAccountsProvider provider, IOwnAccountsMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public List<AccountStatementsResponse> getAccountStatement(String accountId, AccountStatementsRequest request, Map<String, String> parameter) throws IOException {
        String startDate = request.getFilters().getPagination().getStartDate();
        String endDate = request.getFilters().getPagination().getEndDate();
        String key = accountId + "|" + startDate + "|" + endDate;

        String extractType = request.getFilters().getType();
        BigDecimal min = Optional.ofNullable(request.getFilters())
                .map(AccountStatementsRequest.AccountStatementsFilter::getAmount)
                .map(AmountRange::getMin)
                .orElse(null);
        BigDecimal max = Optional.ofNullable(request.getFilters())
                .map(AccountStatementsRequest.AccountStatementsFilter::getAmount)
                .map(AmountRange::getMax)
                .orElse(null);

        Boolean isPageOne = request.getFilters().getPagination().getPage() == 1;

        AccountStatementsMWResponse basicResponse = getAccountStatementsCache(request, accountId, init, total, parameter, key, isPageOne);

        List<AccountStatementsMWResponse.AccountStatementMW> data = basicResponse.getData();

        if (Boolean.FALSE.equals(request.getFilters().getIsAsc())) {
            Collections.reverse(data);
        }

        data = new TypeFilter(AccountStatementType.getValueByCode(extractType)).apply(data);

        data = new AmountRangeFilter(min, max).apply(data);

        data = new PageFilter(request.getFilters().getPagination().getPage(), request.getFilters().getPagination().getPageSize()).apply(data);

        return data.stream().map(AccountStatementService::toProviderResponse).toList();
    }

    public static AccountStatementsResponse toProviderResponse(AccountStatementsMWResponse.AccountStatementMW accountStatementMW) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("ACEP", 1);
        hashMap.put("ENPROC", 2);
        hashMap.put("RECH", 3);

        return AccountStatementsResponse.builder()
                .status(String.valueOf(hashMap.get(accountStatementMW.getStatus())))
                .type(Objects.equals(accountStatementMW.getMoveType(), "D") ? AccountStatementType.DEBITO.getCode() : AccountStatementType.CREDITO.getCode())
                .amount(accountStatementMW.getAmount())
                .currency(accountStatementMW.getCurrencyCod())
                .channel(accountStatementMW.getBranchOffice())
                .dateMov(Util.formatDate(accountStatementMW.getProcessDate()))
                .timeMov(accountStatementMW.getAccountingTime())
                .movBalance(accountStatementMW.getCurrentBalance())
                .seatNumber(String.valueOf(accountStatementMW.getSeatNumber()))
                .description(accountStatementMW.getDescription())
                .build();
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.ACCOUNTS_STATEMENTS, key = "#extractId", condition = "#clearCache == false")},
            put = {@CachePut(value = CacheConstants.ACCOUNTS_STATEMENTS, key = "#extractId", condition = "#clearCache == true")})
    protected AccountStatementsMWResponse getAccountStatementsCache(AccountStatementsRequest request, String accountId, String init, String total, Map<String, String> parameter, String extractId, Boolean clearCache) throws IOException {
        AccountStatementsMWRequest mwRequest = mapper.mapperRequest(accountId, init, total, request);
        return provider.getAccountStatements(mwRequest, parameter);
    }
}
