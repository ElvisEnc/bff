package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.dpf.mw.DpfMWResponse;
import bg.com.bo.bff.providers.interfaces.IDPFProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.DPFMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DPFMiddlewareProvider extends MiddlewareProvider<DPFMiddlewareError> implements IDPFProvider {

    public DPFMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.DPF_MANAGER, DPFMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getDpfManager());
    }

    private static final String URL_PATH_COMPLEMENT_PDF_LIST = "/bs/v1/accounts/persons/%s/companies/%s";

    @Override
    public DpfMWResponse getDPFsList(String personId, String deviceId, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DPF_MANAGER.getName() + String.format(URL_PATH_COMPLEMENT_PDF_LIST, personId, personId);
        return get(url, HeadersMW.getDefaultHeaders(parameters), DpfMWResponse.class);
    }
}
