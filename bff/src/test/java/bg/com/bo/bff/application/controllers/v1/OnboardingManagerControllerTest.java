package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponse;
import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponseFixture;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.models.enums.middleware.onboarding.manager.OnboardingMiddlewareResponse;
import bg.com.bo.bff.services.interfaces.IOnboardingManagerService;
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

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OnboardingManagerControllerTest {
    private static final String GET_ALL_DEVICES = "/api/v1/onboarding-manager/persons/{personId}/devices";
    private static final String DEACTIVATE_DEVICE = "/api/v1/onboarding-manager/persons/{personId}/devices/{deviceId}/disable";

    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private OnboardingManagerController controller;
    @Mock
    private IOnboardingManagerService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();
        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void testGetAllDevices() throws Exception {
        // Arrange
        List<OnboardingManagerResponse> expectedResponse = OnboardingManagerResponseFixture.withDefaultGellAllDevice();
        when(service.getAllDevices(anyInt())).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(
                        get(GET_ALL_DEVICES, "123456")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("device-id", "353535")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getAllDevices(anyInt());
    }


    @Test
    void testDisableDevice() throws Exception {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(OnboardingMiddlewareResponse.ERROR_DEACTIVATE_DEVICE);
        when(service.disableDevice(anyInt(), anyString())).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(
                        post(DEACTIVATE_DEVICE, "123456", "456454554")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(expectedResponse);
        String actual = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).disableDevice(anyInt(), anyString());

    }


}
