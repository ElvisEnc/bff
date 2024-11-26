package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.credit.card.*;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.*;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.interfaces.ICreditCardProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardConstans;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardMiddlewareService;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CreditCardProvider extends MiddlewareProvider<CreditCardMiddlewareError> implements ICreditCardProvider {

    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public CreditCardProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.CREDIT_CARD_MANAGER, CreditCardMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientCreditCardManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.CREDIT_CARD_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ListCreditCardMWResponse getListCreditCard(String personId) throws IOException {
        String url = baseUrl + String.format(CreditCardMiddlewareService.GET_LIST_CARD.getServiceURL(), personId);
        ByMwErrorResponseHandler<ApiDataResponse> responseHandler = ByMwErrorResponseHandler.instance(CreditCardMiddlewareError.MDWCCM_0001);
        ApiDataResponse<ListCreditCardMWResponse> mwResponse = get(url, HeadersMW.getDefaultHeaders(httpServletRequest), ApiDataResponse.class, responseHandler);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), ListCreditCardMWResponse.class);
    }

    @Override
    public DetailsCreditCardMWResponse getDetailCreditCard(String personId, String cardId) throws IOException {
        String url = baseUrl + String.format(CreditCardMiddlewareService.GET_DETAIL_CREDIT_CARD.getServiceURL(), personId, cardId);
        ApiDataResponse<DetailsCreditCardMWResponse> mwResponse = get(url, HeadersMW.getDefaultHeaders(httpServletRequest), ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), DetailsCreditCardMWResponse.class);
    }

    @Override
    public DetailPrepaidCardMWResponse getDetailPrepaidCard(String personId, String cardId) throws IOException {
        String url = baseUrl + String.format(CreditCardMiddlewareService.GET_DETAIL_PREPAID_CARD.getServiceURL(), personId, cardId);
        ApiDataResponse<DetailPrepaidCardMWResponse> mwResponse = get(url, HeadersMW.getDefaultHeaders(httpServletRequest), ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), DetailPrepaidCardMWResponse.class);
    }

    @Override
    public GenericResponse blockCreditCard(BlockCreditCardMWRequest mwRequest) throws IOException {
        String url = baseUrl + CreditCardMiddlewareService.BLOCK_CREDIT_CARD.getServiceURL();
        ApiDataResponse<BlockCreditCardMWResponse> apiDataResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        BlockCreditCardMWResponse mwResponse = Util.stringToObject(Util.objectToString(apiDataResponse.getData()), BlockCreditCardMWResponse.class);
        if (mwResponse.getCode().equals(CreditCardConstans.SUCCESS_BLOCK.getValue()))
            return GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);
        else
            return GenericResponse.instance(CreditCardMiddlewareResponse.ERROR_UPDATE_STATUS_LOCK);
    }

    @Override
    public AvailableCreditCardMWResponse getAvailable(String cmsCard) throws IOException {
        String url = baseUrl + String.format(CreditCardMiddlewareService.GET_AVAILABLE_CREDIT_CARD.getServiceURL(), cmsCard);
        ApiDataResponse<DetailsCreditCardMWResponse> mwResponse = get(url, HeadersMW.getDefaultHeaders(httpServletRequest), ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), AvailableCreditCardMWResponse.class);
    }

    @Override
    public PeriodCreditCardMWResponse getPaymentPeriods() throws IOException {
        String url = baseUrl + CreditCardMiddlewareService.GET_PERIOD_CREDIT_CARD.getServiceURL();
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), PeriodCreditCardMWResponse.class);
    }

    @Override
    public CashAdvanceFeeMWResponse getCashAdvanceFee(CashAdvanceFeeMWRequest request) throws IOException {
        String url = baseUrl + CreditCardMiddlewareService.CASH_ADVANCE_FEE.getServiceURL();
        ApiDataResponse<CashAdvanceFeeMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), CashAdvanceFeeMWResponse.class);
    }

    @Override
    public LinkserCreditCardMWResponse getCreditCardsFromLinkser(String personId, String cmsAccount) throws IOException {
        String url = baseUrl + String.format(CreditCardMiddlewareService.GET_LIST_CREDIT_CARD.getServiceURL(), personId, cmsAccount);
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), LinkserCreditCardMWResponse.class);
    }

    @Override
    public CashAdvanceMWResponse cashAdvance(CashAdvanceMWRequest mwRequest) throws IOException {
        String url = baseUrl + CreditCardMiddlewareService.CASH_ADVANCE_CREDIT_CARD.getServiceURL();
        ApiDataResponse<CashAdvanceMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), CashAdvanceMWResponse.class);
    }

    @Override
    public CreditCardStatementsMWResponse getStatements(String cmsCard, String init, String end) throws IOException {
        String url = baseUrl + String.format(CreditCardMiddlewareService.GET_MOVEMENTS.getServiceURL(), cmsCard, init, end);
        ByMwErrorResponseHandler<CreditCardStatementsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(CreditCardMiddlewareError.MDWCCM_0001);
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), CreditCardStatementsMWResponse.class, responseHandler);
    }

    @Override
    public PurchaseAuthMWResponse getListPurchaseAuth(String personId, String cmsCard) throws IOException {
        String url = baseUrl + String.format(CreditCardMiddlewareService.GET_AUTHS.getServiceURL(), cmsCard, personId);
        ByMwErrorResponseHandler<PurchaseAuthMWResponse> responseHandler = ByMwErrorResponseHandler.instance(CreditCardMiddlewareError.MDWCCM_0001);
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), PurchaseAuthMWResponse.class, responseHandler);
    }

    @Override
    public GenericResponse authorizationCreditCard(AuthorizationCreditCardMWRequest mwRequest) throws IOException {
        String url = baseUrl + CreditCardMiddlewareService.POST_AUTHORIZATION_CREDIT_CARD.getServiceURL();
        ApiDataResponse<AuthorizationCreditCardMWResponse> apiDataResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        AuthorizationCreditCardMWResponse mwResponse = Util.stringToObject(Util.objectToString(apiDataResponse.getData()), AuthorizationCreditCardMWResponse.class);
        if (mwResponse.getCodError().equals(CreditCardConstans.SUCCESS_AUTHORIZATION.getValue()))
            return GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_AUTHORIZATION_ONLINE);
        else
            return GenericResponse.instance(CreditCardMiddlewareResponse.ERROR_AUTHORIZATION_ONLINE);
    }

    @Override
    public FeePrepaidCardMWResponse getFeePrepaidCard(FeePrepaidCardMWRequest mwRequest) throws IOException {
        String url = baseUrl + CreditCardMiddlewareService.GET_FEE_PREPAID_CARD.getServiceURL();
        ApiDataResponse<FeePrepaidCardMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), FeePrepaidCardMWResponse.class);
    }
}
