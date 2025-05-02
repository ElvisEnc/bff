package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.request.loans.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.filters.DateFilter;
import bg.com.bo.bff.commons.filters.OrderFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.mappings.providers.loans.ILoansMapper;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.request.loans.mw.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;
import bg.com.bo.bff.providers.interfaces.ILoansProvider;
import bg.com.bo.bff.providers.interfaces.ILoansTransactionProvider;
import bg.com.bo.bff.providers.models.enums.middleware.loans.LoansMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.loans.LoansTransactionMiddlewareError;
import bg.com.bo.bff.services.interfaces.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoansService implements ILoansService {
    private final ILoansProvider provider;
    private final ILoansTransactionProvider transactionProvider;
    private final ILoansMapper mapper;

    @Autowired
    private LoansService self;

    public LoansService(ILoansProvider idcProvider, ILoansTransactionProvider transactionProvider, ILoansMapper idcMapper) {
        this.provider = idcProvider;
        this.transactionProvider = transactionProvider;
        this.mapper = idcMapper;
    }

    @Override
    public List<ListLoansResponse> getListLoansByPerson(String personId, String clientId, ListLoansRequest request) throws IOException {
        Boolean refreshData = request.getRefreshData();
        List<ListLoansResponse> list = self.getServiceCache(personId, clientId, refreshData);

        String field = (request.getFilters().getOrder() != null) ? request.getFilters().getOrder().getField() : "EXPIRATION_DATE";
        boolean desc = (request.getFilters().getOrder() != null) && request.getFilters().getOrder().getDesc();
        Map<String, Function<ListLoansResponse, ? extends Comparable<?>>> comparatorOptions = new HashMap<>();
        comparatorOptions.put("LOAN_NUMBER", ListLoansResponse::getLoanNumber);
        comparatorOptions.put("EXPIRATION_DATE", response -> LocalDate.parse(UtilDate.getDateGenericFormat(response.getExpirationDate()), UtilDate.getDateFormatter()));
        list = new OrderFilter<>(field, desc, comparatorOptions).apply(list);

        if (request.getFilters().getPagination() != null) {
            int page = request.getFilters().getPagination().getPage();
            int pageSize = request.getFilters().getPagination().getPageSize();
            list = new PageFilter<ListLoansResponse>(page, pageSize).apply(list);
        }

        return list;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.USER_DATA, key = "'loans:' + #personId", condition = "#refreshData == false")},
            put = {@CachePut(value = CacheConstants.USER_DATA, key = "'loans:' + #personId", condition = "#refreshData == true")})
    protected List<ListLoansResponse> getServiceCache(String personId, String clientId, Boolean refreshData) throws IOException {
        ListLoansMWResponse mwResponse = provider.getListLoansByPerson(personId, clientId);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public List<LoanPaymentsResponse> getLoanPayments(String loanId, String clientId, LoanPaymentsRequest request) throws IOException {
        Boolean refreshData = request.getRefreshData();
        List<LoanPaymentsResponse> list = new ArrayList<>(self.getLoanPaymentsCache(loanId, clientId, request.getLoanNumber(), refreshData));

        if (request.getFilters().getDate() != null) {
            String start = request.getFilters().getDate().getStart();
            String end = request.getFilters().getDate().getEnd();
            list = new DateFilter<>(start, end, LoanPaymentsResponse::getDate).apply(list);
        }

        String field = (request.getFilters().getOrder() != null) ? request.getFilters().getOrder().getField() : "DATE";
        boolean desc = (request.getFilters().getOrder() == null) || request.getFilters().getOrder().getDesc();
        Map<String, Function<LoanPaymentsResponse, ? extends Comparable<?>>> comparatorOptions = new HashMap<>();
        comparatorOptions.put("INTEREST_PAID", LoanPaymentsResponse::getInterestAmountPaid);
        comparatorOptions.put("CAPITAL_PAID", LoanPaymentsResponse::getCapitalPaid);
        comparatorOptions.put("DATE", response -> LocalDate.parse(UtilDate.getDateGenericFormat(response.getDate()), UtilDate.getDateFormatter()));
        list = new OrderFilter<>(field, desc, comparatorOptions).apply(list);

        if (request.getFilters().getPagination() != null) {
            int page = request.getFilters().getPagination().getPage();
            int pageSize = request.getFilters().getPagination().getPageSize();
            list = new PageFilter<LoanPaymentsResponse>(page, pageSize).apply(list);
        }
        return list;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.USER_DATA, key = "'loan-payments:' + #personId + ':loans:' + #loanId", condition = "#refreshData == false")},
            put = {@CachePut(value = CacheConstants.USER_DATA, key = "'loan-payments:' + #personId + ':loans:' + #loanId", condition = "#refreshData == true")})
    protected List<LoanPaymentsResponse> getLoanPaymentsCache(String loanId, String clientId, String loamNumber, Boolean refreshData) throws IOException {
        LoanPaymentsMWResponse mwResponse = provider.getListLoanPayments(loanId, loamNumber, clientId);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public List<LoanInsurancePaymentsResponse> getLoanInsurancePayments(String loanId, String clientId, LoanPaymentsRequest request) throws IOException {
        Boolean refreshData = request.getRefreshData();
        List<LoanInsurancePaymentsResponse> list = new ArrayList<>(self.getLoanInsurancePaymentsCache(loanId, clientId, request.getLoanNumber(), refreshData));

        if (request.getFilters().getDate() != null) {
            String start = request.getFilters().getDate().getStart();
            String end = request.getFilters().getDate().getEnd();
            list = new DateFilter<>(start, end, LoanInsurancePaymentsResponse::getPaymentDate).apply(list);
        }

        String field = (request.getFilters().getOrder() != null) ? request.getFilters().getOrder().getField() : "DATE";
        boolean desc = (request.getFilters().getOrder() == null) || request.getFilters().getOrder().getDesc();
        Map<String, Function<LoanInsurancePaymentsResponse, ? extends Comparable<?>>> comparatorOptions = new HashMap<>();
        comparatorOptions.put("AMOUNT_PAID", LoanInsurancePaymentsResponse::getAmount);
        comparatorOptions.put("DATE", response -> LocalDate.parse(response.getPaymentDate(), UtilDate.getDateFormatter()));
        list = new OrderFilter<>(field, desc, comparatorOptions).apply(list);

        if (request.getFilters().getPagination() != null) {
            int page = request.getFilters().getPagination().getPage();
            int pageSize = request.getFilters().getPagination().getPageSize();
            list = new PageFilter<LoanInsurancePaymentsResponse>(page, pageSize).apply(list);
        }
        return list;
    }

    @Override
    public LoanDetailPaymentResponse getLoanDetailPayment(String loanId, String personId, String clientId, String currencyCode) throws IOException {
        List<ListLoansResponse> list = self.getServiceCache(personId, clientId,false);
        boolean existData = list.stream().anyMatch(response -> response.getLoanId().equals(loanId) && response.getClientId().equals(clientId));
        if (!existData) {
            throw new GenericException(LoansMiddlewareError.MDWPRE_NOT_FOUND);
        }
        LoanDetailPaymentMWResponse mwResponse = provider.getLoanDetailPayment(loanId, clientId);
        return mapper.convertResponse(mwResponse, currencyCode);
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.USER_DATA, key = "'loan-insurance-payments:' + #personId + ':loans:' + #loanId", condition = "#refreshData == false")},
            put = {@CachePut(value = CacheConstants.USER_DATA, key = "'loan-insurance-payments:' + #personId + ':loans:' + #loanId", condition = "#refreshData == true")})
    protected List<LoanInsurancePaymentsResponse> getLoanInsurancePaymentsCache(String loanId, String clientId, String loamNumber, Boolean refreshData) throws IOException {
        LoanInsurancePaymentsMWResponse mwResponse = provider.getListLoanInsurancePayments(loanId, loamNumber, clientId);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public List<LoanPlanResponse> getLoanPlans(String loanId, String clientId) throws IOException {
        LoanPlanMWResponse mwResponse = provider.getLoanPlansPayments(loanId, clientId);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public LoanPaymentResponse payLoanInstallment(String personId, String accountId, LoanPaymentRequest request) throws IOException {
        LoanPaymentMWRequest mwRequest = mapper.mapperRequest(personId, accountId, request);
        LoanPaymentMWResponse mwResponse = transactionProvider.payLoanInstallment(mwRequest);
        if (Objects.equals(mwResponse.getStatus(), "PENDING")) {
            throw new GenericException(LoansTransactionMiddlewareError.MDWLTM_PENDING);
        }
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public Pcc01Response makeControl(String personId, String accountId, Pcc01Request request) throws IOException {
        Pcc01MWRequest mwRequest = mapper.mapperRequest(personId, accountId, request);
        Pcc01MWResponse mwResponse = provider.validateControl(mwRequest);
        return Pcc01Response.builder().requiresPcc01(mwResponse.getRequireUif()).build();
    }
}
