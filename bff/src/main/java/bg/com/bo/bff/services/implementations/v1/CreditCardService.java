package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.credit.card.*;
import bg.com.bo.bff.application.dtos.response.credit.card.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.filters.OrderFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.mappings.providers.card.ICreditCardMapper;
import bg.com.bo.bff.providers.dtos.request.credit.card.*;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.CreditCardStatementsMWResponse;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.PayCreditCardMWResponse;
import bg.com.bo.bff.providers.interfaces.ICreditCardProvider;
import bg.com.bo.bff.providers.interfaces.ICreditCardTransactionProvider;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardConstans;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardTransactionMiddlewareError;
import bg.com.bo.bff.services.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CreditCardService implements ICreditCardService {
    private final ICreditCardProvider provider;
    private final ICreditCardTransactionProvider transactionProvider;
    private final ICreditCardMapper mapper;
    @Autowired
    private CreditCardService self;

    public CreditCardService(ICreditCardProvider provider, ICreditCardTransactionProvider transactionProvider, ICreditCardMapper mapper) {
        this.provider = provider;
        this.transactionProvider = transactionProvider;
        this.mapper = mapper;
    }

    @Override
    public ListCreditCardResponse getListCard(String personId) throws IOException {
        return mapper.convertResponse(provider.getListCreditCard(personId));
    }

    @Override
    public DetailCreditCardResponse getDetailsCreditCard(String personId, String cardId) throws IOException {
        return mapper.convertDetails(provider.getDetailCreditCard(personId, cardId));
    }

    @Override
    public DetailPrepaidCardResponse getDetailsPrepaidCard(String personId, String cardId) throws IOException {
        return mapper.convertDetails(provider.getDetailPrepaidCard(personId, cardId));
    }

    @Override
    public GenericResponse blockCreditCard(String personId, String cardId, BlockCreditCardRequest request) throws IOException {
        BlockCreditCardMWRequest mwRequest = mapper.mapperRequest(request, personId);
        return provider.blockCreditCard(mwRequest);
    }

    @Override
    public AvailableCreditCardResponse getAvailable(String personId, String cardId, String cmsCard) throws IOException {
        return mapper.convertAvailable(provider.getAvailable(cmsCard));
    }

    @Override
    public List<PeriodCreditCardResponse> getPeriods(String personId, String cardId) throws IOException {
        return mapper.convertPeriods(provider.getPaymentPeriods());
    }

    @Override
    public CashAdvanceFeeResponse getCashAdvanceFee(String personId, String cmsAccountNumber, BigDecimal amount) throws IOException {
        CashAdvanceFeeMWRequest mwRequest = mapper.mapperRequest(personId, cmsAccountNumber, amount);
        return mapper.convertResponse(provider.getCashAdvanceFee(mwRequest));
    }

    @Override
    public List<LinkserCreditCardResponse> getCreditCards(String personId, String cmsAccount) throws IOException {
        return mapper.convertListCreditCard(provider.getCreditCardsFromLinkser(personId, cmsAccount));
    }

    @Override
    public CashAdvanceResponse makeCashAdvance(String personId, String cardId, CashAdvanceRequest request) throws IOException {
        CashAdvanceMWRequest mwRequest = mapper.mapperRequestAdvance(personId, request);
        return mapper.convertResponse(provider.cashAdvance(mwRequest));
    }

    @Override
    public List<CreditCardStatementsResponse> creditCardStatements(String personId, CreditCardStatementRequest request) throws IOException {
        Boolean refreshData = request.getRefreshData();
        List<CreditCardStatementsResponse> list = self.getStatementsCache(personId, request, refreshData);

        String field = (request.getFilters().getOrder() != null) ? request.getFilters().getOrder().getField() : "DATE";
        boolean desc = (request.getFilters().getOrder() == null) || request.getFilters().getOrder().getDesc();
        Map<String, Function<CreditCardStatementsResponse, ? extends Comparable<?>>> comparatorOptions = new HashMap<>();
        comparatorOptions.put("AMOUNT", response -> {
            BigDecimal foreignAmount = response.getMrAmount();
            BigDecimal localAmount = response.getMlAmount();
            return (localAmount != null && localAmount.compareTo(BigDecimal.ZERO) > 0) ? localAmount : foreignAmount;
        });
        comparatorOptions.put("DATE", response -> LocalDate.parse(response.getProcessDate(), UtilDate.getDateFormatter()));
        list = new OrderFilter<>(field, desc, comparatorOptions).apply(list);

        if (request.getFilters().getPagination() != null) {
            int page = request.getFilters().getPagination().getPage();
            int pageSize = request.getFilters().getPagination().getPageSize();
            list = new PageFilter<CreditCardStatementsResponse>(page, pageSize).apply(list);
        }
        return list;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.USER_DATA, key = "'credit-card-statements:' + #personId + ':cmsCard:' + #request.cmsCard", condition = "#refreshData == false")},
            put = {@CachePut(value = CacheConstants.USER_DATA, key = "'credit-card-statements:' + #personId + ':cmsCard:' + #request.cmsCard", condition = "#refreshData == true")})
    protected List<CreditCardStatementsResponse> getStatementsCache(String personId, CreditCardStatementRequest request, Boolean refreshData) throws IOException {
        String card = request.getCmsCard();
        String init = UtilDate.adaptDateToMWFormat(request.getFilters().getDate().getStart());
        String end = UtilDate.adaptDateToMWFormat(request.getFilters().getDate().getEnd());
        CreditCardStatementsMWResponse mwResponse = provider.getStatements(card, init, end);
        return new ArrayList<>(mapper.convertStatements(mwResponse));
    }

    @Override
    public List<PurchaseAuthResponse> getPurchasesAuthorizations(String personId, String cmsCard) throws IOException {
        return mapper.convertPurchase(provider.getListPurchaseAuth(personId, cmsCard));
    }

    @Override
    public PayCreditCardResponse payCreditCard(String personId, String accountId, PayCreditCardRequest request) throws IOException {
        PayCreditCardMWRequest mwRequest = mapper.mapperRequest(personId, accountId, request);
        PayCreditCardMWResponse mwResponse = transactionProvider.payCreditCard(mwRequest);
        if (mwResponse.getStatus().equals(CreditCardConstans.APPROVED.getValue()))
            return mapper.convertPayCC(mwResponse);
        else {
            CreditCardTransactionMiddlewareError error = CreditCardTransactionMiddlewareError.PENDING;
            throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
        }
    }

    @Override
    public GenericResponse authorizationCreditCard(String personId, AuthorizationCreditCardRequest request) throws IOException {
        AuthorizationCreditCardMWRequest mwRequest = mapper.mapperRequest(personId, request);
        return provider.authorizationCreditCard(mwRequest);
    }

    @Override
    public FeePrepaidCardResponse getFeePrepaidCard(String personId, String cardId, FeePrepaidCardRequest request) throws IOException {
        FeePrepaidCardMWRequest mwRequest = mapper.mapperRequest(cardId, request);
        return  mapper.convertResponse(provider.getFeePrepaidCard(mwRequest));
    }
}
