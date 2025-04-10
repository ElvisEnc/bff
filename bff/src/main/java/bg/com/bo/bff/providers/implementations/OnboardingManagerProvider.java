package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.DisableDeviceMWResponse;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse;
import bg.com.bo.bff.providers.interfaces.IOnboardingManagerProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.onboarding.manager.OnboardingManagerMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.onboarding.manager.OnboardingManagerMiddlewareService;
import bg.com.bo.bff.providers.models.enums.middleware.onboarding.manager.OnboardingMiddlewareResponse;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OnboardingManagerProvider extends MiddlewareProvider<OnboardingManagerMiddlewareError> implements
        IOnboardingManagerProvider {
    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public OnboardingManagerProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig,
                                     IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.ONBOARDING_MANAGER, OnboardingManagerMiddlewareError.class, tokenMiddlewareProvider,
                middlewareConfig, httpClientFactory, middlewareConfig.getClientOnboardingManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.ONBOARDING_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;

    }

    @Override
    public ListDevicesMWResponse listDeviceOnboardingManager(int personId) throws IOException {

        String url = baseUrl + String.format(OnboardingManagerMiddlewareService.GET_PERSON_DEVICES.getServiceURL(), personId);
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), ListDevicesMWResponse.class);

    }

    @Override
    public GenericResponse disableDevice(int personId, String deviceId) throws IOException {

        String url = baseUrl + String.format(OnboardingManagerMiddlewareService.DISABLE_DEVICE.getServiceURL(), personId,
                deviceId);
        DisableDeviceMWResponse mwResponse = post(
                url,
                HeadersMW.getDefaultHeaders(httpServletRequest),
                null,
                DisableDeviceMWResponse.class
        );

        if (mwResponse.getData() != null && mwResponse.getData().getCodeResponse().equals("COD000"))
            return GenericResponse.instance(OnboardingMiddlewareResponse.SUCCESS_DEACTIVATE_DEVICE);
        else return GenericResponse.instance(OnboardingMiddlewareResponse.ERROR_DEACTIVATE_DEVICE);

    }
}
