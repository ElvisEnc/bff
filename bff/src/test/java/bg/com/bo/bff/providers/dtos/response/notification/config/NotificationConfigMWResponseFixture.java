package bg.com.bo.bff.providers.dtos.response.notification.config;

import bg.com.bo.bff.application.dtos.request.notification.config.SubscribeNotificationRequest;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationResponse;

import java.math.BigDecimal;
import java.util.Collections;

public class NotificationConfigMWResponseFixture {

    public static SubscribeNotificationMWResponse withDefaultSubscribeNotification() {
        return SubscribeNotificationMWResponse.builder()
                .data(IdentifierNotification.builder()
                        .identifier(595).build()).build();
    }

    public static NotificationResponse withDefaultRegisterNpsResponse() {
        return NotificationResponse.builder()
                .identifier(595)
                .build();
    }

    public static SubscribeNotificationRequest withDefaultSubscribeNotificationRequest() {
        return SubscribeNotificationRequest.builder()
                .devicePushToken("abcdefghijklmnopqrstuvwxyz123456789").build();
    }

    public static NotificationConfigMWResponse withDefaultGetNotificationConfigMW() {
        return NotificationConfigMWResponse.builder()
                .data(Collections.singletonList(NotificationConfigMWResponse.NotificationConfig
                        .builder()
                        .amount(BigDecimal.valueOf(365))
                        .identify(5668)
                        .identifyregistry("321654987abcd")
                        .shippingType((byte) 1)
                        .movementType("D")
                        .build()))
                .build();

    }

    public static SubscribeNotificationMWResponse withDefaultSubscribeNotificationMWResponse() {
        return SubscribeNotificationMWResponse.builder()
                .data(
                        IdentifierNotification.builder()
                                .identifier(951951).build()
                ).build();
    }

    public static SubscribeNotificationMWResponse withDefaultSubscriptionNotification() {
        return SubscribeNotificationMWResponse.builder()
                .data(
                        IdentifierNotification.builder().identifier(45645).build()
                ).build();
    }

    public static NotificationConfigMWResponse withDefaultGetNotificationConfig() {
        return NotificationConfigMWResponse.builder()
                .data(
                        Collections.singletonList(NotificationConfigMWResponse.NotificationConfig
                                .builder()
                                .amount(BigDecimal.valueOf(365))
                                .identify(5668)
                                .identifyregistry("321654987abcd")
                                .shippingType((byte) 1)
                                .movementType("D")
                                .build())
                ).build();
    }

    public static SubscribeNotificationMWResponse withDefaultDisableNotification() {
        return SubscribeNotificationMWResponse.builder()
                .data(IdentifierNotification.builder()
                        .identifier(595).build()
                ).build();
    }

}
