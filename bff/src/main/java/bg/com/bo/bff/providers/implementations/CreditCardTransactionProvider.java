package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.credit.card.PayCreditCardMWRequest;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.PayCreditCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.interfaces.ICreditCardTransactionProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardMiddlewareService;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardTransactionMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CreditCardTransactionProvider extends MiddlewareProvider<CreditCardTransactionMiddlewareError> implements ICreditCardTransactionProvider {
    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public CreditCardTransactionProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.CREDIT_CARD_TRANSACTION_MANAGER, CreditCardTransactionMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientCreditCardTransactionManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.CREDIT_CARD_TRANSACTION_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public PayCreditCardMWResponse payCreditCard(PayCreditCardMWRequest request) throws IOException {
        String url = baseUrl + CreditCardMiddlewareService.PAY_CREDIT_CARD.getServiceURL();
        ApiDataResponse<PayCreditCardMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), PayCreditCardMWResponse.class);
    }
}
