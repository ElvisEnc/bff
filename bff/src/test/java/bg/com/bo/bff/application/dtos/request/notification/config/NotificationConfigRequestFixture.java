package bg.com.bo.bff.application.dtos.request.notification.config;

import java.math.BigDecimal;

public class NotificationConfigRequestFixture {

    public static RegisterNotificationRequest withDefaultEnableNotificationRequest() {
        return RegisterNotificationRequest.builder()
                .baseAmount(BigDecimal.valueOf(185))
                .configurationId(951951)
                .email("canalesdigitales@bg.com.bo")
                .build();
    }

    public static RegisterNotificationRequest withDefaultDisableNotificationRequest() {
        return RegisterNotificationRequest.builder()
                .baseAmount(BigDecimal.valueOf(185))
                .configurationId(951951)
                .email("canalesdigitales@bg.com.bo")
                .build();
    }

    public static SubscribeNotificationRequest withDefaultSubscribeNotification() {
        return SubscribeNotificationRequest.builder()
                .devicePushToken("abcdefghijklmnopqrstuvwxyz123456789")
                .build();
    }

}
