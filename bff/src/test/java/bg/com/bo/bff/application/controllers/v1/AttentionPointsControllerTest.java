package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.attention.points.AttentionPointsResponseFixture;
import bg.com.bo.bff.application.dtos.response.attention.points.DetailAttentionPointResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.ListAttentionPointsResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.PendingTicketResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IAttentionPointsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AttentionPointsControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private AttentionPointsController controller;
    @Mock
    private IAttentionPointsService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void givenPointIdWhenGetDetailAttentionPointThenExpectedResponse() throws Exception {
        // Arrange
        DetailAttentionPointResponse expectedResponse = AttentionPointsResponseFixture.withDefaultDetailAttentionPointResponse();
        when(service.getDetailAttentionPoint(any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/attention-points/points/{pointId}";
        MvcResult result = mockMvc.perform(get(path, "1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getDetailAttentionPoint(any());
    }

    @Test
    void givenPointIdWhenGetPendingTicketsAttentionPointThenExpectedResponse() throws Exception {
        // Arrange
        PendingTicketResponse expectedResponse = AttentionPointsResponseFixture.withDefaultPendingTicketResponse();
        when(service.getPendingTickets(any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/attention-points/points/{pointId}/tickets";
        MvcResult result = mockMvc.perform(get(path, "1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getPendingTickets(any());
    }


    @Test
    void givenValidPetitionWhenGetListAttentionPointsThenExpectedResponse() throws Exception {
        // Arrange
        ListAttentionPointsResponse expectedResponse = AttentionPointsResponseFixture.withDefaultListAttentionPointsResponse();
        when(service.getListAttentionPoints()).thenReturn(expectedResponse);

        // Act
        MvcResult result = mockMvc.perform(get("/api/v1/attention-points/points")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getListAttentionPoints();
    }
}