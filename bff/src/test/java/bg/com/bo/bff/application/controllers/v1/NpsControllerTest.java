package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.nps.NpsRequestFixture;
import bg.com.bo.bff.application.dtos.request.nps.ResponseNpsRequest;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponse;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponseFixture;
import bg.com.bo.bff.application.dtos.response.nps.RegisterNpsResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.INpsService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NpsControllerTest {
    private static final String REGISTER_DEVICE_NPS = "/api/v1/nps/persons/{personId}";
    private static final String SEND_ANSWER_NPS = "/api/v1/nps/persons/{personId}/response-nps";

    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private NpsController controller;
    @Mock
    private INpsService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();
        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void registerDevice() throws Exception {
        // Arrange
        RegisterNpsResponse expectedResponse = NpsResponseFixture.withDefaultRegisterNpsResponse();
        when(service.registerDevice(any(), any())).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(
                        post(REGISTER_DEVICE_NPS, "123456")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("device-id", "353535")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(expectedResponse);
        String actual = result.getResponse().getContentAsString();
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).registerDevice(any(), any());
    }

    @Test
    void testAnswerNps() throws Exception {
        // Arrange
        NpsResponse expectedResponse = NpsResponseFixture.withDefaultNpsNpsResponse();
        ResponseNpsRequest request = NpsRequestFixture.withDefaultResponseNpsRequest();
        when(service.sendAnswerNps(anyInt(), anyString(), any(ResponseNpsRequest.class))).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(
                        post(SEND_ANSWER_NPS, "123456")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("device-id", "353535")
                                .content(Util.objectToString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(expectedResponse);
        String actual = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).sendAnswerNps(anyInt(), anyString(), any(ResponseNpsRequest.class));
    }


}
