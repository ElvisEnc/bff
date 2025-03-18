package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.loans.*;
import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;
import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.ILoansService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoansControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private LoansController controller;
    @Mock
    private ILoansService service;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenValidDataWhenGetListLoansByPersonIdThenListLoansResponse() throws Exception {
        // Arrange
        ListLoansRequest requestMock = LoansRequestFixture.withDefaultListLoansRequest();
        List<ListLoansResponse> expectedResponse = LoansResponseFixture.withDataDefaultListLoansResponse();
        when(service.getListLoansByPerson(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/persons/1234/clients/4321";
        MvcResult result = mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getListLoansByPerson(any(), any(), any());
    }

    @Test
    void givenLoanPaymentsRequestWhenGetLoanPaymentsThenResponseListLoanPayments() throws Exception {
        //Arrange
        LoanPaymentsRequest requestMock = LoansRequestFixture.withDefaultLoanPaymentsRequest();
        List<LoanPaymentsResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanPaymentsResponse();
        when(service.getLoanPayments(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/{loanId}/clients/{clientId}/payments";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getLoanPayments(any(), any(), any());
    }

    @Test
    void givenBadFormatPeriodDateRequestWhenGetLoanPaymentsThenResponseBadRequest() throws Exception {
        //Arrange
        LoanPaymentsRequest requestMock = LoanPaymentsRequest.builder()
                .loanNumber("123456")
                .filters(LoanPaymentsRequest.LoanPaymentsFilter.builder()
                        .date(PeriodRequest.builder().start("2023/11/22").end("2024/04/30").build())
                        .build())
                .refreshData(false)
                .build();
        // Act
        String path = "/api/v1/loans/{loanId}/clients/{clientId}/payments";
        mockMvc.perform(post(path, "123", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMock)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNullPeriodDateRequestWhenGetLoanPaymentsThenResponseBadRequest() throws Exception {
        //Arrange
        LoanPaymentsRequest requestMock = LoanPaymentsRequest.builder()
                .loanNumber("123456")
                .filters(LoanPaymentsRequest.LoanPaymentsFilter.builder()
                        .date(PeriodRequest.builder().start("").end(null).build())
                        .build())
                .refreshData(false)
                .build();
        // Act
        String path = "/api/v1/loans/{loanId}/clients/{clientId}/payments";
        mockMvc.perform(post(path, "123", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMock)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenLoanInsurancePaymentsRequestWhenGetLoanPaymentsThenResponseListLoanInsurancePayments() throws Exception {
        //Arrange
        LoanPaymentsRequest requestMock = LoansRequestFixture.withDefaultLoanPaymentsRequest();
        List<LoanInsurancePaymentsResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanInsurancePaymentsResponse();
        when(service.getLoanInsurancePayments(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/{loanId}/persons/{personId}/insurance-payments";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getLoanInsurancePayments(any(), any(), any());
    }

    @Test
    void givenLoanPlanRequestWhenGetLoanPlansThenResponseListLoanPlans() throws Exception {
        //Arrange
        List<LoanPlanResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanPlanResponse();
        when(service.getLoanPlans(any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/{loanId}/persons/{personId}";
        MvcResult result = mockMvc.perform(get(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getLoanPlans(any(), any());
    }

    @Test
    void givenLoandIdAndPersonIdWhenGetLoanPaymentRequestThenResponseLoanDetailPaymentResponse() throws Exception {
        //Arrange
        LoanDetailPaymentResponse expectedResponse = LoansResponseFixture.withDefaultLoanDetailPaymentResponse();
        when(service.getLoanDetailPayment(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/{loanId}/persons/{personId}/payments/{clientId}";
        MvcResult result = mockMvc.perform(get(path, "123", "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getLoanDetailPayment(any(), any(), any());
    }

    @Test
    void givenLoanIdAccountIdPersonIdWhenPayLoanRequestThenResponseLoanPaymentResponse() throws Exception {
        //Arrange
        LoanPaymentRequest requestMock = LoansRequestFixture.withDefaultLoanPaymentRequest();
        LoanPaymentResponse expectedResponse = LoansResponseFixture.withDataDefaultLoanPaymentResponse();
        when(service.payLoanInstallment(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/persons/{personId}/accounts/{accountId}/payment";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).payLoanInstallment(any(), any(), any());
    }

    @Test
    void givenValidDataWhenValidatePCC01ThenResponsePcc01Response() throws Exception {
        //Arrange
        Pcc01Request requestMock = LoansRequestFixture.withDefaultPcc01Request();
        Pcc01Response expectedResponse = LoansResponseFixture.withDefaultPcc01Response();
        when(service.makeControl(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/persons/{personId}/accounts/{accountId}/validate-digital";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).makeControl(any(), any(), any());
    }
}