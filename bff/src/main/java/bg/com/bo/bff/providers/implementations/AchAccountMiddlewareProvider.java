package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.destination.account.AddAccountResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
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
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchAccountMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchAccountMiddlewareServices;
import bg.com.bo.bff.providers.models.enums.middleware.third.account.ThirdAccountMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AchAccountMiddlewareProvider extends MiddlewareProvider<AchAccountMiddlewareError> implements IAchAccountProvider {
    private final String baseUrl;

    public AchAccountMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.ACH_ACCOUNTS, AchAccountMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientAchAccount());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_ACCOUNTS.getName();
    }


    @Override
    public AddAccountResponse addAchAccount(AddAchAccountBasicRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + AchAccountMiddlewareServices.ADD_ACCOUNT.getServiceURL();
        ApiDataResponse<AddAccountMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        AddAccountMWResponse response = Util.stringToObject(Util.objectToString(mwResponse.getData()), AddAccountMWResponse.class);
        if (response.getIdentifier() != null)
            return AddAccountResponse.builder().id(Long.valueOf(response.getIdentifier())).build();
        else throw new GenericException(AchAccountMiddlewareError.ERROR_ADD_ACCOUNT);
    }

    @Override
    public GenericResponse deleteAchAccount(String personId, long identifier, long accountNumber, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(AchAccountMiddlewareServices.DELETE_ACCOUNT.getServiceURL(), identifier, personId, accountNumber);
        ApiDataResponse<AddAccountMWResponse> mwResponse = delete(url, HeadersMW.getDefaultHeaders(parameters), ApiDataResponse.class);
        AddAccountMWResponse response = Util.stringToObject(Util.objectToString(mwResponse.getData()), AddAccountMWResponse.class);
        if (response.getIdentifier() != null)
            return GenericResponse.instance(AchAccountMiddlewareResponse.SUCCESS_DELETE_ACCOUNT);
        else return GenericResponse.instance(AchAccountMiddlewareResponse.ERROR_DELETE_ACCOUNT);
    }

    @Override
    public BanksMWResponse getBanks(Map<String, String> parameters) throws IOException {
        String url = baseUrl + AchAccountMiddlewareServices.GET_BANKS.getServiceURL();
        return get(url, HeadersMW.getDefaultHeaders(parameters), BanksMWResponse.class);
    }

    @Override
    public BranchOfficeMWResponse getAllBranchOfficeBank(String code, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(AchAccountMiddlewareServices.GET_BRANCH_OFFICES.getServiceURL(), code);
        return get(url, HeadersMW.getDefaultHeaders(parameters), BranchOfficeMWResponse.class);
    }

    @Override
    public AchAccountsMWResponse getAchAccounts(String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(AchAccountMiddlewareServices.GET_ACH_ACCOUNTS.getServiceURL(), personId, personId);
        ByMwErrorResponseHandler<AchAccountsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(AchAccountMiddlewareError.MDWAAM_004);
        return get(url, HeadersMW.getDefaultHeaders(parameters), AchAccountsMWResponse.class, responseHandler);
    }

    @Override
    public QrListMWResponse getListQrGeneratePaidMW(QrListMWRequest request, String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + AchAccountMiddlewareServices.GET_TRANSACTION_HISTORY.getServiceURL();
        ByMwErrorResponseHandler<QrListMWResponse> responseHandler = ByMwErrorResponseHandler.instance(AchAccountMiddlewareError.MDWAAM_001);
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, QrListMWResponse.class, responseHandler);
    }
}
