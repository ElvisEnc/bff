package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QrListMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.*;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchAccountMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AchAccountMiddlewareProvider extends MiddlewareProvider<AchMiddlewareError> implements IAchAccountProvider {
    private final String baseUrl;

    public AchAccountMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.ACH_ACCOUNTS, AchMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientAchAccount());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_ACCOUNTS.getName();
    }


    @Override
    public GenericResponse addAchAccount(AddAchAccountBasicRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + AchMiddlewareServices.ADD_ACCOUNT.getServiceURL();
        ApiDataResponse<AchAccountMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        AchAccountMWResponse response = Util.stringToObject(Util.objectToString(mwResponse.getData()), AchAccountMWResponse.class);
        if (response.getIdentifier() != null)
            return GenericResponse.instance(AchAccountMiddlewareResponse.SUCCESS_ADD_ACCOUNT);
        else return GenericResponse.instance(AchAccountMiddlewareResponse.ERROR_ADD_ACCOUNT);
    }

    @Override
    public GenericResponse deleteAchAccount(DeleteAchAccountMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + AchMiddlewareServices.DELETE_ACCOUNT.getServiceURL();
        ApiDataResponse<AchAccountMWResponse> mwResponse = deleteWithBody(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        AchAccountMWResponse response = Util.stringToObject(Util.objectToString(mwResponse.getData()), AchAccountMWResponse.class);
        if (response.getIdentifier() != null)
            return GenericResponse.instance(AchAccountMiddlewareResponse.SUCCESS_DELETE_ACCOUNT);
        else return GenericResponse.instance(AchAccountMiddlewareResponse.ERROR_DELETE_ACCOUNT);
    }

    @Override
    public BanksMWResponse getBanks(Map<String, String> parameters) throws IOException {
        String url = baseUrl + AchMiddlewareServices.GET_BANKS.getServiceURL();
        return get(url, HeadersMW.getDefaultHeaders(parameters), BanksMWResponse.class);
    }

    @Override
    public BranchOfficeMWResponse getAllBranchOfficeBank(String code, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(AchMiddlewareServices.GET_BRANCH_OFFICES.getServiceURL(), code);
        return get(url, HeadersMW.getDefaultHeaders(parameters), BranchOfficeMWResponse.class);
    }

    @Override
    public AchAccountsMWResponse getAchAccounts(String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(AchMiddlewareServices.GET_ACH_ACCOUNTS.getServiceURL(), personId, personId);
        ByMwErrorResponseHandler<AchAccountsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(AchMiddlewareError.MDWAAM_004);
        return get(url, HeadersMW.getDefaultHeaders(parameters), AchAccountsMWResponse.class, responseHandler);
    }

    @Override
    public QrListMWResponse getListQrGeneratePaidMW(QrListMWRequest request, String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + AchMiddlewareServices.GET_TRANSACTION_HISTORY.getServiceURL();
        ByMwErrorResponseHandler<QrListMWResponse> responseHandler = ByMwErrorResponseHandler.instance(AchMiddlewareError.MDWAAM_001);
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, QrListMWResponse.class, responseHandler);
    }
}
