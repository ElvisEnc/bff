package bg.com.bo.bff.providers.implementations;


import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.notification.config.NotificationConfigMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.RegisterNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.SubscribeNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.response.notification.config.NotificationConfigMWResponse;
import bg.com.bo.bff.providers.dtos.response.notification.config.SubscribeNotificationMWResponse;
import bg.com.bo.bff.providers.interfaces.INotificationConfigProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.notification.config.NotificationConfigMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.notification.config.NotificationConfigMiddlewareService;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationConfigProvider extends MiddlewareProvider<NotificationConfigMiddlewareError>
        implements INotificationConfigProvider {
    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public NotificationConfigProvider(
            ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig,
            IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest
    ) {
        super(
                ProjectNameMW.NOTIFICATION_MANAGER, NotificationConfigMiddlewareError.class,
                tokenMiddlewareProvider, middlewareConfig,
                httpClientFactory, middlewareConfig.getClientNotificationConfigManager()
        );
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.NOTIFICATION_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public SubscribeNotificationMWResponse subscriptionNotification(SubscribeNotificationMWRequest request) throws IOException {
        String url = baseUrl + String.format(NotificationConfigMiddlewareService.SUBSCRIBER_NOTIFICATION.getServiceURL());
        ByMwErrorResponseHandler<SubscribeNotificationMWResponse> responseHandler = ByMwErrorResponseHandler
                .instance(NotificationConfigMiddlewareError.RM001);
        return post(
                url,
                HeadersMW.getDefaultHeaders(httpServletRequest),
                request,
                SubscribeNotificationMWResponse.class, responseHandler
        );
    }

    @Override
    public NotificationConfigMWResponse getNotificationConfig(NotificationConfigMWRequest request) throws IOException {
        String url = baseUrl + String.format(NotificationConfigMiddlewareService.GET_NOTIFICATION_CONFIG.getServiceURL());
        ByMwErrorResponseHandler<NotificationConfigMWResponse> responseHandler = ByMwErrorResponseHandler
                .instance(NotificationConfigMiddlewareError.RM001);
        return post(
                url,
                HeadersMW.getDefaultHeaders(httpServletRequest),
                request,
                NotificationConfigMWResponse.class, responseHandler
        );
    }


    @Override
    public SubscribeNotificationMWResponse enableNotification(RegisterNotificationMWRequest request) throws IOException {
        String url = baseUrl + String.format(NotificationConfigMiddlewareService.ENABLE_NOTIFICATION_CONFIG.getServiceURL());
        ByMwErrorResponseHandler<SubscribeNotificationMWResponse> responseHandler = ByMwErrorResponseHandler
                .instance(NotificationConfigMiddlewareError.RM001);
        return post(
                url,
                HeadersMW.getDefaultHeaders(httpServletRequest),
                request,
                SubscribeNotificationMWResponse.class, responseHandler
        );
    }

    @Override
    public SubscribeNotificationMWResponse disableNotification(RegisterNotificationMWRequest request) throws IOException {
        String url = baseUrl + String.format(NotificationConfigMiddlewareService.DISABLE_NOTIFICATION_CONFIG.getServiceURL());
        ByMwErrorResponseHandler<SubscribeNotificationMWResponse> responseHandler = ByMwErrorResponseHandler
                .instance(NotificationConfigMiddlewareError.RM001);
        return post(
                url,
                HeadersMW.getDefaultHeaders(httpServletRequest),
                request,
                SubscribeNotificationMWResponse.class, responseHandler
        );
    }
}
