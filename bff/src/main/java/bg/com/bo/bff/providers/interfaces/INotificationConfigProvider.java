package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.notification.config.NotificationConfigMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.RegisterNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.SubscribeNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.response.notification.config.NotificationConfigMWResponse;
import bg.com.bo.bff.providers.dtos.response.notification.config.SubscribeNotificationMWResponse;

import java.io.IOException;

public interface INotificationConfigProvider {

    SubscribeNotificationMWResponse subscriptionNotification(SubscribeNotificationMWRequest request) throws IOException;

    NotificationConfigMWResponse getNotificationConfig(NotificationConfigMWRequest request) throws IOException;

    SubscribeNotificationMWResponse enableNotification(RegisterNotificationMWRequest request) throws IOException;

    SubscribeNotificationMWResponse disableNotification(RegisterNotificationMWRequest request) throws IOException;

}
