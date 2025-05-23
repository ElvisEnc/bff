package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.notification.config.RegisterNotificationRequest;
import bg.com.bo.bff.application.dtos.request.notification.config.SubscribeNotificationRequest;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationConfigResponse;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationResponse;
import bg.com.bo.bff.mappings.providers.notification.config.INotificationConfigMapper;
import bg.com.bo.bff.providers.dtos.request.notification.config.NotificationConfigMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.RegisterNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.SubscribeNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.response.notification.config.NotificationConfigMWResponse;
import bg.com.bo.bff.providers.interfaces.INotificationConfigProvider;
import bg.com.bo.bff.services.interfaces.INotificationConfigService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationConfigService implements INotificationConfigService {
    private final INotificationConfigMapper mapper;
    private final INotificationConfigProvider provider;

    public NotificationConfigService(INotificationConfigProvider provider, INotificationConfigMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public NotificationResponse subscribeNotification(
            Integer personId, SubscribeNotificationRequest request
    ) throws IOException {
        SubscribeNotificationMWRequest mwRequest = mapper.convertRequest(personId, request);
        return mapper.convertResponse(provider.subscriptionNotification(mwRequest));
    }

    @Override
    public NotificationConfigResponse getNotificationConfig(
            Integer personId
    ) throws IOException {
        NotificationConfigMWRequest mwRequest = mapper.convertRequest(personId);
        NotificationConfigMWResponse mwResponse = provider.getNotificationConfig(mwRequest);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public NotificationResponse enableNotification(
            Integer personId, RegisterNotificationRequest request
    ) throws IOException {
        RegisterNotificationMWRequest mwRequest = mapper.convertRequestRegister(personId, request);
        return mapper.convertResponse(provider.enableNotification(mwRequest));
    }

    @Override
    public NotificationResponse disableNotification(Integer personId, Integer configurationId) throws IOException {
        RegisterNotificationMWRequest mwRequest = mapper.convertRequestRegister(personId, configurationId);
        return mapper.convertResponse(provider.disableNotification(mwRequest));
    }

}
