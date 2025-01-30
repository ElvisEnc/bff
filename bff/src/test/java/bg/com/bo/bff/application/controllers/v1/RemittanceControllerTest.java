package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.remittance.CheckRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.MoneyOrderSentResponse;
import bg.com.bo.bff.application.dtos.response.remittance.RemittanceResponseFixture;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareResponse;
import bg.com.bo.bff.services.interfaces.IRemittanceService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RemittanceControllerTest {
    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private RemittanceController controller;
    @Mock
    private IRemittanceService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenValidDataWhenObtainGeneralParametersThenThenListGeneralParametersResponse() throws Exception {
        // Arrange
        ListGeneralParametersResponse responseExpected = RemittanceResponseFixture.withDefaultGeneralParameters();
        when(service.getGeneralParameters(any())).thenReturn(responseExpected);

        // Act
        String URL_GET_GENERAL_PARAMETERS = "/api/v1/remittances/persons/{personId}/parameters";
        MvcResult result = mockMvc.perform(get(URL_GET_GENERAL_PARAMETERS, "123")
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
        verify(service).getGeneralParameters(any());
    }

    @Test
    void givenValidDataWhenValidateAccountThenGenericResponse() throws Exception {
        // Arrange
        GenericResponse responseExpected = GenericResponse.instance(RemittanceMiddlewareResponse.ACCOUNT_ENABLED);
        when(service.validateAccount(any(), any())).thenReturn(responseExpected);

        // Act
        String URL_VALIDATE_ACCOUNT = "/api/v1/remittances/persons/{personId}/accounts/{accountId}/validate";
        MvcResult result = mockMvc.perform(get(URL_VALIDATE_ACCOUNT, "123", "123456")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).validateAccount(any(), any());
    }

    @Test
    void givenValidDataWhenGetMoneyOrdersSentThenListMoneyOrderSentResponse() throws Exception {
        // Arrange
        List<MoneyOrderSentResponse> expectedResponse = RemittanceResponseFixture.withDataDefaultListMoneyOrderSentResponse();
        when(service.getMoneyOrdersSent(any())).thenReturn(expectedResponse);

        // Act
        String URL_MONEY_ORDERS = "/api/v1/remittances/persons/{personId}/money-orders";
        MvcResult result = mockMvc.perform(get(URL_MONEY_ORDERS, "123456")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getMoneyOrdersSent(any());
    }

    @Test
    void givenValidDataWhenCheckRemittanceThenListCheckRemittanceResponse() throws Exception {
        // Arrange
        List<CheckRemittanceResponse> expectedResponse = RemittanceResponseFixture.withDataDefaultListCheckRemittanceResponse();
        when(service.checkRemittance(any(), any())).thenReturn(expectedResponse);

        // Act
        String URL_MONEY_ORDERS = "/api/v1/remittances/persons/{personId}/remittance/{remittanceId}";
        MvcResult result = mockMvc.perform(get(URL_MONEY_ORDERS, "123456", "123456789")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String expected = Util.objectToString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).checkRemittance(any(), any());
    }
}
