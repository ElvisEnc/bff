package bg.com.bo.bff.providers.dtos.request.notification.config;

import java.math.BigDecimal;

public class NotificationConfigMWRequestFixture {

    public static SubscribeNotificationMWRequest withDefaultSubscriptionNotification() {
        return SubscribeNotificationMWRequest.builder()
                .registerID("147147")
                .personId(123123)
                .build();
    }

    public static NotificationConfigMWRequest withDefaultGetNotificationConfig() {
        return NotificationConfigMWRequest.builder()
                .personId(132123)
                .build();
    }


    public static RegisterNotificationMWRequest withDefaultEnableNotification() {
        return RegisterNotificationMWRequest.builder()
                .email("ganamovil@bg.com.bo")
                .amountBase(BigDecimal.valueOf(159))
                .identificator(741741)
                .build();
    }

    public static RegisterNotificationMWRequest withDefaultDisableNotification() {
        return RegisterNotificationMWRequest.builder()
                .identificator(741741).build();
    }


}
