package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsHistoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsPreferredExchMWResponse;
import bg.com.bo.bff.providers.interfaces.ICertificationsProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.certifications.CertificationsMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.certifications.CertificationsMiddlewareService;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CertificationsProvider extends MiddlewareProvider<CertificationsMiddlewareError> implements ICertificationsProvider {

    private final String baseURL;

    private final HttpServletRequest httpServletRequest;

    public CertificationsProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.CERTIFICATIONS_MANAGER, CertificationsMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientCertificationsManager());
        baseURL = middlewareConfig.getUrlBase() + ProjectNameMW.CERTIFICATIONS_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public CertificatesTypeListMWResponse getCertificatesType(String personId, String appCode) throws IOException {
        String url = baseURL + String.format(
                CertificationsMiddlewareService.GET_CERTIFICATIONS_TYPE.getServiceURL(),
                personId,
                appCode
        );
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), CertificatesTypeListMWResponse.class);
    }

    @Override
    public CertificatesAccountsListMWResponse getAccountsList(String personId) throws IOException {
        String url = baseURL + String.format(
                CertificationsMiddlewareService.GET_ACCOUNTS.getServiceURL(),
                personId
        );
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), CertificatesAccountsListMWResponse.class);
    }

    @Override
    public CertificationsPreferredExchMWResponse getPreferredExRate(String personId) throws IOException {
        String url = baseURL + String.format(
                CertificationsMiddlewareService.GET_PREFERRED_EXCHANGE_RATE.getServiceURL(),
                personId
        );
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), CertificationsPreferredExchMWResponse.class);
    }

    @Override
    public CertificationsHistoryMWResponse getCertificationsHistory(String personId) throws IOException {
        String url = baseURL + String.format(
                CertificationsMiddlewareService.GET_CERTIFICATION_HISTORY.getServiceURL(),
                personId
        );
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), CertificationsHistoryMWResponse.class);
    }
}
