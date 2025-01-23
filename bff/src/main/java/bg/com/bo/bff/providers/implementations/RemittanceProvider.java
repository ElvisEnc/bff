package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ValidateAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ValidateAccountMWResponse;
import bg.com.bo.bff.providers.interfaces.IRemittanceProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareService;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RemittanceProvider extends MiddlewareProvider<RemittanceMiddlewareError> implements IRemittanceProvider {
    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public RemittanceProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.REMITTANCE_MANAGER, RemittanceMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientRemittanceManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.REMITTANCE_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ListGeneralParametersMWResponse getGeneralParameters(GeneralParametersMWRequest request) throws IOException {
        String url = baseUrl + RemittanceMiddlewareService.GET_GENERAL_PARAMETERS.getServiceURL();
        ApiDataResponse<ListGeneralParametersMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), ListGeneralParametersMWResponse.class);
    }

    @Override
    public ValidateAccountMWResponse validateAccount(ValidateAccountMWRequest request) throws IOException {
        String url = baseUrl + RemittanceMiddlewareService.VALIDATE_ACCOUNT.getServiceURL();
        ApiDataResponse<ValidateAccountMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), ValidateAccountMWResponse.class);
    }
}
