package bg.com.bo.bff.mappings.providers.notification.config;

import bg.com.bo.bff.application.dtos.request.notification.config.RegisterNotificationRequest;
import bg.com.bo.bff.application.dtos.request.notification.config.SubscribeNotificationRequest;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationConfigResponse;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationResponse;
import bg.com.bo.bff.providers.dtos.request.notification.config.NotificationConfigMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.RegisterNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.request.notification.config.SubscribeNotificationMWRequest;
import bg.com.bo.bff.providers.dtos.response.notification.config.NotificationConfigMWResponse;
import bg.com.bo.bff.providers.dtos.response.notification.config.SubscribeNotificationMWResponse;


public interface INotificationConfigMapper {

    SubscribeNotificationMWRequest convertRequest(Integer personId, SubscribeNotificationRequest request);

    NotificationConfigResponse convertResponse(NotificationConfigMWResponse mwResponse);

    NotificationConfigMWRequest convertRequest(Integer personId);

    RegisterNotificationMWRequest convertRequestRegister(Integer personId, RegisterNotificationRequest request);

    NotificationResponse convertResponse(SubscribeNotificationMWResponse mwResponse);

    RegisterNotificationMWRequest convertRequestRegister(Integer personId, Integer configurationId);

}
