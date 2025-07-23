package bg.com.bo.bff.providers.dtos.request.notification.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeNotificationMWRequest {
    private int personId;
    private String registerID;
}
