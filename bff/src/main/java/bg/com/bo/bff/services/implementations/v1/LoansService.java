package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPaymentsResponse;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.filters.LoanPaymentsDateFilter;
import bg.com.bo.bff.commons.filters.LoanPaymentsOrderFilter;
import bg.com.bo.bff.commons.filters.LoansFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.mappings.providers.loans.ILoansMapper;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoanPaymentsMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;
import bg.com.bo.bff.providers.interfaces.ILoansProvider;
import bg.com.bo.bff.services.interfaces.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoansService implements ILoansService {
    private final ILoansProvider provider;
    private final ILoansMapper mapper;
    @Autowired
    private LoansService self;

    public LoansService(ILoansProvider idcProvider, ILoansMapper idcMapper) {
        this.provider = idcProvider;
        this.mapper = idcMapper;
    }

    @Override
    public List<ListLoansResponse> getListLoansByPerson(String personId, ListLoansRequest request, Map<String, String> parameters) throws IOException {
        Boolean refreshData = request.getRefreshData();
        List<ListLoansResponse> list = self.getServiceCache(personId, parameters, refreshData);

        if (request.getFilters().getOrder() != null) {
            list = new LoansFilter(request.getFilters()).apply(list);
        }

        if (request.getFilters().getPagination() != null) {
            int page = request.getFilters().getPagination().getPage();
            int pageSize = request.getFilters().getPagination().getPageSize();
            list = new PageFilter<ListLoansResponse>(page, pageSize).apply(list);
        }

        return list;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.USER_DATA, key = "'loans:' + #personId", condition = "#refreshData == false")},
            put = {@CachePut(value = CacheConstants.USER_DATA, key = "'loans:' + #personId", condition = "#refreshData == true")})
    protected List<ListLoansResponse> getServiceCache(String personId, Map<String, String> parameters, Boolean refreshData) throws IOException {
        ListLoansMWResponse mwResponse = provider.getListLoansByPerson(personId, parameters);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public List<LoanPaymentsResponse> getLoanPayments(String loanId, String personId, LoanPaymentsRequest request, Map<String, String> parameter) throws IOException {
        Boolean refreshData = request.getRefreshData();
        List<LoanPaymentsResponse> list = new ArrayList<>(self.getLoanPaymentsCache(loanId, personId, request.getLoanNumber(), parameter, refreshData));

        if (request.getFilters().getPaymentDate() != null) {
            list = new LoanPaymentsDateFilter(request).apply(list);
        }

        list = new LoanPaymentsOrderFilter(request).apply(list);

        if (request.getFilters().getPagination() != null) {
            int page = request.getFilters().getPagination().getPage();
            int pageSize = request.getFilters().getPagination().getPageSize();
            list = new PageFilter<LoanPaymentsResponse>(page, pageSize).apply(list);
        }
        return list;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.USER_DATA, key = "'loan-payments:' + #personId + ':loans:' + #loanId", condition = "#refreshData == false")},
            put = {@CachePut(value = CacheConstants.USER_DATA, key = "'loan-payments:' + #personId + ':loans:' + #loanId", condition = "#refreshData == true")})
    protected List<LoanPaymentsResponse> getLoanPaymentsCache(String loanId, String personId, String loamNumber, Map<String, String> parameter, Boolean refreshData) throws IOException {
        ListLoanPaymentsMWResponse mwResponse = provider.getListLoanPayments(loanId, loamNumber, parameter);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }
}
