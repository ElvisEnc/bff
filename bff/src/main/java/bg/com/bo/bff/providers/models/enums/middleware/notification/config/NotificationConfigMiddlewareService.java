package bg.com.bo.bff.providers.models.enums.middleware.notification.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationConfigMiddlewareService {

    SUBSCRIBER_NOTIFICATION("/bs/v1/notification/suscribe"),
    GET_NOTIFICATION_CONFIG("/bs/v1/notification"),
    ENABLE_NOTIFICATION_CONFIG("/bs/v1/notification/enabled"),
    DISABLE_NOTIFICATION_CONFIG("/bs/v1/notification/disable");

    private final String serviceURL;
}
