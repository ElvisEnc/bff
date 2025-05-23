package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.notification.config.NotificationConfigRequestFixture;
import bg.com.bo.bff.application.dtos.request.notification.config.RegisterNotificationRequest;
import bg.com.bo.bff.application.dtos.request.notification.config.SubscribeNotificationRequest;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationConfigResponse;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationConfigResponseFixture;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationResponse;
import bg.com.bo.bff.mappings.providers.notification.config.NotificationConfigMapper;
import bg.com.bo.bff.providers.dtos.response.notification.config.NotificationConfigMWResponse;
import bg.com.bo.bff.providers.dtos.response.notification.config.NotificationConfigMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.notification.config.SubscribeNotificationMWResponse;
import bg.com.bo.bff.providers.interfaces.INotificationConfigProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationConfigServiceTest {
    @InjectMocks
    private NotificationConfigService service;
    @Spy
    private NotificationConfigMapper mapper = new NotificationConfigMapper();

    @Mock
    private INotificationConfigProvider provider;

    @Test
    void givenSubscribeNotificationIsSuccess() throws IOException {
        //Arrange
        SubscribeNotificationMWResponse mwResponseMock = NotificationConfigMWResponseFixture.withDefaultSubscribeNotification();
        NotificationResponse expectedResponse = NotificationConfigMWResponseFixture.withDefaultRegisterNpsResponse();
        SubscribeNotificationRequest request = NotificationConfigMWResponseFixture.withDefaultSubscribeNotificationRequest();

        //Act
        when(provider.subscriptionNotification(any())).thenReturn(mwResponseMock);
        NotificationResponse response = service.subscribeNotification(10, request);

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getIdentifier(), response.getIdentifier());
        assertEquals(expectedResponse, response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).subscriptionNotification(any());

    }

    @Test
    void givenGetNotificationConfigIsSuccess() throws IOException {

        Integer personId = 123132;
        NotificationConfigMWResponse mwResponseMock = NotificationConfigMWResponseFixture.withDefaultGetNotificationConfigMW();

        NotificationConfigResponse expectedResponse = NotificationConfigResponseFixture.withDataDefaultGetNotificationConfig();

        when(provider.getNotificationConfig(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        NotificationConfigResponse response = service.getNotificationConfig(personId);

        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getNotificationConfig(any());
        verify(mapper).convertResponse(mwResponseMock);

    }

    @Test
    void givengetEnableNotificationSuccess() throws IOException {
        //Arrange
        Integer personId = 9589589;
        RegisterNotificationRequest request = NotificationConfigRequestFixture.withDefaultEnableNotificationRequest();
        SubscribeNotificationMWResponse mwResponseMock = NotificationConfigMWResponseFixture
                .withDefaultSubscribeNotificationMWResponse();
        NotificationResponse expectedResponse = NotificationConfigResponseFixture.withDataDefaultEnableNotificationeResponse();

        when(provider.enableNotification(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        NotificationResponse response = service.enableNotification(personId, request);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).enableNotification(any());
        verify(mapper).convertResponse(mwResponseMock);

    }

    @Test
    void givengetDisableNotificationSuccess() throws IOException {
        //Arrange
        Integer personId = 951951;
        Integer configurationId = 159159;
        SubscribeNotificationMWResponse mwResponseMock = NotificationConfigMWResponseFixture
                .withDefaultSubscribeNotificationMWResponse();
        NotificationResponse expectedResponse = NotificationConfigResponseFixture.withDataDefaultEnableNotificationeResponse();

        when(provider.disableNotification(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        NotificationResponse response = service.disableNotification(personId, configurationId);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).disableNotification(any());
        verify(mapper).convertResponse(mwResponseMock);

    }



}
