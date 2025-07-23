package bg.com.bo.bff.application.dtos.response.notification.config;

import java.math.BigDecimal;
import java.util.Collections;

public class NotificationConfigResponseFixture {

    public static NotificationConfigResponse withDataDefaultGetNotificationConfig() {
        return NotificationConfigResponse.builder()
                .data(
                        Collections.singletonList(NotificationConfigResponse.Configuration
                                .builder()
                                .amount(BigDecimal.valueOf(365))
                                .identify(5668)
                                .identifyregistry("321654987abcd")
                                .shippingType((byte) 1)
                                .movementType("D")
                                .statecode((byte) 1)
                                .build())
                )
                .build();
    }

    public static NotificationResponse withDataDefaultEnableNotificationeResponse() {
        return NotificationResponse.builder()
                .identifier(7854)
                .build();
    }

    public static NotificationResponse withDataDefaultSubscribeNotificationResponse() {
        return NotificationResponse.builder()
                .identifier(123456)
                .build();
    }

}