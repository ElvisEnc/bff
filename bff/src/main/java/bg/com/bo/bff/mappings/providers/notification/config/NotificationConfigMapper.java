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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationConfigMapper implements INotificationConfigMapper {

    @Override
    public SubscribeNotificationMWRequest convertRequest(Integer personId, SubscribeNotificationRequest request) {
        return SubscribeNotificationMWRequest.builder()
                .personId(personId)
                .registerID(request.getDevicePushToken())
                .build();
    }

    @Override
    public NotificationConfigResponse convertResponse(NotificationConfigMWResponse mwResponse) {
        List<NotificationConfigResponse.Configuration> lista = mwResponse.getData().stream()
                .map(
                        data -> NotificationConfigResponse.Configuration.builder()
                                .identify(data.getIdentify())
                                .shippingType(data.getShippingType())
                                .movementType(data.getMovementType())
                                .statecode(data.getStatecode())
                                .identifyregistry(data.getIdentifyregistry())
                                .amount(data.getAmount())
                                .build()
                ).toList();
        return NotificationConfigResponse.builder()
                .data(lista)
                .build();
    }

    @Override
    public NotificationConfigMWRequest convertRequest(Integer personId) {
        return NotificationConfigMWRequest.builder()
                .personId(personId)
                .build();
    }


    @Override
    public NotificationResponse convertResponse(SubscribeNotificationMWResponse mwResponse) {
        return NotificationResponse.builder()
                .identifier(mwResponse.getData().getIdentifier())
                .build();
    }

    @Override
    public RegisterNotificationMWRequest convertRequestRegister(Integer personId, Integer configurationId) {
        return RegisterNotificationMWRequest.builder()
                .identificator(configurationId)
                .build();
    }

    @Override
    public RegisterNotificationMWRequest convertRequestRegister(Integer personId, RegisterNotificationRequest request) {
        return RegisterNotificationMWRequest.builder()
                .identificator(request.getConfigurationId())
                .amountBase(request.getBaseAmount())
                .email(request.getEmail())
                .build();
    }


}
