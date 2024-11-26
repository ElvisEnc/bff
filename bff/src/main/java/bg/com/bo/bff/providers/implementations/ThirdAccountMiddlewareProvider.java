package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.destination.account.AddAccountResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.ValidateAccountResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.account.statement.PersonRol;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AddAccountMWResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.providers.models.enums.middleware.third.account.ThirdAccountMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.third.account.ThirdAccountMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.third.account.ThirdAccountMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ThirdAccountMiddlewareProvider extends MiddlewareProvider<ThirdAccountMiddlewareError> implements IThirdAccountProvider {
    private final String baseUrl;

    public ThirdAccountMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.THIRD_ACCOUNTS, ThirdAccountMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientThirdAccount());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.THIRD_ACCOUNTS.getName();
    }

    @Override
    public ValidateAccountResponse validateAccount(String accountNumber, String clientName, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(ThirdAccountMiddlewareServices.VALIDATE_NEW_ACCOUNT.getServiceURL(), accountNumber, clientName);
        return get(url, HeadersMW.getDefaultHeaders(parameters), ValidateAccountResponse.class);
    }

    @Override
    public AddAccountResponse addThirdAccount(AddThirdAccountBasicRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + ThirdAccountMiddlewareServices.ADD_THIRD_ACCOUNT.getServiceURL();
        ApiDataResponse<AddAccountMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        AddAccountMWResponse response = Util.stringToObject(Util.objectToString(mwResponse.getData()), AddAccountMWResponse.class);
        if (response.getIdentifier() != null)
            return AddAccountResponse.builder().id(Long.valueOf(response.getIdentifier())).build();
        else throw new GenericException(ThirdAccountMiddlewareError.ERROR_ADD_ACCOUNT);
    }

    @Override
    public AddAccountResponse addWalletAccount(AddWalletAccountBasicRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + ThirdAccountMiddlewareServices.ADD_WALLET_ACCOUNT.getServiceURL();
        ApiDataResponse<AddAccountMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        AddAccountMWResponse response = Util.stringToObject(Util.objectToString(mwResponse.getData()), AddAccountMWResponse.class);
        if (response.getIdentifier() != null)
            return AddAccountResponse.builder().id(Long.valueOf(response.getIdentifier())).build();
        else throw new GenericException(ThirdAccountMiddlewareError.ERROR_ADD_ACCOUNT);
    }

    @Override
    public GenericResponse deleteThirdAccount(DeleteThirdAccountMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + ThirdAccountMiddlewareServices.DELETE_THIRD_ACCOUNT.getServiceURL();
        ApiDataResponse<AddAccountMWResponse> mwResponse = deleteWithBody(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        AddAccountMWResponse response = Util.stringToObject(Util.objectToString(mwResponse.getData()), AddAccountMWResponse.class);
        if (response.getIdentifier() != null)
            return GenericResponse.instance(ThirdAccountMiddlewareResponse.SUCCESS_DELETE_ACCOUNT);
        else return GenericResponse.instance(ThirdAccountMiddlewareResponse.ERROR_DELETE_ACCOUNT);
    }

    @Override
    public GenericResponse deleteWalletAccount(String personId, long identifier, long accountNumber, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(ThirdAccountMiddlewareServices.DELETE_WALLET_ACCOUNT.getServiceURL(), identifier, personId, accountNumber);
        ApiDataResponse<AddAccountMWResponse> mwResponse = delete(url, HeadersMW.getDefaultHeaders(parameters), ApiDataResponse.class);
        AddAccountMWResponse response = Util.stringToObject(Util.objectToString(mwResponse.getData()), AddAccountMWResponse.class);
        if (response.getIdentifier() != null)
            return GenericResponse.instance(ThirdAccountMiddlewareResponse.SUCCESS_DELETE_ACCOUNT);
        else return GenericResponse.instance(ThirdAccountMiddlewareResponse.ERROR_DELETE_ACCOUNT);
    }

    @Override
    public ThirdAccountsMWResponse getThirdAccounts(String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(ThirdAccountMiddlewareServices.GET_THIRD_ACCOUNTS.getServiceURL(), personId, personId, PersonRol.PERSONA.getId(), PersonRol.PERSONA.getId());
        ByMwErrorResponseHandler<ThirdAccountsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(ThirdAccountMiddlewareError.MDWACTM_002);
        return get(url, HeadersMW.getDefaultHeaders(parameters), ThirdAccountsMWResponse.class, responseHandler);
    }

    @Override
    public ThirdAccountsMWResponse getWalletAccounts(String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(ThirdAccountMiddlewareServices.GET_WALLET_ACCOUNTS.getServiceURL(), personId, personId);
        ByMwErrorResponseHandler<ThirdAccountsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(ThirdAccountMiddlewareError.MDWACTM_002);
        return get(url, HeadersMW.getDefaultHeaders(parameters), ThirdAccountsMWResponse.class, responseHandler);
    }
}
