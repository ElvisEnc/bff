package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.*;
import bg.com.bo.bff.providers.models.enums.middleware.own.account.OwnAccountsMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.own.account.OwnAccountsMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.own.account.OwnAccountsMiddlewareService;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.commons.enums.PersonRol;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.interfaces.IOwnAccountsProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@Service
public class OwnAccountsMiddlewareProvider extends MiddlewareProvider<OwnAccountsMiddlewareError> implements IOwnAccountsProvider {
    private final String baseUrl;

    public OwnAccountsMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.OWN_ACCOUNT_MANAGER, OwnAccountsMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientOwnManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.OWN_ACCOUNT_MANAGER.getName();
    }

    @Override
    public OwnAccountsListMWResponse getAccounts(String personId, String userDeviceId, Map<String, String> parameter) throws IOException {
        String url = baseUrl + String.format(OwnAccountsMiddlewareService.GET_OWN_ACCOUNTS.getServiceURL(), personId, personId, userDeviceId, PersonRol.PERSONA.getId());
        return get(url, HeadersMW.getDefaultHeaders(parameter), OwnAccountsListMWResponse.class);
    }

    @Override
    public GenericResponse updateTransactionLimit(String personId, String accountId, UpdateTransactionLimitMWRequest request, Map<String, String> parameter) throws IOException {
        String url = baseUrl + String.format(OwnAccountsMiddlewareService.PUT_TRANSACTION_LIMIT.getServiceURL(), accountId, personId, personId);
        UpdateLimitMWResponse mwResponse = put(url, HeadersMW.getDefaultHeaders(parameter), request, UpdateLimitMWResponse.class);
        if (mwResponse.getData().getIdentifier() != null)
            return GenericResponse.instance(OwnAccountsMiddlewareResponse.SUCCESS_UPDATE_TRANSACTION_LIMIT);
        else
            return GenericResponse.instance(OwnAccountsMiddlewareResponse.ERROR_UPDATE_TRANSACTION_LIMIT);
    }

    @Override
    public TransactionLimitsMWResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException {
        String url = baseUrl + String.format(OwnAccountsMiddlewareService.GET_TRANSACTION_LIMIT.getServiceURL(), personId, accountId, personId);
        ApiDataResponse<TransactionLimitsMWResponse> mwResponse = get(url, HeadersMW.getDefaultHeaders(parameter), ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), TransactionLimitsMWResponse.class);
    }

    @Override
    public AccountStatementsMWResponse getAccountStatements(AccountStatementsMWRequest request, Map<String, String> parameter) throws IOException {
        String url = baseUrl + OwnAccountsMiddlewareService.POST_ACCOUNT_STATEMENT.getServiceURL();
        ByMwErrorResponseHandler<AccountStatementsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(OwnAccountsMiddlewareError.MDWACM_008);
        return post(url, HeadersMW.getDefaultHeaders(parameter), request, AccountStatementsMWResponse.class, responseHandler);
    }
}
