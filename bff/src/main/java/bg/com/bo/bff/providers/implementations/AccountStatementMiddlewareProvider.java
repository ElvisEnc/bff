package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.account.statement.AccountStatementMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.account.statement.AccountStatementMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AccountStatementMiddlewareProvider extends MiddlewareProvider<AccountStatementMiddlewareError> implements IAccountStatementProvider {
    private final String baseUrl;

    public AccountStatementMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.ACCOUNT_STATEMENT_MANAGER, AccountStatementMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientAccountStatementManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.ACCOUNT_STATEMENT_MANAGER.getName();
    }

    @Override
    public AccountStatementsMWResponse getAccountStatements(AccountStatementsMWRequest request, Map<String, String> parameter) throws IOException {
        String url = baseUrl + AccountStatementMiddlewareServices.POST_ACCOUNT_STATEMENT.getServiceURL();
        ByMwErrorResponseHandler<AccountStatementsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(AccountStatementMiddlewareError.MDWACM_009);
        return post(url, HeadersMW.getDefaultHeaders(parameter), request, AccountStatementsMWResponse.class, responseHandler);
    }
}
