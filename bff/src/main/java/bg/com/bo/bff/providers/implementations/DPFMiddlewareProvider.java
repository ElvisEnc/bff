package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.DPFMWResponse;
import bg.com.bo.bff.providers.interfaces.IDPFProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.DPFMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DPFMiddlewareProvider extends MiddlewareProvider<DPFMiddlewareError> implements IDPFProvider {

    public DPFMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.DPF_MANAGER, DPFMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory);
    }

    private static final String URL_PATH_COMPLEMENT_PDF_LIST = "/bs/v1/accounts/persons/%s/companies/%s";

    @Override
    public DPFMWResponse getDPFsList(String personId, String deviceId, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DPF_MANAGER.getName() + String.format(URL_PATH_COMPLEMENT_PDF_LIST, personId, personId);
        Header[] headers = {
                new BasicHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), deviceId),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()))
        };

        return get(url, headers, DPFMWResponse.class);
    }
}
