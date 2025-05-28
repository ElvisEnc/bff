package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.notification.config.NotificationConfigRequestFixture;
import bg.com.bo.bff.application.dtos.request.notification.config.RegisterNotificationRequest;
import bg.com.bo.bff.application.dtos.request.notification.config.SubscribeNotificationRequest;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationConfigResponse;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationConfigResponseFixture;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.INotificationConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NotificationConfigControllerTest {
    private static final String SUBSCRIBE_NOTIFICATION = "/api/v1/notifications/persons/{personId}/subscribe";
    private static final String LIST_NOTIFICATION_CONFIG = "/api/v1/notifications/persons/{personId}";
    private static final String ENABLE_NOTIFICATION_CONFIG = "/api/v1/notifications/persons/{personId}/enabled";
    private static final String DISABLE_NOTIFICATION_CONFIG =
            "/api/v1/notifications/persons/{personId}/configurations/{configurationId}/disabled";

    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private NotificationConfigController controller;
    @Mock
    private INotificationConfigService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();
        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenValidDataWhenSubscribeNotificationThenIsSuccess() throws Exception {
        // Arrange
        NotificationResponse expectedResponse = NotificationConfigResponseFixture.withDataDefaultSubscribeNotificationResponse();
        SubscribeNotificationRequest request = NotificationConfigRequestFixture.withDefaultSubscribeNotification();
        when(service.subscribeNotification(any(), any())).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(post(SUBSCRIBE_NOTIFICATION, "123456")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).subscribeNotification(any(), any());
    }

    @Test
    void givenValidDataWhenGetNotificationConfigThenIsSuccess() throws Exception {
        // Arrange
        NotificationConfigResponse expectedResponse = NotificationConfigResponseFixture.withDataDefaultGetNotificationConfig();
        when(service.getNotificationConfig(any())).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(get(LIST_NOTIFICATION_CONFIG, "123456")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getNotificationConfig(any());
    }

    @Test
    void givenValidDataWhenEnableNotificationConfigThenIsSuccess() throws Exception {
        // Arrange
        RegisterNotificationRequest request = NotificationConfigRequestFixture.withDefaultEnableNotificationRequest();
        NotificationResponse expectedResponse = NotificationConfigResponseFixture.withDataDefaultEnableNotificationeResponse();
        when(service.enableNotification(any(), any())).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(patch(ENABLE_NOTIFICATION_CONFIG, "123456")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).enableNotification(any(), any());
    }

    @Test
    void givenValidDataWhenDisableNotificationConfigThenIsSuccess() throws Exception {
        // Arrange
        NotificationResponse expectedResponse = NotificationConfigResponseFixture.withDataDefaultEnableNotificationeResponse();
        when(service.disableNotification(any(), any())).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(patch(DISABLE_NOTIFICATION_CONFIG, "123456", "293092")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).disableNotification(any(), any());
    }


}
