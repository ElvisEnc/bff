package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyRequestFixture;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyResponseFixture;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.services.interfaces.ILoyaltyService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoyaltyControllerTest {
    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private LoyaltyController controller;
    @Mock
    private ILoyaltyService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenPersonIdWhenGetSystemCodeThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltySystemCodeResponse responseExpected = LoyaltyResponseFixture.withDefaultSystemCode();
        when(service.getSystemCode(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/system-code";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getSystemCode(any());
    }

    @Test
    void givenPersonIdWhenGetSumPointThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltySumPointResponse responseExpected = LoyaltyResponseFixture.withDefaultSumPoint();
        when(service.getSumPoint(any(), any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/code-system/{codeSystem}/sum-points";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123", "1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getSumPoint(any(), any());
    }

    @Test
    void givenPersonIdWhenRegisterSubscriptionThenReturnSuccess() throws Exception {
        // Arrange
        RegisterSubscriptionRequest request = LoyaltyRequestFixture.withDefaultRegisterSubscription();
        GenericResponse responseExpected = LoyaltyResponseFixture.withDefaultGeneric();
        when(service.registerSubscription(any(), any(), any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/accounts/{accountId}/register-subscription";
        MvcResult result = mockMvc.perform(post(urlLoyalty, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).registerSubscription(any(), any(), any());
    }
}
