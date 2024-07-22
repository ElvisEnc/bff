package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;
import bg.com.bo.bff.providers.interfaces.ILoansProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.loans.LoansMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.loans.LoansMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class LoansProvider extends MiddlewareProvider<LoansMiddlewareError> implements ILoansProvider {
    private final String baseUrl;

    public LoansProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.LOANS_SERVICES, LoansMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientLoansManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.LOANS_SERVICES.getName();
    }

    @Override
    public ListLoansMWResponse getListLoansByPerson(String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(LoansMiddlewareServices.GET_LIST_LOANS.getServiceURL(), personId);
        return get(url, HeadersMW.getDefaultHeaders(parameters), ListLoansMWResponse.class);
    }
}
