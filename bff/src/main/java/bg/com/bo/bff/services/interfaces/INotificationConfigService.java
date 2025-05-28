package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.notification.config.RegisterNotificationRequest;
import bg.com.bo.bff.application.dtos.request.notification.config.SubscribeNotificationRequest;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationConfigResponse;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationResponse;

import java.io.IOException;

public interface INotificationConfigService {

    NotificationResponse subscribeNotification(Integer personId, SubscribeNotificationRequest request) throws IOException;

    NotificationConfigResponse getNotificationConfig(Integer personId) throws IOException;

    NotificationResponse enableNotification(Integer personId, RegisterNotificationRequest request) throws IOException;

    NotificationResponse disableNotification(Integer personId, Integer configurationId) throws IOException;

}
